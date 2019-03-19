package ru.mobile.proxy;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

/**
 * Created by OAKutsenko on 25.01.2017.
 */
public class CustomErrorFilter extends ZuulFilter {

    private static final Logger LOG = LoggerFactory.getLogger(CustomErrorFilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return -1; // Needs to run before SendErrorFilter which has filterOrder == 0
    }

    @Override
    public boolean shouldFilter() {
        // only forward to errorPath if it hasn't been forwarded to already
        return RequestContext.getCurrentContext().containsKey("error.status_code");
    }

    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            Object e = ctx.get("error.exception");

            if (e != null && e instanceof ZuulException) {
                ZuulException zuulException = (ZuulException)e;
                //LOG.error("Zuul failure detected: " + zuulException.getMessage(), zuulException);

                // Remove error code to prevent further error handling in follow up filters
                ctx.remove("error.status_code");


                final int cmdCode = Integer.parseInt( ctx.getRequest().getRequestURI()
                        .replaceAll(".*/(\\d{4}).*?","$1")) + 1000;

                String path = ctx.getRequest().getServletPath();

                // Populate context with new response values
                ctx.setResponseBody("{\"cmdCode\":"+cmdCode+", \"resultCode\":111, " +
                        "\"errMsg\":\"Service path '" + path + "' temporarily unavailable.\"}");
                ctx.getResponse().setContentType("application/json");
                ctx.setResponseStatusCode(503); //Can set any error code as excepted
            }
        }
        catch (Exception ex) {
            //LOG.error("Exception filtering in custom error filter", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }
}
