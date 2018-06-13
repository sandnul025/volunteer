package com.bankledger.config;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2017年12月5日 下午5:03:01
 */
@Aspect
@Component
public class WebLogAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ThreadLocal<Long> starTime = new ThreadLocal<>();

    @Pointcut(value = "execution(* com.sandnul.controller..*(..))")
    public void pointCut() {
    }

    @Before(value = "pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        logger.info("#################### WebLogAspect ####################");
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("#### ClassName/MethodName: {}/{}", className, methodName);
        logger.info("#### ===>");
        getArgs(joinPoint);
        logger.info("#### <===");
        logger.info("#### ");
        starTime.set(System.currentTimeMillis());
    }

    @AfterReturning(value = "pointCut()", returning = "returnValue")
    public void doAfterReturn(JoinPoint joinPoint, Object returnValue) {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("#### ");
        logger.info("#### execute time: {}", System.currentTimeMillis() - starTime.get());
        logger.info("#### {}/{} returnValue: {}", className, methodName, JSONObject.toJSONString(returnValue));
        logger.info("#################### WebLogAspect ####################");
    }

    // 获取所有的参数名和值，组装为String
    private void getArgs(JoinPoint joinPoint) {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            logger.info("#### {}: {}", parameterNames[i],
                    joinPoint.getArgs()[i] == null ? null : joinPoint.getArgs()[i].toString());
        }
    }

}
