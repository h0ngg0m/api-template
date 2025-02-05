package io.codeidea.apitemplate.code.service;

import io.codeidea.apitemplate.code.domain.CodeUpdate;
import io.codeidea.apitemplate.code.service.response.CodeResponse;

public interface CodeCommandService {

    CodeResponse update(Long id, CodeUpdate codeUpdate);
}
