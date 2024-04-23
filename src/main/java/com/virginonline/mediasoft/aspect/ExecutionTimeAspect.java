package com.virginonline.mediasoft.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class ExecutionTimeAspect {

  @Around("execution(* *(..)) && @annotation(com.virginonline.mediasoft.annotation.Timed)")
  public Object executionTime(ProceedingJoinPoint point) throws Throwable {
    var stopwatch = new StopWatch();
    stopwatch.start();
    Object object;
    try {
      object = point.proceed();
    } catch (Throwable e) {
      log.error(e.getMessage(), e);
      throw e;
    } finally {
      stopwatch.stop();
      log.info(
          "Class Name: {}. Method: {}. Time elapsed: {}ms",
          point.getSignature().getDeclaringTypeName(),
          point.getSignature().getName(),
          stopwatch.getTotalTimeMillis());
    }
    return object;
  }
}
