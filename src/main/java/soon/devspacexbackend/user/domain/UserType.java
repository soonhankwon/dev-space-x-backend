package soon.devspacexbackend.user.domain;

public enum UserType {
    CANDIDATE(0L),
    PILOT(50L),
    SECOND_PILOT(100L),
    FIRST_PILOT(200L),
    COMMANDER(500L);

    private Long requiredLike;

    UserType(Long requiredLike) {
        this.requiredLike = requiredLike;
    }
}