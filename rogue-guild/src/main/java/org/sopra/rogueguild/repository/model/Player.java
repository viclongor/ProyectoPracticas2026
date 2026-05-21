package org.sopra.rogueguild.repository.model;
import java.util.ArrayList;
import java.util.List;
public class Player {
    private String name;
    private int gold;
    private List<Item> inventory = new ArrayList<>();
    public Player(String name, int gold) {
        this.name = name;
        this.gold = gold;
    }
    public String getName() {
        return name;
    }
    public int getGold() {
        return gold;
    }
    public void buy(Item item) {
        this.gold -= item.getPrice();
        this.inventory.add(item);
    }
    public boolean addItem(Item item){
        return inventory.add(item);
    }
    public boolean removeItem(Item item){
        return inventory.remove(item);
    }
}
