package org.sopra.rogueguild.repository.model.Incursiones;

import org.sopra.rogueguild.repository.model.RewardBag;
import util.NumUtil;

public class ConquestIncursion extends Incursion{

    public ConquestIncursion(String shortName, String description) {
        super(shortName, description);
        itemReward = generateBigItemReward();
        if(hasGold()){
            goldReward = generateSmallGoldReward();
        }
    }

    public RewardBag completeQuest(){
        return new RewardBag(goldReward, itemReward);
    }

    public boolean hasGold(){
        int chance = NumUtil.generateInt(1,4);
        if(chance==1){
            return true;
        } else{
            return false;
        }
    }
}
