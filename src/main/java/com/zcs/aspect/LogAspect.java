package com.zcs.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @description: TODO
 * @author: mufeng
 */
@Aspect   //AOP切面
@Component//开启组件扫描把类交给spring管理
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //指定切面位置
    @Pointcut("execution(* com.zcs.web.*.*(..))")
    public void log() {
    }


    /**
     * @param joinPoint 封装了代理方法信息的对象
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {

        //拿到request对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //获取url
        String url = request.getRequestURL().toString();
        //获取ip
        String ip = request.getRemoteAddr();
        //通过joinPoint对象获取类名方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        //获取请求参数
        Object[] args = joinPoint.getArgs();

        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);

        logger.info("Request : {}",requestLog);
    }

    @After("log()")
    public void doAfter() {
        //logger.info("----------------doAfter---------------");
    }


    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterRuturn(Object result) {
        logger.info("Result : {}", result);

    }

    //创建日志对象
    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
