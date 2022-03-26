package de.c24.finacc.klt.rest;

import de.c24.finacc.klt.services.RateService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @Operation(
            summary = "Currency converter",
            description = "Return pair currency-amount"
    )
    @ApiOperation(value = "Convert base amount base currency and target currency to target amount")
    @GetMapping("latest/{baseAmount}/{baseCurrency}/{targetCurrency}")
    @ResponseStatus(HttpStatus.OK)
    public Double convert(
            @PathVariable() @Parameter(description = "baseAmount") Double baseAmount,
            @PathVariable() @Parameter(description = "baseCurrency") String baseCurrency,
            @PathVariable() @Parameter(description = "targetCurrency") String targetCurrency
    ) {
        return rateService.getExchange(baseAmount, baseCurrency, targetCurrency);
    }
}