package org.sopra.rogueguild.repository.model.Items;

public enum ItemCategory {

    WEAPON(100, 300),
    ARMOR(50, 200),
    POTION(10, 40),
    HELMET(20, 150),
    BOOTS(20, 100),
    OTHERS(250, 300);


    final int minPrice;
    final int maxPrice;

    ItemCategory(int minPrice, int maxPrice){
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public int getMinPrice() {
        return minPrice;
    }
    public int getMaxPrice(){
        return maxPrice;
    }
}
