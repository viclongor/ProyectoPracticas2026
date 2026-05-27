package org.sopra.rogueguild.controller.dto;

import org.sopra.rogueguild.repository.model.Items.ItemCategory;
import org.sopra.rogueguild.repository.model.Quest;
import java.util.Map;

public class QuestResponse {
    public enum Status { SUCCESS, REQUIREMENTS_NOT_MET,STATS_NOT_MET, NOT_FOUND, NO_QUESTS }

    private final Status status;
    private final Quest quest;
    private  Map<ItemCategory, Integer> missing;
    private  int missingAttack;
    private  int missingArmor;

    private QuestResponse(Status status, Quest quest, Map<ItemCategory, Integer> missing) {
        this.status = status;
        this.quest = quest;
        this.missing = missing;
    }
    private QuestResponse(Status status, Quest quest, int missingAttack, int missingArmor) {
        this.status = status;
        this.quest = quest;
        this.missingAttack = missingAttack;
        this.missingArmor = missingArmor;
    }

    public static QuestResponse success(Quest quest) {
        return new QuestResponse(Status.SUCCESS, quest, null);
    }
    public static QuestResponse requirementsNotMet(Quest quest, Map<ItemCategory, Integer> missing) {
        return new QuestResponse(Status.REQUIREMENTS_NOT_MET, quest, missing);
    }
    public static QuestResponse requirementsNotMet(Quest quest, int missingAttack, int missingArmor) {
        return new QuestResponse(Status.STATS_NOT_MET, quest, missingAttack, missingArmor);
    }
    public static QuestResponse notFound() {
        return new QuestResponse(Status.NOT_FOUND, null, null);
    }
    public static QuestResponse noQuests() {
        return new QuestResponse(Status.NO_QUESTS, null, null);
    }

    public Status getStatus()                      { return status; }
    public Quest getQuest()                        { return quest; }
    public Map<ItemCategory, Integer> getMissing() { return missing; }
    public int getMissingAttack() {return missingAttack;}
    public int getMissingArmor() {return missingArmor;}
}