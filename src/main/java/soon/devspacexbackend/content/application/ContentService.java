package soon.devspacexbackend.content.application;

import org.springframework.data.domain.Pageable;
import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;
import soon.devspacexbackend.content.presentation.dto.ContentUpdateReqDto;
import soon.devspacexbackend.user.domain.User;

import java.util.List;

public interface ContentService {

    void registerContent(ContentRegisterReqDto dto, User loginUser);

    List<ContentGetResDto> getAllContent(Pageable pageable);

    ContentGetResDto getContent(Long contentId, User loginUser);

    void updateContent(Long contentId, ContentUpdateReqDto dto, User loginUser);

    void deleteContent(Long contentId, User loginUser);
}
