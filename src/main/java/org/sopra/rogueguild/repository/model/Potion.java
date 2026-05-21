package org.sopra.rogueguild.repository.model;

public class Potion extends Item{

    String effectName;
    int effect;

    public Potion(String name, int price,String effectName ,int effect) {
        super(name, price, ItemCategory.POTION);
        this.effectName = effectName;
        this.effect = effect;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public String getEffectName() {
        return effectName;
    }

    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }

    @Override
    public String toString() {
        return getName() + " (" + getPrice() + " oro) Efecto: "+ getEffectName() + ": " + getEffect();
    }
}
