package org.sopra.rogueguild.repository.model;

import org.sopra.rogueguild.repository.model.Items.ItemCategory;
import util.NumUtil;
import java.util.Map;

public class Quest {

    private final String description;
    private final int goldReward;
    private final Map<ItemCategory, Integer> requiredItems;
    private boolean isCompleted;
    private int requiredAttack;
    private int requiredArmor;
    public Quest(String description, int goldReward, Map<ItemCategory, Integer> requiredItems) {
        this.description = description;
        this.goldReward = NumUtil.roundTo5(goldReward);
        this.requiredItems = requiredItems;
        this.isCompleted = false;
    }
    public Quest(String description, int goldReward, int requiredAttack, int requiredArmor) {
        this.description = description;
        this.goldReward = NumUtil.roundTo5(goldReward);
        this.requiredItems = null;
        this.isCompleted = false;
        this.requiredAttack = requiredAttack;
        this.requiredArmor =  requiredArmor;
    }
    public boolean requiresStats(){
        return requiredItems == null;
    }
    public boolean checkRequirement(Player player) {
        if (isCompleted) return false;
        boolean completesRequirements;
        if(requiresStats()){
            completesRequirements = checkStats(player);
        } else {
            completesRequirements = checkItems(player);
        }
        return completesRequirements;
    }
    private boolean checkStats(Player player){
        if(player.getAttack() >= requiredAttack && player.getArmor() >= requiredArmor){
            return true;
        } else {
            return false;
        }

        /*boolean gotRequiredStats = false;
        if(player.getArmor() >= requiredArmor){
            gotRequiredStats = true;
        }*/

    }
    private boolean checkItems(Player player){
        for (Map.Entry<ItemCategory, Integer> requirement : requiredItems.entrySet()) {
            ItemCategory category = requirement.getKey();
            int needed = requirement.getValue();
            long count = player.getInventory().stream()
                    .filter(item -> item.getCategory() == category)
                    .count();
            if (count < needed) return false;
        }
        return true;
    }
    public boolean complete(Player player) {
        if (!checkRequirement(player)) return false;
        isCompleted = true;
        player.receiveGold(goldReward);
        return true;
    }

    public String getDescription()                       { return description; }
    public int getGoldReward()                           { return goldReward; }
    public Map<ItemCategory, Integer> getRequiredItems() { return requiredItems; }
    public boolean isCompleted()                         { return isCompleted; }
    public int getRequiredAttack() { return requiredAttack;}
    public int getRequiredArmor() {return requiredArmor;}
}