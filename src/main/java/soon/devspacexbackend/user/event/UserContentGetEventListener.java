package soon.devspacexbackend.user.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.exception.ApiException;
import soon.devspacexbackend.exception.CustomErrorCode;
import soon.devspacexbackend.user.domain.BehaviorType;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.domain.UserContent;
import soon.devspacexbackend.user.infrastructure.persistence.UserContentRepository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserContentGetEventListener {

    private final UserContentRepository userContentRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserContentGetEventCaseByExistRecordType(UserContentGetEvent event) {
        log.info("EVENT ON");

        if(event.getBehaviorType() == BehaviorType.POST) {
            Optional<UserContent> optionalUserContent = userContentRepository.findUserContentByContentAndUserAndType(event.getContent(), event.getUser(), BehaviorType.GET);
            if (optionalUserContent.isPresent()) {
                optionalUserContent.get().setModifiedAtNow();
            }
            else {
                saveContentGetRecordByUser(event.getUser(), event.getContent());
            }
        } else {
            UserContent userContent = userContentRepository.findUserContentByContentAndUserAndType(event.getContent(), event.getUser(), BehaviorType.GET)
                    .orElseThrow(() -> new ApiException(CustomErrorCode.DB_DATA_ERROR));
            userContent.setModifiedAtNow();
        }
        log.info("EVENT OFF");
    }

    private void saveContentGetRecordByUser(User loginUser, Content content) {
        userContentRepository.save(new UserContent(loginUser, content, BehaviorType.GET));
    }
}
