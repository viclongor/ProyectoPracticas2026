package org.sopra.rogueguild.repository.model.Incursions;

import org.sopra.rogueguild.repository.model.Items.RewardBag;
import org.sopra.rogueguild.service.ItemGenerator;
import util.NumUtil;

public class ConquestIncursion extends Incursion{

    public ConquestIncursion(String shortName, String description, ItemGenerator itemGenerator) {
        super(shortName, description, itemGenerator);
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
