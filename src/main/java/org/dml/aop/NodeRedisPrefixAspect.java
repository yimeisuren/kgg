package org.dml.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.dml.annotation.NodeRedisPrefixAnnotation;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NodeRedisPrefixAspect {
    @Pointcut("@annotation(org.dml.annotation.NodeRedisPrefixAnnotation)")
    public void pointCut() {

    }

    /**
     * 通过joinPoint来获取当前执行方法的形参, 通过注解来获取设置的前缀值
     *
     * @param joinPoint
     * @param nodeRedisPrefixAnnotation
     * @return
     */
    @Around(value = "pointCut() && args(nodeRedisPrefixAnnotation)")
    public Object beforeDone(ProceedingJoinPoint joinPoint, NodeRedisPrefixAnnotation nodeRedisPrefixAnnotation) {
        String prefix = nodeRedisPrefixAnnotation.value();

        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String) {
                args[i] = prefix + args[i];
            }
        }

        //  调用被代理方法
        Object result = null;
        try {
            result = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
}
