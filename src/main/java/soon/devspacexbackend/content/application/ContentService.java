package soon.devspacexbackend.content.application;

import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;

public interface ContentService {

    void registerContent(ContentRegisterReqDto dto);
}
