package io.codeidea.apitemplate.common.infrastructure;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class NowTimeHolder implements TimeHolder {
    @Override
    public LocalDateTime getTime() {
        return LocalDateTime.now();
    }
}
