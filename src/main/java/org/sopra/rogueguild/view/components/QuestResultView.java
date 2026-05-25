package org.sopra.rogueguild.view.components;

import org.sopra.rogueguild.controller.dto.QuestResponse;
import org.sopra.rogueguild.repository.model.Items.ItemCategory;
import java.util.Map;

public class QuestResultView {
    private final MessageView messages;

    public QuestResultView(MessageView messages) {
        this.messages = messages;
    }

    public void show(QuestResponse r) {
        switch (r.getStatus()) {
            case SUCCESS ->
                    messages.showMessage("[+] ¡Misión completada! Has recibido " + r.getQuest().getGoldReward() + " oro.");
            case REQUIREMENTS_NOT_MET -> {
                StringBuilder sb = new StringBuilder("[!] No cumples los requisitos. Te faltan: ");
                for (Map.Entry<ItemCategory, Integer> entry : r.getMissing().entrySet()) {
                    sb.append(entry.getValue()).append(" ").append(entry.getKey().name()).append(" ");
                }
                messages.showMessage(sb.toString().trim());
            }
            case NOT_FOUND ->
                    messages.showMessage("[!] Esa misión no existe.");
            case NO_QUESTS ->
                    messages.showMessage("[!] No hay misiones disponibles.");
        }
    }
}