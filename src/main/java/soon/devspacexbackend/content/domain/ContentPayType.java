package soon.devspacexbackend.content.domain;

import soon.devspacexbackend.exception.CustomErrorCode;

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
            throw new IllegalArgumentException(CustomErrorCode.FREE_TYPE_MATTER_INVALID.getMessage());
    }

    private void validatePayTypeDarkMatter(Integer darkMatter) {
        if(!isPayTypeDarkMatterValid(darkMatter))
            throw new IllegalArgumentException(CustomErrorCode.PAY_TYPE_MATTER_INVALID.getMessage());
    }

    private boolean isFreeTypeDarkMatterValid(Integer darkMatter) {
        return darkMatter == Content.FREE_MATTER_VALUE;
    }

    private boolean isPayTypeDarkMatterValid(Integer darkMatter) {
        return darkMatter >= Content.MIN_PAY_MATTER_VALUE && darkMatter <= Content.MAX_PAY_MATTER_VALUE;
    }
}
