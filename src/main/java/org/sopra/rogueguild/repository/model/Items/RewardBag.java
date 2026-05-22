package org.sopra.rogueguild.repository.model.Items;

public class RewardBag {
    int gold;
    Item item;

    public RewardBag(int gold, Item item) {
        this.gold = gold;
        this.item = item;
    }

    public RewardBag(int gold) {
        this.gold = gold;
    }

    public RewardBag(Item item) {
        this.item = item;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
