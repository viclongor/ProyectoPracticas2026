package org.sopra.rogueguild.repository.model.Incursions;

import org.sopra.rogueguild.repository.model.Items.RewardBag;
import org.sopra.rogueguild.service.ItemGenerator;

public class SmallIncursion extends Incursion{
    public SmallIncursion(String shortName, String description, ItemGenerator itemGenerator) {
        super(shortName, description, itemGenerator);
        goldReward = generateSmallGoldReward();
        itemReward = generateSmallItemReward();
    }

    public RewardBag completeQuest(){
        return new RewardBag(goldReward, itemReward);
    }

}
