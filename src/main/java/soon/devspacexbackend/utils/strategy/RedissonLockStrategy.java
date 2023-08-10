package soon.devspacexbackend.utils.strategy;

@FunctionalInterface
public interface RedissonLockStrategy {

    void call();
}
