package io.codeidea.apitemplate.code.service;

import static org.assertj.core.api.Assertions.assertThat;

import io.codeidea.apitemplate.code.domain.Code;
import io.codeidea.apitemplate.code.group.domain.CodeGroup;
import io.codeidea.apitemplate.code.service.port.CodeRepository;
import io.codeidea.apitemplate.code.service.response.CodeResponse;
import io.codeidea.apitemplate.common.request.PaginationRequest;
import io.codeidea.apitemplate.mock.FakeCodeRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

class CodeQueryServiceImplTest {

    private CodeQueryService codeQueryService;
    private CodeRepository codeRepository;

    @BeforeEach
    void setUp() {
        codeRepository = new FakeCodeRepository();
        codeQueryService = new CodeQueryServiceImpl(codeRepository);

        CodeGroup codeGroup1 = CodeGroup.of(1L, "group1", LocalDateTime.now(), LocalDateTime.now());
        CodeGroup codeGroup2 = CodeGroup.of(2L, "group2", LocalDateTime.now(), LocalDateTime.now());

        codeRepository.save(
                Code.of(
                        null,
                        "code1",
                        "code1",
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        codeGroup1));

        codeRepository.save(
                Code.of(
                        null,
                        "code2",
                        "code2",
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        codeGroup1));

        codeRepository.save(
                Code.of(
                        null,
                        "code3",
                        "code3",
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        codeGroup2));
    }

    @Test
    void 코드들을_페이징으로_조회할_수_있다_1() {
        // given
        PaginationRequest paginationRequest = new PaginationRequest(0, 2, "id", false);

        // when
        Page<CodeResponse> codes = codeQueryService.findByPagination(paginationRequest);

        // then
        assertThat(codes.getTotalElements()).isEqualTo(3);
        assertThat(codes.getTotalPages()).isEqualTo(2);
        assertThat(codes.getContent().size()).isEqualTo(2);
        assertThat(codes.hasNext()).isTrue();
    }

    @Test
    void 코드들을_페이징으로_조회할_수_있다_2() {
        // given
        PaginationRequest paginationRequest = new PaginationRequest(0, -1, "id", false);

        // when
        Page<CodeResponse> codes = codeQueryService.findByPagination(paginationRequest);

        // then
        assertThat(codes.getTotalElements()).isEqualTo(3);
        assertThat(codes.getTotalPages()).isEqualTo(1);
        assertThat(codes.getContent().size()).isEqualTo(3);
        assertThat(codes.hasNext()).isFalse();
    }
}
