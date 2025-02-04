package io.codeidea.apitemplate.code.group.service;

import static org.assertj.core.api.Assertions.assertThat;

import io.codeidea.apitemplate.code.group.domain.CodeGroup;
import io.codeidea.apitemplate.code.group.service.port.CodeGroupRepository;
import io.codeidea.apitemplate.code.group.service.response.CodeGroupResponse;
import io.codeidea.apitemplate.common.request.PaginationRequest;
import io.codeidea.apitemplate.mock.FakeCodeGroupRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

class CodeGroupQueryServiceImplTest {

    private CodeGroupQueryService codeGroupQueryService;
    private CodeGroupRepository codeGroupRepository;

    @BeforeEach
    void setUp() {
        codeGroupRepository = new FakeCodeGroupRepository();
        codeGroupQueryService = new CodeGroupQueryServiceImpl(codeGroupRepository);

        codeGroupRepository.save(
                CodeGroup.of(1L, "group1", LocalDateTime.now(), LocalDateTime.now()));
        codeGroupRepository.save(
                CodeGroup.of(2L, "group2", LocalDateTime.now(), LocalDateTime.now()));
        codeGroupRepository.save(
                CodeGroup.of(3L, "group3", LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    void 코드그룹들을_페이징으로_조회할_수_있다_1() {
        // given
        PaginationRequest paginationRequest = new PaginationRequest(0, 2, "id", false);

        // when
        Page<CodeGroupResponse> codeGroups =
                codeGroupQueryService.findByPagination(paginationRequest);

        // then
        assertThat(codeGroups.getTotalElements()).isEqualTo(3);
        assertThat(codeGroups.getTotalPages()).isEqualTo(2);
        assertThat(codeGroups.getContent().size()).isEqualTo(2);
        assertThat(codeGroups.hasNext()).isTrue();
    }

    @Test
    void 코드그룹들을_페이징으로_조회할_수_있다_2() {
        // given
        PaginationRequest paginationRequest = new PaginationRequest(0, -1, "id", false);

        // when
        Page<CodeGroupResponse> codeGroups =
                codeGroupQueryService.findByPagination(paginationRequest);

        // then
        assertThat(codeGroups.getTotalElements()).isEqualTo(3);
        assertThat(codeGroups.getTotalPages()).isEqualTo(1);
        assertThat(codeGroups.getContent().size()).isEqualTo(3);
        assertThat(codeGroups.hasNext()).isFalse();
    }
}
