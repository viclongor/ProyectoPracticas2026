package org.sopra.rogueguild.repository.model.Incursions;

import org.sopra.rogueguild.repository.model.RewardBag;
import util.NumUtil;

public class PillageIncursion extends Incursion{
    public PillageIncursion(String shortName, String description) {
        super(shortName, description);
        goldReward = generateBigGoldReward();
        if(hasItem()){
            itemReward = generateSmallItemReward();
        }
    }

    public RewardBag completeQuest(){
        return new RewardBag(goldReward, itemReward);
    }

    public boolean hasItem(){
        int chance = NumUtil.generateInt(1,4);
        if(chance==1){
            return true;
        } else{
            return false;
        }
    }
}
