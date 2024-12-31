package io.codeidea.apitemplate.common.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
public class PaginationRequest {

    @NotNull private Integer page;

    @NotNull private Integer itemsPerPage;

    @NotBlank private String sortBy;

    @NotNull private Boolean sortDesc;

    private Sort getSort() {
        return Sort.by(this.sortDesc ? Sort.Order.desc(this.sortBy) : Sort.Order.asc(this.sortBy));
    }

    public PageRequest getPageRequest() {
        return PageRequest.of(this.page - 1, this.itemsPerPage, this.getSort());
    }
}
