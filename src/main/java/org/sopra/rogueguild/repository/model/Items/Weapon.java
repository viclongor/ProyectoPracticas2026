package org.sopra.rogueguild.repository.model.Items;

public class Weapon extends Item {

    private int damage;

    public Weapon(int idOriginal,String name, int price, int damage) {
        super(idOriginal,name, price, ItemCategory.WEAPON);
        this.damage = damage;
    }
}
