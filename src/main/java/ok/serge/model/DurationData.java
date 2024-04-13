package ok.serge.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "duration_data")
@Getter
@Setter
public class DurationData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String methodName;

    @Column
    private String execType;

    @Column
    private Long durationInMillis;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime finishTime;
}
