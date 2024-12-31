package io.codeidea.apitemplate.mock;

import io.codeidea.apitemplate.common.infrastructure.TimeHolder;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestTimeHolder implements TimeHolder {

    private final LocalDateTime time;

    @Override
    public LocalDateTime getTime() {
        return time;
    }
}
