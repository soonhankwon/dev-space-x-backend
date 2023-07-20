package soon.devspacexbackend.content.domain;

public enum ContentPayType {

    FREE, PAY;

    void validateTypeMatchDarkMatter(Integer darkMatter) {
        if(this == FREE)
            validateFreeTypeDarkMatter(darkMatter);
        else
            validatePayTypeDarkMatter(darkMatter);
    }

    private void validateFreeTypeDarkMatter(Integer darkMatter) {
        if(!isFreeTypeDarkMatterValid(darkMatter))
            throw new IllegalArgumentException("invalid matter value");
    }

    private void validatePayTypeDarkMatter(Integer darkMatter) {
        if(!isPayTypeDarkMatterValid(darkMatter))
            throw new IllegalArgumentException("invalid matter value");
    }

    private boolean isFreeTypeDarkMatterValid(Integer darkMatter) {
        return darkMatter == Content.FREE_MATTER_VALUE;
    }

    private boolean isPayTypeDarkMatterValid(Integer darkMatter) {
        return darkMatter >= Content.MIN_PAY_MATTER_VALUE && darkMatter <= Content.MAX_PAY_MATTER_VALUE;
    }
}
