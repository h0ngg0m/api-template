package io.codeidea.apitemplate.code.service;

import io.codeidea.apitemplate.code.domain.Code;
import io.codeidea.apitemplate.code.domain.CodeUpdate;
import io.codeidea.apitemplate.code.service.port.CodeRepository;
import io.codeidea.apitemplate.code.service.response.CodeResponse;
import io.codeidea.apitemplate.codegroup.domain.CodeGroup;
import io.codeidea.apitemplate.codegroup.service.port.CodeGroupRepository;
import io.codeidea.apitemplate.mock.FakeCodeGroupRepository;
import io.codeidea.apitemplate.mock.FakeCodeRepository;
import io.codeidea.apitemplate.mock.TestTimeHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


class CodeCommandServiceImplTest {

    private CodeCommandService codeCommandService;
    private CodeRepository codeRepository;
    private CodeGroupRepository codeGroupRepository;

    private Code sampleCode;
    private CodeGroup sampleCodeGroup1;
    private CodeGroup sampleCodeGroup2;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();


    @BeforeEach
    void setUp() {
        codeRepository = new FakeCodeRepository();
        codeGroupRepository = new FakeCodeGroupRepository();
        codeCommandService = new CodeCommandServiceImpl(codeRepository, codeGroupRepository, new TestTimeHolder(updatedAt));

        sampleCodeGroup1 = codeGroupRepository.save(CodeGroup.of(1L, "group1", createdAt, createdAt));
        sampleCodeGroup2 = codeGroupRepository.save(CodeGroup.of(2L, "group2", createdAt, createdAt));
        sampleCode = codeRepository.save(Code.of(1L, "code1", "code1", createdAt, createdAt, sampleCodeGroup1));
    }

    @Test
    void 코드의_정보를_수정할_수_있다() {
        // given
        CodeUpdate codeUpdate = new CodeUpdate("title2", "value2", 2L);

        // when
        CodeResponse codeResponse = codeCommandService.update(1L, codeUpdate);

        // then
        assertThat(codeResponse.title()).isEqualTo("title2");
        assertThat(codeResponse.value()).isEqualTo("value2");
        assertThat(codeResponse.createdAt()).isEqualTo(createdAt);
        assertThat(codeResponse.updatedAt()).isEqualTo(updatedAt);

        CodeGroup newCodeGroup = codeResponse.codeGroup();
        assertThat(newCodeGroup.getId()).isEqualTo(2L);
        assertThat(newCodeGroup.getTitle()).isEqualTo("group2");
    }
}