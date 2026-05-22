package org.sopra.rogueguild.repository.model.Items;

public class Armor extends Item {

  private int shield;

  public Armor(int idOriginal,String name, int price, int shield) {
    super(idOriginal,name, price, ItemCategory.ARMOR);
    this.shield = shield;
  }

  public int getShield() {
    return shield;
  }

  public void setShield(int shield) {
    this.shield = shield;
  }

  @Override
  public String toString() {
    return getName() + " (" + getPrice() + " oro) Armadura: "+ getShield();
  }

}
