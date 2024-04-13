package ok.serge.aspect;

import lombok.extern.slf4j.Slf4j;
import ok.serge.utils.MethodExecType;
import ok.serge.model.DurationData;
import ok.serge.service.StatisticSavingService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;

@Component
@Aspect
@Order(2)
@Slf4j
public class PersistAspect {

    private final StatisticSavingService savingService;

    @Autowired
    public PersistAspect(StatisticSavingService savingService) {
        this.savingService = savingService;
    }

    @Around("@annotation(ok.serge.timetrack.TrackTime))")
    public Object aroundSyncMethods(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        LocalDateTime startTime = LocalDateTime.now();

        Object result = pjp.proceed();
        writeData(methodName, MethodExecType.SYNC.name(), startTime);

        return result;
    }

    @Around("@annotation(ok.serge.timetrack.TrackAsyncTime))")
    public Object aroundAsyncMethods(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        LocalDateTime startTime = LocalDateTime.now();

        Object result = pjp.proceed();
        CompletableFuture<?> future = (CompletableFuture<?>) result;
        future.thenAccept(o -> writeData(methodName, MethodExecType.ASYNC.name(), startTime));

        return future;
    }

    private void writeData(String methodName, String execType, LocalDateTime startTime) {
        LocalDateTime endTime = LocalDateTime.now();

        DurationData data = new DurationData();
        data.setMethodName(methodName);
        data.setExecType(execType);
        data.setStartTime(startTime);
        data.setFinishTime(endTime);
        data.setDurationInMillis(ChronoUnit.MILLIS.between(startTime, endTime));

        savingService.writeData(data);
    }
}
