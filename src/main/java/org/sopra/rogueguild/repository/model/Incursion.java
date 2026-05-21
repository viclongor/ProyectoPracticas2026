package org.sopra.rogueguild.repository.model;

import util.NumMalipulator;

public class Incursion {
    String shortName;
    String description;
    int goldReward;
    Item itemReward;


    public Incursion(String shortName,String description,  int goldReward, Item itemReward) {
        this.goldReward = smallGoldReward(goldReward);
        this.itemReward = itemReward;
        this.shortName = shortName;
        this.description = description;
    }
    public Incursion(String shortName,String description,  int goldReward) {
        this.goldReward = bigGoldReward(goldReward);
        this.shortName = shortName;
        this.description = description;
    }
    public Incursion(String shortName,String description,  Item itemReward) {
        this.itemReward = itemReward;
        this.shortName = shortName;
        this.description = description;
    }


    public int bigGoldReward(int goldReward){
        goldReward = NumMalipulator.roundTo5(goldReward);
        if(goldReward >300){
            goldReward = 300;
        } else if (goldReward <150) {
            goldReward = 150;
        }
        return goldReward;
    }
    public int smallGoldReward(int goldReward){
        goldReward = NumMalipulator.roundTo5(goldReward);
        if(goldReward >150){
            goldReward = 150;
        } else if (goldReward <50) {
            goldReward = 50;
        }
        return goldReward;
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
