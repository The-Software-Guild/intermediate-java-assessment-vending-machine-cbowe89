package VendingMachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum Coins {
    QUARTER(new BigDecimal("0.25")),
    DIME(new BigDecimal("0.10")),
    NICKEL(new BigDecimal("0.05")),
    PENNY(new BigDecimal("0.01"));

    private final BigDecimal value;

    Coins(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value.setScale(2, RoundingMode.DOWN);
    }
}
