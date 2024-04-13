package ok.serge.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Aspect
@Order(1)
@Slf4j
public class LoggingAspect {

    @Around("@annotation(ok.serge.annotation.TrackTime)")
    public Object asyncRunner(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();

        String methodName = pjp.getSignature().getName();
        Object[] methodArgs = pjp.getArgs();

        logMethodStart(methodName, methodArgs);
        Object result = pjp.proceed();
        logMethodFinish(startTime, methodName, methodArgs);

        return result;
    }

    @Around("execution(@ok.serge.annotation.TrackAsyncTime public java.util.concurrent.CompletableFuture* *(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();

        String methodName = pjp.getSignature().getName();
        Object[] methodArgs = pjp.getArgs();

        logMethodStart(methodName, methodArgs);
        Object result = pjp.proceed();
        CompletableFuture<?> future = (CompletableFuture<?>)result;
        future.thenAccept(o -> logMethodFinish(startTime, methodName, methodArgs));

        return future;
    }

    private void logMethodStart(String methodName, Object[] methodArgs) {
        log.info("Выполнение метода {} с аргументами {}", methodName, methodArgs);
    }

    private void logMethodFinish(long startTime, String methodName, Object[] methodArgs) {
        long endTime = System.currentTimeMillis();
        log.info("Метод {} с аргументами {} выполнился за {} мс", methodName, methodArgs, endTime - startTime);
    }
}