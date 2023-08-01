package soon.devspacexbackend.user.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.user.domain.BehaviorType;
import soon.devspacexbackend.user.domain.User;

@Getter
@AllArgsConstructor
public final class UserContentGetEvent {

    private final User user;
    private final Content content;
    private final BehaviorType behaviorType;
}
