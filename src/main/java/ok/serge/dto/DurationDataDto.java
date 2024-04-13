package ok.serge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DurationDataDto {

    private Long id;

    private String methodName;

    private String execType;

    private Long durationInNano;

    private LocalDateTime startTime;

    private LocalDateTime finishTime;
}
