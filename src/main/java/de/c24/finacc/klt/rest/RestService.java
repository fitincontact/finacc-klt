package de.c24.finacc.klt.rest;

import de.c24.finacc.klt.dto.ExchangeDTO;
import de.c24.finacc.klt.services.RateService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Rest Service
 */
@Tag(name = "RestService", description = "Currency data")
@RestController
@RequestMapping("/api")
public class RestService {
    private final RateService rateService;

    public RestService(RateService rateService) {
        this.rateService = rateService;
    }

    /**
     * Test rest endpoint
     *
     * @return Map of key/values
     */
    @GetMapping("/testit")
    public Map<String, String> test() {
        Map<String, String> response = new HashMap<>();
        response.put("key", "value");
        return response;
    }

    @Operation(
            summary = "Currency converter",
            description = "Return pair currency-amount"
    )
    @ApiOperation(value = "Convert pair of currency and amount to target currency")
    @GetMapping("latest/{baseAmount}/{baseCurrency}/{targetCurrency}")
    @ResponseStatus(HttpStatus.OK)
    public ExchangeDTO convert(
            @PathVariable @Parameter(description = "baseAmount") Double baseAmount,
            @PathVariable @Parameter(description = "baseCurrency") String baseCurrency,
            @PathVariable @Parameter(description = "targetCurrency") String targetCurrency
    ) {
        return rateService.getExchange(baseAmount, baseCurrency, targetCurrency);
    }
}