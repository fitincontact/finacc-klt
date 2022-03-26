package de.c24.finacc.klt.services;

/**
 * Interface for methods that work with webAPI (Currency)
 */
public interface RateService {
    /**
     * Method returns target amount by base amount base currency and target currency
     *
     * @param baseAmount     base amount
     * @param baseCurrency   base currency
     * @param targetCurrency target currency
     * @return target amount
     */
    Double getExchange(Double baseAmount, String baseCurrency, String targetCurrency);
}