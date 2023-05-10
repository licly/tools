package org.charlie.ratelimiter;


import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Charlie-6327
 * @date 2023/5/10
 */
public class FixedTimeRateLimitAlg implements RateLimitAlg {

    // 200ms
    private static final long TRY_LOCK_TIMEOUT = 200L;

    private StopWatch stopWatch;
    private AtomicInteger currentCount = new AtomicInteger();
    private final int limit;
    private Lock lock = new ReentrantLock();

    public FixedTimeRateLimitAlg(int limit) {
        this.limit = limit;
    }

    public FixedTimeRateLimitAlg(int limit, StopWatch stopWatch) {
        this.limit = limit;
        this.stopWatch = stopWatch;
    }

    @Override
    public boolean tryAcquire() {
        int updatedCount = currentCount.incrementAndGet();
        if (updatedCount <= limit) {
            return true;
        }

        try {
            if (lock.tryLock(TRY_LOCK_TIMEOUT, TimeUnit.MILLISECONDS)) {
                try {
                    if (stopWatch.getLastTaskTimeMillis() > TimeUnit.SECONDS.toMillis(1)) {
                        currentCount.set(0);
                        stopWatch.stop();
                    }

                    return currentCount.incrementAndGet() <= limit;
                }  finally {
                    lock.unlock();
                }
            } else {
                throw new RuntimeException("tryAcquire() wait lock too long:" + TRY_LOCK_TIMEOUT + "ms");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("tryAcquire() is interrupted by lock-time-out.", e);
        }
    }
}
