package ok.serge.repository;

import ok.serge.model.DurationData;
import ok.serge.model.view.FuncDataView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DurationDataRepository extends JpaRepository<DurationData, Long>, JpaSpecificationExecutor<DurationData> {

    List<DurationData> findByExecType(String execType);

    List<DurationData> findByMethodName(String methodName);

    @Query("SELECT data.methodName as methodName, AVG(data.durationInMillis) as funcResult FROM DurationData data group by data.methodName")
    List<FuncDataView> calcAvgTimeInMillis();

    @Query("SELECT data.methodName as methodName, SUM(data.durationInMillis) as funcResult FROM DurationData data group by data.methodName")
    List<FuncDataView> calcSumTimeInMillis();
}
