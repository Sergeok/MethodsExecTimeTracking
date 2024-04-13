package ok.serge.store;

import ok.serge.utils.LifecycleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.Queue;

@Configuration
public class WorkerConfig {

    private static final String WORKERS_LABEL = "Работник";
    private static final int WORKERS_MAX_COUNT = 6;
    private static final int WORK_TIME_MIN = 500;
    private static final int WORK_TIME_MAX = 1500;

    @Bean
    public Queue<Worker> getWorkers() {
        Queue<Worker> workerQueue = new LinkedList<>();
        for (String workerName : LifecycleService.generateNameList(WORKERS_LABEL, WORKERS_MAX_COUNT)) {
            long workTime = LifecycleService.getRandInt(WORK_TIME_MIN, WORK_TIME_MAX);
            workerQueue.add(
                    new Worker(workerName, workTime)
            );
        }
        return workerQueue;
    }
}
