package org.sopra.rogueguild.repository.model;

public class Boots extends Item {
    private int speed;

    public Boots(String name, int price, int speed) {
        super(name, price, ItemCategory.BOOTS);
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
        return getName() + " (" + getPrice() + " oro) Velocidad: "+ getSpeed();
    }
}
