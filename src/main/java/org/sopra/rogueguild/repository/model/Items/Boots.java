package org.sopra.rogueguild.repository.model.Items;

public class Boots extends Item {
    private int speed;

    public Boots(int idOriginal,String name, int price, int speed) {
        super(idOriginal,name, price, ItemCategory.BOOTS);
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return getName() + " (" + getPrice() + " oro)";
    }
}
