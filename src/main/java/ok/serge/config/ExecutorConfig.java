package ok.serge.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ExecutorConfig {

    @Bean
    @Qualifier(value = "statisticPersistExecutor")
    public Executor statisticPersistExecutor(@Value("${statistic.executor.pool.size}") int poolSize) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("stat-thread-");
        threadPoolTaskExecutor.setCorePoolSize(poolSize);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    @Bean
    @Qualifier(value = "workerExecutor")
    public Executor workerExecutor(@Value("${worker.executor.pool.size}") int poolSize) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("work-thread-");
        threadPoolTaskExecutor.setCorePoolSize(poolSize);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
