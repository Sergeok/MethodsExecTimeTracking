package ok.serge.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ExecutorConfig {

    @Bean
    @Qualifier(value = "statisticPersistExecutor")
    public Executor statisticPersistExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("stat-thread-");
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    @Bean
    @Qualifier(value = "workerExecutor")
    public Executor workerExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("work-thread-");
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
