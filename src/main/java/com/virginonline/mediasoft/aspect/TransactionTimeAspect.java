package com.virginonline.mediasoft.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class TransactionTimeAspect {

  @Before(
      "@annotation(com.virginonline.mediasoft.annotation.Timed) && (@within(org.springframework.transaction.annotation.Transactional) || @annotation(org.springframework.transaction.annotation.Transactional))")
  public void monitor(JoinPoint pjp) {
    var className = pjp.getTarget().getClass().getSimpleName();
    var stopwatch = new StopWatch("Transaction in class=" + className);
    stopwatch.start();
    log.info("Transaction method={} started", className);
    TransactionSynchronizationManager.registerSynchronization(
        new TransactionSynchronization() {
          @Override
          public void afterCommit() {
            stopwatch.stop();
            log.info(
                "Transaction method={} ended | tookMs={}",
                className,
                stopwatch.getTotalTimeMillis());
          }
        });
  }
}
