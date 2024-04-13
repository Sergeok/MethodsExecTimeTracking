package ok.serge.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class LifecycleService {

    private static int sessionSeed = LocalDateTime.now().getSecond();
    private static final Random random = new Random(sessionSeed);

    static {
        log.info("Session seed: " + sessionSeed);
    }

    @Value("${session.seed}")
    public void setSessionSeed(Integer seed) {
        try {
            if (seed != null) {
                sessionSeed = seed;
                log.info("Session seed (Мануальное назначение): " + sessionSeed);
            }
        } catch (Exception e) {
            log.error("Мануальное назначение Session seed завершилось ошибкой. Session seed: " + sessionSeed, e);
        }
    }

    public static List<String> generateNameList(String nameBase, int upperBound) {
        List<String> nameList = new ArrayList<>();
        int count = sessionSeed % upperBound + 1;
        for (int i = 0; i < count; i++) {
            nameList.add(nameBase + "<" + i + ">");
        }
        return nameList;
    }

    public static int getRandInt(int lowerBound, int upperBound) {
        return random.nextInt(lowerBound,upperBound);
    }
}
