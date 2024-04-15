package com.virginonline.mediasoft.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
public class ExecutionTimeAspect {

  @Around("!execution(* *(..)) && @annotation(com.virginonline.mediasoft.annotation.Timed)")
  public Object executionTime(ProceedingJoinPoint point) throws Throwable {
    var stopwatch =
        new StopWatch(
            "Benchmarking method: %s | Class: %s"
                .formatted(
                    point.getSignature().getName(), point.getSignature().getDeclaringTypeName()));
    stopwatch.start();
    Object object = point.proceed();
    stopwatch.stop();
    log.info(
        "Class Name: {}. Method: {}. Time elapsed: {}ms",
        point.getSignature().getDeclaringTypeName(),
        point.getSignature().getName(),
        stopwatch.getTotalTimeMillis());
    return object;
  }
}
