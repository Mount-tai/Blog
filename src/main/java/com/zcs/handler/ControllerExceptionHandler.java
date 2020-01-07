package com.zcs.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


//拦截异常，进行统一处理
//拦截所有标注由@Controller注解类的拦截器
@ControllerAdvice
public class ControllerExceptionHandler {

    //拿到logger日志对象
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * @return ModelAndView :指定返回页面名称
     */
    @ExceptionHandler(Exception.class) //标识此方法是做异常处理的 以及异常级别
    public ModelAndView exceptionHander(HttpServletRequest httpServletRequest,
                                        Exception e) throws Exception {


        //控制台输出异常日志信息
        logger.error("Request URL : {},Exception : {}",httpServletRequest.getRequestURL(),e);


        //判断异常状态是否为空，如果不为空就抛出让springboot处理
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }

        //拿到异常路径及信息
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url",httpServletRequest.getRequestURL());
        modelAndView.addObject("exception",e);
        //返回到指定页面
        modelAndView.setViewName("error/error");
        return modelAndView;
    }

}
