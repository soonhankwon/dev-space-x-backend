package soon.devspacexbackend.utils.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import soon.devspacexbackend.exception.ApiException;
import soon.devspacexbackend.exception.CustomErrorCode;
import soon.devspacexbackend.user.domain.User;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedissonLockContext<T> {

    private final RedissonClient redissonClient;

    public void executeLock(T dto, User loginUser, RedissonLockStrategy strategy) {
        RLock lock = redissonClient.getLock(String.valueOf(loginUser.getId()));
        try {
            boolean available = lock.tryLock(0, 1, TimeUnit.SECONDS);
            if(!available) {
                throw new ApiException(CustomErrorCode.CANT_GET_LOCK);
            }
            log.info("loginUser lock={}", lock);
            strategy.call();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
