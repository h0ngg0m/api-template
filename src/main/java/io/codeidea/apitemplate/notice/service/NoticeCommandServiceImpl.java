package io.codeidea.apitemplate.notice.service;

import io.codeidea.apitemplate.notice.domain.NoticeCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeCommandServiceImpl implements NoticeCommandService {

    @Override
    public void create(NoticeCreate noticeCreate) {}
}
