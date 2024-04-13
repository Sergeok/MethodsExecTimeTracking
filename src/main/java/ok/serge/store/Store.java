package ok.serge.store;

import ok.serge.timetrack.TrackTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;

@Component
public class Store {

    public static final String PRODUCTS_LABEL = "Продукт";
    public static final int PRODUCTS_MAX_COUNT = 10;

    private final Warehouse warehouse;

    private final Queue<Worker> workerQueue;

    @Autowired
    public Store(Queue<Worker> workerQueue, Warehouse warehouse) {
        this.workerQueue = workerQueue;
        this.warehouse = warehouse;
    }

    @TrackTime
    public List<String> doShopping(List<String> order) {
        if (workerQueue.isEmpty()) {
            throw new RuntimeException("На складе не оказалось ни одного работника!");
        }

        List<String> result = new ArrayList<>();
        List<CompletableFuture<Map.Entry<Worker, String>>> distributionPlan = new LinkedList<>();

        for (String productName : order) {
            if (workerQueue.isEmpty()) {
                workAcceptance(distributionPlan, result);
            }
            workAssignment(distributionPlan, productName);
        }
        workAcceptance(distributionPlan, result);

        return result;
    }

    private void workAssignment(List<CompletableFuture<Map.Entry<Worker, String>>> distributionPlan, String productName) {
        distributionPlan.add(
                warehouse.delegateWork(workerQueue.poll(), productName)
        );
    }

    private void workAcceptance(List<CompletableFuture<Map.Entry<Worker, String>>> distributionPlan, List<String> result) {
        List<Map.Entry<Worker, String>> workersWithProducts = warehouse.receiveWorkersWithProducts(distributionPlan);
        result.addAll(
                workersWithProducts.stream()
                        .map(Map.Entry::getValue)
                        .toList()
        );
        workerQueue.addAll(
                workersWithProducts.stream()
                        .map(Map.Entry::getKey)
                        .toList()
        );
        distributionPlan.clear();
    }
}
