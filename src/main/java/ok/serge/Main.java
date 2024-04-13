package ok.serge;

import lombok.extern.slf4j.Slf4j;
import ok.serge.utils.LifecycleService;
import ok.serge.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@SpringBootApplication
@Slf4j
public class Main {

    private final Store store;

    @Autowired
    public Main(Store store) {
        this.store = store;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Scheduled(fixedRateString = "${order.rate.in.milliseconds}")
    public void onReady() {
        List<String> order = LifecycleService.generateNameList(Store.PRODUCTS_LABEL, Store.PRODUCTS_MAX_COUNT);
        log.info("Заказанные товары: " + order);
        log.info("Полученные товары: " + store.doShopping(order));
    }
}