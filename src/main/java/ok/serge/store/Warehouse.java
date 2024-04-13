package ok.serge.store;

import ok.serge.timetrack.TrackAsyncTime;
import ok.serge.timetrack.TrackTime;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;

@Component
public class Warehouse {

    @TrackTime
    public List<Map.Entry<Worker, String>> receiveWorkersWithProducts(List<CompletableFuture<Map.Entry<Worker, String>>> futureQueue) {
        return futureQueue.stream()
                .map(CompletableFuture::join)
                .toList();
    }

    @Async(value = "workerExecutor")
    @TrackAsyncTime
    public CompletableFuture<Map.Entry<Worker, String>> delegateWork(Worker worker, String productName) {
        if (worker == null || productName == null) {
            throw new RuntimeException("Отсутствует назначаемый работник или заказываемый продукт");
        }
        return CompletableFuture.completedFuture(
                worker.bringProduct(productName)
        );
    }
}
