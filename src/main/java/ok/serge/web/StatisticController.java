package ok.serge.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ok.serge.utils.MethodExecType;
import ok.serge.dto.DurationDataDto;
import ok.serge.dto.FuncDataDto;
import ok.serge.model.DurationData;
import ok.serge.model.view.FuncDataView;
import ok.serge.service.StatisticAccessService;
import ok.serge.utils.StatisticFuncType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "statistic", produces = "application/json")
public class StatisticController {

    private final StatisticAccessService accessService;

    @Autowired
    public StatisticController(StatisticAccessService accessService) {
        this.accessService = accessService;
    }

    @Operation(summary = "Получение всех данных о выполнении методов")
    @ApiResponse(responseCode = "200", description = "Список всех записей")
    @GetMapping(value = "/full")
    public List<DurationDataDto> full() {
        return accessService.getFullStatistic().stream()
                .map(this::asDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Получение данных для заданного названия метода")
    @ApiResponse(responseCode = "200", description = "Список записей для заданного метода")
    @GetMapping(value = "/method/{methodName}")
    public List<DurationDataDto> byMethodName(
            @Parameter(description = "Название метода")
            @PathVariable String methodName
    ) {
        return accessService.getByMethodName(methodName).stream()
                .map(this::asDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Получение данных для заданного типа выполнения метода")
    @ApiResponse(responseCode = "200", description = "Список записей для заданного типа выполнения")
    @GetMapping(value = "/type/{execType}")
    public List<DurationDataDto> byMethodType(
            @Parameter(description = "Тип выполнения метода")
            @PathVariable MethodExecType execType
    ) {
        return accessService.findByExecType(execType).stream()
                .map(this::asDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Получение результата выполнения математической функции над данными продолжительности выполнения методов")
    @ApiResponse(responseCode = "200", description = "Статистика продолжительности выполнения (мс) по методам")
    @GetMapping(value = "/{func}")
    public List<FuncDataDto> calcFuncInMillis(
            @Parameter(description = "Математическая функция, выполняемая над данными")
            @PathVariable StatisticFuncType func,

            @Parameter(description = "Название метода (опционально)")
            @RequestParam(required = false) String method
    ) {
        return accessService.calcTimeInMillis(func, method).stream()
                .map(this::asDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Получение доступа к функционалу на стадии тестирования")
    @ApiResponse(responseCode = "200", description = "Что-то интересное")
    @GetMapping(value = "/feature")
    public Map<String, Object> someFeature() {
        return accessService.someFeature();
    }

    private DurationDataDto asDto(DurationData data) {
        return new DurationDataDto(
                data.getId(),
                data.getMethodName(),
                data.getExecType(),
                data.getDurationInMillis(),
                data.getStartTime(),
                data.getFinishTime()
        );
    }

    private FuncDataDto asDto(FuncDataView view) {
        return new FuncDataDto(
                view.getMethodName(),
                view.getFuncResult()
        );
    }
}
