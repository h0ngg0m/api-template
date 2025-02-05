package io.codeidea.apitemplate.code.service;

import io.codeidea.apitemplate.code.domain.Code;
import io.codeidea.apitemplate.code.domain.CodeUpdate;
import io.codeidea.apitemplate.code.service.port.CodeRepository;
import io.codeidea.apitemplate.code.service.response.CodeResponse;
import io.codeidea.apitemplate.codegroup.domain.CodeGroup;
import io.codeidea.apitemplate.codegroup.service.port.CodeGroupRepository;
import io.codeidea.apitemplate.common.exception.custom.CustomException;
import io.codeidea.apitemplate.common.infrastructure.TimeHolder;
import io.codeidea.apitemplate.common.response.ApiResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CodeCommandServiceImpl implements CodeCommandService {

    private final CodeRepository codeRepository;
    private final CodeGroupRepository codeGroupRepository;
    private final TimeHolder timeHolder;

    @Override
    public CodeResponse update(Long id, CodeUpdate codeUpdate) {
        Code code =
                codeRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new CustomException(
                                                ApiResponseCode.CODE_NOT_FOUND.customMessage(
                                                        "The code cannot be found. id: " + id)));

        CodeGroup codeGroup =
                codeGroupRepository
                        .findById(codeUpdate.codeGroupId())
                        .orElseThrow(
                                () ->
                                        new CustomException(
                                                ApiResponseCode.CODE_GROUP_NOT_FOUND.customMessage(
                                                        "The code group cannot be found. id: "
                                                                + codeUpdate.codeGroupId())));

        return new CodeResponse(
                codeRepository.save(code.update(codeUpdate, codeGroup, timeHolder.getTime())));
    }
}
