package com.zcx.cloud.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
/**
 * 对请求json参数进行防sql注入过滤
 *
 * @create 2020-05-30
 */
@Component
@Slf4j
@RefreshScope
public class SqLinjectionFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    // 自定义过滤器执行的顺序，数值越大越靠后执行，越小就越先执行
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    // 执行过滤逻辑
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        try {
            InputStream in = ctx.getRequest().getInputStream();
            String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
            Map<String, Object> stringObjectMap = cleanXSS(body);
            JSONObject json = (JSONObject) JSONObject.toJSON(stringObjectMap);
            String newBody = json.toString();
            //	如果存在sql注入,直接拦截请求
            if (newBody.contains("forbid")) {
                setUnauthorizedResponse(ctx);
            }
            final byte[] reqBodyBytes = newBody.getBytes();
            ctx.setRequest(new HttpServletRequestWrapper(request) {

                @Override
                public ServletInputStream getInputStream() throws IOException {
                    return new ServletInputStreamWrapper(reqBodyBytes);
                }

                @Override
                public int getContentLength() {
                    return reqBodyBytes.length;
                }

                @Override
                public long getContentLengthLong() {
                    return reqBodyBytes.length;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, Object> cleanXSS(String value) {
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        value = value.replaceAll("[*]", "[" + "*]");
        value = value.replaceAll("[+]", "[" + "+]");
        value = value.replaceAll("[?]", "[" + "?]");

        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|%|chr|mid|master|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|;|or|+|,|like'|and|exec|execute|insert|create|drop|" +
                "table|from|grant|use|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|" +
                "chr|mid|master|truncate|char|declare|or|;|--|,|like|//|/|%|#";

        JSONObject json = (JSONObject) JSONObject.toJSON(value);
        String[] badStrs = badStr.split("\\|");
        Map<String, Object> map = json;
        Map<String, Object> mapjson = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String value1 = entry.getValue().toString();
            for (String bad : badStrs) {
                if (value1.equalsIgnoreCase(bad)) {
                    value1 = "forbid";
                    mapjson.put(entry.getKey(), value1);
                    break;
                } else {
                    mapjson.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return mapjson;
    }

    /**
     * 设置500拦截状态
     */
    private void setUnauthorizedResponse(RequestContext requestContext) {
        requestContext.setResponseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

    }
}

