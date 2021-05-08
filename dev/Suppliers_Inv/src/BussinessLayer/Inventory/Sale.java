package BussinessLayer.Inventory;

import java.time.LocalDateTime;

public class Sale {
    private LocalDateTime saleDate;
    private Double itemCost;
    private Double salePrice;
    private int itemId;

    public Sale(int itemId, double itemCost, double salePrice) {
        this.itemId = itemId;
        this.itemCost = itemCost;
        this.salePrice = salePrice;
        this.saleDate = LocalDateTime.now();
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public Double getItemCost() {
        return itemCost;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public int getItemId() {
        return itemId;
    }

    public String toString() {
        String str = "Item ID:\t" + itemId + "\n" +
                "Sale date:\t" + saleDate + "\n" +
                "Item cost:\t" + itemCost + "\n" +
                "Sale Price:\t" + salePrice;
        return str;
    }

}
