package soon.devspacexbackend.content.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.content.infrastructure.persistence.ContentRepository;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;

@RequiredArgsConstructor
@Service
public class ContentServiceImpl implements ContentService{

    private final ContentRepository contentRepository;

    @Override
    public void registerContent(ContentRegisterReqDto dto) {
        Content content = new Content(dto);
        contentRepository.save(content);
    }
}
