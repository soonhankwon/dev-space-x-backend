package soon.devspacexbackend.utils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Service
public class TransactionService {

    @Transactional
    public <T> void executeAsTransactional(Supplier<T> supplier) {
        supplier.get();
    }
}