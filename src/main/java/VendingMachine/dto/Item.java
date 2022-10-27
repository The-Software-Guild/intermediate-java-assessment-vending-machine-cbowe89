package VendingMachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {
    private String itemName;
    private BigDecimal itemCost;
    private int itemQuantity;

    public Item(String itemName) {
        this.itemName = itemName;
    }

    public Item(String itemName, BigDecimal itemCost, int itemQuantity) {
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal cost) {
        this.itemCost = cost;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Item item = (Item) o;
        return itemQuantity == item.itemQuantity
                && Objects.equals(itemName, item.itemName)
                && Objects.equals(itemCost, item.itemCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, itemCost, itemQuantity);
    }

    @Override
    public String toString() {
        return "Item{" + "name = " + itemName + ", cost = " + itemCost
                + ", itemQuantity = " + itemQuantity + '}';
    }
}
