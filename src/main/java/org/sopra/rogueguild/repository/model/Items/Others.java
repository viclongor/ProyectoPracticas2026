package org.sopra.rogueguild.repository.model.Items;

public class Others extends Item{

    public Others(int originalId, String name, int price) {
        super(originalId, name, price, ItemCategory.OTHERS);
    }
}
