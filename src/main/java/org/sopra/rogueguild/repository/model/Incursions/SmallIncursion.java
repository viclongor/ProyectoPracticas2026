package org.sopra.rogueguild.repository.model.Incursions;

import org.sopra.rogueguild.repository.model.Items.RewardBag;

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
