package ok.serge.service;

import ok.serge.annotation.ProblemPart;
import ok.serge.utils.MethodExecType;
import ok.serge.model.DurationData;
import ok.serge.model.view.FuncDataView;
import ok.serge.repository.DurationDataRepository;
import ok.serge.utils.StatisticFuncType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticAccessService {

    private final DurationDataRepository repository;

    @Autowired
    public StatisticAccessService(DurationDataRepository repository) {
        this.repository = repository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<DurationData> getFullStatistic() {
        return repository.findAll();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<DurationData> getByMethodName(String methodName) {
        return repository.findByMethodName(methodName);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<DurationData> findByExecType(MethodExecType methodType) {
        return repository.findByExecType(methodType.name());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<FuncDataView> calcTimeInMillis(StatisticFuncType func, String methodType) {
        try {
            return switch (func) {
                case AVG ->
                        pickMethodIfRequired(repository.calcAvgTimeInMillis(), methodType);
                case SUM ->
                        pickMethodIfRequired(repository.calcSumTimeInMillis(), methodType);
            };
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Функционал не поддерживается");
        }
    }

    private List<FuncDataView> pickMethodIfRequired(List<FuncDataView> dataViews, String methodName) {
        if (methodName != null) {
            return dataViews.stream()
                    .filter(data -> methodName.equals(data.getMethodName()))
                    .collect(Collectors.toList());
        } else {
            return dataViews;
        }
    }

    @Deprecated
    @ProblemPart
    public Map<String, Object> someFeature() {
        throw new RuntimeException("Больше не поддерживается");
    }
}
