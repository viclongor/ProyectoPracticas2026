package org.sopra.rogueguild.repository.model.Items;

public class Armor extends Item {

  private int shield;

  public Armor(int idOriginal,String name, int price, int shield) {
    super(idOriginal,name, price, ItemCategory.ARMOR);
    this.shield = shield;
  }
}
