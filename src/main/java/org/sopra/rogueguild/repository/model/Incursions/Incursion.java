package org.sopra.rogueguild.repository.model.Incursions;

import org.sopra.rogueguild.repository.model.Items.Item;
import org.sopra.rogueguild.service.ItemGenerator;
import util.NumUtil;

public abstract class Incursion {
    String shortName;
    String description;
    int goldReward;
    Item itemReward;
    ItemGenerator itemGenerator;

    public Incursion(String shortName,String description, ItemGenerator itemGenerator) {
        this.shortName = shortName;
        this.description = description;
        int goldReward = 0;
        Item itemReward = null;
        this.itemGenerator = itemGenerator;
    }

    public Item generateBigItemReward(){
        Item  potentialItem;
        do{
            potentialItem = itemGenerator.generate(); 
        }while(potentialItem.getBasePrice()<50);
        return potentialItem;
    }
    public Item generateSmallItemReward(){
        Item  potentialItem;
        do{
            potentialItem = itemGenerator.generate();
        }while(potentialItem.getBasePrice()>50);
        return potentialItem;
    }
    public int generateBigGoldReward(){
        return NumUtil.roundTo5(NumUtil.generateInt(100,130));

    }
    public int generateSmallGoldReward(){
        return NumUtil.roundTo5(NumUtil.generateInt(10,30));

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
