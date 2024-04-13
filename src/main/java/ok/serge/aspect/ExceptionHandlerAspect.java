package ok.serge.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Aspect
@Slf4j
public class ExceptionHandlerAspect {

    @Around("execution(@ok.serge.timetrack.ProblemPart public java.util.Map* *(..))")
    public Object asyncRunner(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (Exception e) {
            return Map.of("Ошибка", "Функция перемещена или более недоступна");
        }
    }
}
