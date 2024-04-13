package ok.serge.store;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.AbstractMap;
import java.util.Map;

@Getter
@Setter
@Slf4j
public class Worker {

    private final String name;
    private final long workTime;

    public Worker(String name, long workTime) {
        this.name = name;
        this.workTime = workTime;
    }

    public Map.Entry<Worker, String> bringProduct(String productName) {
        fussOperation(productName);
        locationSearchOperation(productName);
        String product = productSearchOperation(productName);
        deliveryOperation(productName);
        return new AbstractMap.SimpleEntry<>(this, product);
    }

    private void fussOperation(String productName) {
        log.debug(this + ": Анализ задачи " + productName);
        WorkerUtils.wasteTime(workTime / 2);
    }

    private void locationSearchOperation(String productName) {
        log.debug(this + ": Поиск расположения товара на складе " + productName);
    }

    private String productSearchOperation(String productName) {
        log.debug(this + ": Поиск товара на полке " + productName);
        return productName.toLowerCase();
    }

    private void deliveryOperation(String productName) {
        log.debug(this + ": Доставка " + productName);
        WorkerUtils.wasteTime(workTime / 2);
    }

    @Override
    public String toString() {
        return name + "<" + workTime + ">";
    }
}
