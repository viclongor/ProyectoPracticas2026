package org.sopra.rogueguild.repository.model.Items;

public class Potion extends Item {

    int healPoint;

    public Potion(int idOriginal,String name, int price,int healPoint) {
        super(idOriginal,name, price, ItemCategory.POTION);
        this.healPoint = healPoint;
    }

    public void setHealPoint(int healPoint) {
        this.healPoint = healPoint;
    }

    public int getHealPoint() {
        return healPoint;
    }

    @Override
    public String toString() {
        return getName() + " (" + getPrice() + " oro) Restaura: "+ getHealPoint() + " Puntos de vida";
    }
}
