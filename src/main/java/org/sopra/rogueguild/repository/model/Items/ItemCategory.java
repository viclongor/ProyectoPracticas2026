package org.sopra.rogueguild.repository.model.Items;

public enum ItemCategory {

    WEAPON(50 ,200),
    ARMOR(20,100),
    POTION(20, 150),
    HELMET(100, 300),
    BOOTS(10, 40),
    OTHERS(Integer.MIN_VALUE, Integer.MAX_VALUE);


    int minPrice;
    int maxPrice;

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
