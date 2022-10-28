package VendingMachine.dto;

import VendingMachine.service.InsufficientFundsException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import static VendingMachine.dto.Coins.QUARTER;
import static VendingMachine.dto.Coins.DIME;
import static VendingMachine.dto.Coins.NICKEL;
import static VendingMachine.dto.Coins.PENNY;

public class Change {
    private static HashMap<Coins, Integer> coinChangeMap = new HashMap<>();

    public Change(HashMap<Coins, Integer> change) {
        coinChangeMap = change;
    }

    public static HashMap<Coins,Integer> getChange(BigDecimal funds) throws
            InsufficientFundsException {
        if (funds.compareTo(new BigDecimal("0.00")) < 1)
            throw new InsufficientFundsException(
                    "Unable to make change. Balance is less than $0.01.");

        int quarters = funds.divide(QUARTER.getValue(), RoundingMode.DOWN).intValue();
        coinChangeMap.put(QUARTER, quarters);
        funds = funds.subtract(QUARTER.getValue().multiply(BigDecimal.valueOf(quarters)));

        int dimes = funds.divide(DIME.getValue(), RoundingMode.DOWN).intValue();
        coinChangeMap.put(DIME, dimes);
        funds = funds.subtract(DIME.getValue().multiply(BigDecimal.valueOf(dimes)));

        int nickels = funds.divide(NICKEL.getValue(), RoundingMode.DOWN).intValue();
        coinChangeMap.put(NICKEL, nickels);
        funds = funds.subtract(NICKEL.getValue().multiply(BigDecimal.valueOf(nickels)));

        int pennies = funds.divide(PENNY.getValue(), RoundingMode.DOWN).intValue();
        coinChangeMap.put(PENNY, pennies);
        funds = funds.subtract(PENNY.getValue().multiply(BigDecimal.valueOf(pennies)));

        return coinChangeMap;
    }
}
