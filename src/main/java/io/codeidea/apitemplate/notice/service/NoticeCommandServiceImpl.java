package io.codeidea.apitemplate.notice.service;

import io.codeidea.apitemplate.common.infrastructure.TimeHolder;
import io.codeidea.apitemplate.notice.domain.Notice;
import io.codeidea.apitemplate.notice.domain.NoticeCreate;
import io.codeidea.apitemplate.notice.service.port.NoticeRepository;
import io.codeidea.apitemplate.notice.service.response.NoticeResponse;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeCommandServiceImpl implements NoticeCommandService {

    private final NoticeRepository noticeRepository;
    private final TimeHolder timeHolder;

    @Override
    public NoticeResponse create(NoticeCreate noticeCreate) {
        LocalDateTime now = timeHolder.getTime();
        return new NoticeResponse(noticeRepository.save(Notice.create(noticeCreate, now, now)));
    }

    @Override
    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }
}
