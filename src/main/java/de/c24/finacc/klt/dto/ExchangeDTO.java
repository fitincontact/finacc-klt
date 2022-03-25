package de.c24.finacc.klt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Schema(description = "Output pair of currency and amount")
@Getter
@Setter
public class ExchangeDTO implements Serializable {
    private static final long serialVersionUID = 435623653426L;

    @Schema(description = "currency", example = "USD")
    String currency;
    @Schema(description = "amount", example = "2")
    Double amount;

    public ExchangeDTO(Double baseAmount) {
        this.amount = baseAmount;
    }

    public void setAmount(Double amount) {
        this.amount = this.amount * amount;
    }
}