package VendingMachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import static VendingMachine.dto.Coins.QUARTER;
import static VendingMachine.dto.Coins.DIME;
import static VendingMachine.dto.Coins.NICKEL;
import static VendingMachine.dto.Coins.PENNY;

public class Change {

    private HashMap<Coins, Integer> coinChangeMap = new HashMap<>();

    public Change(HashMap<Coins, Integer> change) {
        this.coinChangeMap = change;
    }

    public HashMap<Coins,Integer> getChange(BigDecimal funds) {
        int quarters = funds.divide(QUARTER.getValue(), RoundingMode.DOWN).intValue();
        coinChangeMap.put(QUARTER, quarters);
        int dimes = funds.divide(DIME.getValue(), RoundingMode.DOWN).intValue();
        coinChangeMap.put(DIME, dimes);
        int nickels = funds.divide(NICKEL.getValue(), RoundingMode.DOWN).intValue();
        coinChangeMap.put(NICKEL, nickels);
        int pennies = funds.divide(PENNY.getValue(), RoundingMode.DOWN).intValue();
        coinChangeMap.put(PENNY, pennies);

        return coinChangeMap;
    }
}
