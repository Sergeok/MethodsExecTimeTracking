package ok.serge.service;

import lombok.extern.slf4j.Slf4j;
import ok.serge.model.DurationData;
import ok.serge.repository.DurationDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class StatisticSavingService {

    private final DurationDataRepository repository;

    @Autowired
    public StatisticSavingService(DurationDataRepository repository) {
        this.repository = repository;
    }

    @Async(value = "statisticPersistExecutor")
    public void writeData(DurationData data) {
        log.debug("Начало записи в бд");
        internalInsertDataToDatabase(data);
        log.debug("Завершение записи в бд. Id: " + data.getId());
    }

    @Transactional
    public void internalInsertDataToDatabase(DurationData data) {
        repository.save(data);
    }
}
