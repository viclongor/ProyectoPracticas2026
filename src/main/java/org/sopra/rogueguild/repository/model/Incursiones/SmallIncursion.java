package org.sopra.rogueguild.repository.model.Incursiones;

import org.sopra.rogueguild.repository.model.RewardBag;

public class SmallIncursion extends Incursion{
    public SmallIncursion(String shortName, String description) {
        super(shortName, description);
        goldReward = generateSmallGoldReward();
        itemReward = generateSmallItemReward();
    }

    public RewardBag completeQuest(){
        return new RewardBag(goldReward, itemReward);
    }

}
