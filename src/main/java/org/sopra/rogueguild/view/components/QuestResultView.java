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
            case STATS_NOT_MET -> {
                if(r.getMissingAttack()<=0){
                    messages.showMessage("[!] No cumples los requisitos. Te faltan: "+r.getMissingArmor()+" puntos de armadura");
                } else  if(r.getMissingArmor()<=0){
                    messages.showMessage("[!] No cumples los requisitos. Te faltan: "+r.getMissingAttack()+" puntos de ataque");
                }else{
                    messages.showMessage("[!] No cumples los requisitos. Te faltan: "+r.getMissingAttack()+" puntos de ataque y "+r.getMissingArmor()+" puntos de armadura");
                }
            }
            case NOT_FOUND ->
                    messages.showMessage("[!] Esa misión no existe.");
            case NO_QUESTS ->
                    messages.showMessage("[!] Has completado todas las misiones disponibles.");
        }
    }
}