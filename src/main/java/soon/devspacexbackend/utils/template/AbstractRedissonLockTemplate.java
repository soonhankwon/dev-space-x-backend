package soon.devspacexbackend.utils.template;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import soon.devspacexbackend.exception.ApiException;
import soon.devspacexbackend.exception.CustomErrorCode;
import soon.devspacexbackend.user.domain.User;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractRedissonLockTemplate<T> {

    private final RedissonClient redissonClient;

    public void executeLock(T dto, User loginUser) {
        RLock lock = redissonClient.getLock(String.valueOf(loginUser.getId()));
        try {
            boolean available = lock.tryLock(0, 1, TimeUnit.SECONDS);
            if(!available) {
                throw new ApiException(CustomErrorCode.CANT_GET_LOCK);
            }
            log.info("loginUser lock={}", lock);
            call(dto, loginUser);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void call(T dto, User loginUser);
}
