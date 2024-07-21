package com.yicj.study.component;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import com.yicj.study.enums.LogAction;
import com.yicj.study.enums.XiaoBaWangLog;
import com.yicj.study.model.SysLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用spring aop，实现拦截用户请求，记录用户日志。比如ip、请求参数、请求用户等信息，需要配合下面的XiaoBaWangLog注解使用。
 * 这里拦截上面所说的第二种日志类型。
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    LoggerBuilder loggerBuilder;

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    private SysLog sysLog;

    @Pointcut("@annotation(com.yicj.study.enums.XiaoBaWangLog)")
    public void pointcut() {
    }

    /**
     * 前置方法执行
     *
     * @param joinPoint
     */
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        //获取请求的request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String clientIP = ServletUtil.getClientIP(request, null);
        if ("0.0.0.0".equals(clientIP) || "0:0:0:0:0:0:0:1".equals(clientIP) || "localhost".equals(clientIP) || "127.0.0.1".equals(clientIP)) {
            clientIP = "127.0.0.1";
        }
        sysLog = new SysLog();
        sysLog.setIp(clientIP);
        String requestParams = JSONUtil.toJsonStr(getRequestParams(request));
        sysLog.setRequestParams(requestParams.length() > 5000 ? ("请求参数过长，参数长度为：" + requestParams.length()) : requestParams);
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        String logName = method.getAnnotation(XiaoBaWangLog.class).value();
        sysLog.setLogName(logName);
        sysLog.setUserAgent(request.getHeader("User-Agent"));
        String fullUrl = request.getRequestURL().toString();
        if (request.getQueryString() != null && !"".equals(request.getQueryString())) {
            fullUrl = request.getRequestURL().toString() + "?" + request.getQueryString();
        }
        sysLog.setRequestUrl(fullUrl);
        sysLog.setRequestMethod(request.getMethod());
        //tkSysLog.setUsername(JwtUtils.getUsername());
    }

    /**
     * 方法返回后执行
     *
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "pointcut()")
    public void after(Object ret) {
        Logger logger = loggerBuilder.getLogger(LogAction.USER_ACTION);
        String retJsonStr = JSONUtil.toJsonStr(ret);
        if (retJsonStr != null) {
            sysLog.setResponseInfo(retJsonStr.length() > 5000 ? ("响应参数过长，参数长度为：" + retJsonStr.length()) : retJsonStr);
        }
        sysLog.setUseTime(System.currentTimeMillis() - startTime.get());
        logger.info(JSONUtil.toJsonStr(sysLog));
    }

    /**
     * 环绕通知,收集方法执行期间的错误信息
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            Object obj = proceedingJoinPoint.proceed();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            sysLog.setExceptionInfo(e.getMessage());
            Logger logger = loggerBuilder.getLogger(LogAction.USER_ACTION);
            logger.error(JSONUtil.toJsonStr(sysLog));
            throw e;
        }
    }

    /**
     * 获取请求的参数
     *
     * @param request
     * @return
     */
    private Map getRequestParams(HttpServletRequest request) {
        Map map = new HashMap();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }


}
