package org.sopra.rogueguild.repository.model;

public abstract class Item {
    private String name;
    private int price;
    private ItemCategory category;
    private final int basePrice;
    private int originalId;


    public Item(String name, int price, ItemCategory category) {
        this.name = name;
        this.price = price;
        this.category = category;
        basePrice = price;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public int getBasePrice()           { return basePrice; }
    public int getOriginalId()        { return originalId; }
    public void setOriginalId(int id) { this.originalId = id; }
    public ItemCategory getCategory()   { return category; }

    public String toString() { return name + " (" + price + " oro)"; }
}
