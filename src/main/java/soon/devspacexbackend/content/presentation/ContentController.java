package soon.devspacexbackend.content.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import soon.devspacexbackend.content.application.ContentService;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterResDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contents")
@Tag(name = "컨텐츠 관련 API")
public class ContentController {

    private final ContentService contentServiceImpl;

    @PostMapping
    @Operation(summary = "컨텐츠 등록 API")
    @ResponseStatus(HttpStatus.CREATED)
    public ContentRegisterResDto registerContent(@RequestBody ContentRegisterReqDto dto) {
        contentServiceImpl.registerContent(dto);
        return new ContentRegisterResDto();
    }
}
