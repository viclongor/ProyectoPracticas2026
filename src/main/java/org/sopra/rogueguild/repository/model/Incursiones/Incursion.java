package org.sopra.rogueguild.repository.model.Incursiones;

import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.service.ItemGenerator;
import util.NumUtil;

public abstract class Incursion {
    String shortName;
    String description;
    int goldReward;
    Item itemReward;

    public Incursion(String shortName,String description) {
        this.shortName = shortName;
        this.description = description;
        int goldReward = 0;
        Item itemReward = null;
    }

    public Item generateBigItemReward(){
        Item  potentialItem;
        ItemGenerator itemGenerator = new ItemGenerator();
        do{
            potentialItem = itemGenerator.generate();
        }while(itemReward.getBasePrice()<50);
        return potentialItem;
    }
    public Item generateSmallItemReward(){
        Item  potentialItem;
        ItemGenerator itemGenerator = new ItemGenerator();
        do{
            potentialItem = itemGenerator.generate();
        }while(itemReward.getBasePrice()>50);
        return potentialItem;
    }
    public int generateBigGoldReward(){
        return NumUtil.generateInt(100,130);

    }
    public int generateSmallGoldReward(){
        return NumUtil.generateInt(10,30);

    }

    public int getGoldReward() {
        return goldReward;
    }

    public Item getItemReward() {
        return itemReward;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setGoldReward(int goldReward) {
        this.goldReward = goldReward;
    }

    public void setItemReward(Item itemReward) {
        this.itemReward = itemReward;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
