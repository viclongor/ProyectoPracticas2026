package org.sopra.rogueguild.repository.model.Items;

public class Armor extends Item {

  private int armor;

  public Armor(int idOriginal,String name, int price, int armor) {
    super(idOriginal,name, price, ItemCategory.ARMOR);
    this.armor = armor;
  }

  public int getArmor() {
    return armor;
  }

  public void setArmor(int armor) {
    this.armor = armor;
  }

  @Override
  public String toString() {
    return getName() + " (" + getPrice() + " oro) Armadura: "+ getArmor();
  }

}
