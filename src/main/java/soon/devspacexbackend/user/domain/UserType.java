package soon.devspacexbackend.user.domain;

import lombok.Getter;

@Getter
public enum UserType {
    CANDIDATE(0L, 50L),
    PILOT(50L, 100L),
    SECOND_PILOT(100L, 200L),
    FIRST_PILOT(200L, 500L),
    COMMANDER(500L, Long.MAX_VALUE),
    ADMIN(-1234L, -1234L);

    private final Long requiredExp;
    private final Long nextRequiredExp;

    UserType(Long requiredExp, Long nextRequiredExp) {
        this.requiredExp = requiredExp;
        this.nextRequiredExp = nextRequiredExp;
    }

    public static UserType ofAvailableUpgradeType(Long exp) {
        if(exp >= PILOT.requiredExp && exp < SECOND_PILOT.requiredExp) {
            return PILOT;
        }
        else if (exp >= SECOND_PILOT.requiredExp && exp < FIRST_PILOT.requiredExp) {
            return SECOND_PILOT;
        }
        else if (exp >= FIRST_PILOT.requiredExp && exp < COMMANDER.requiredExp) {
            return FIRST_PILOT;
        }
        else {
            return COMMANDER;
        }
    }
}