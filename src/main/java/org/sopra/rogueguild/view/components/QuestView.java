package org.sopra.rogueguild.view.components;

import org.sopra.rogueguild.repository.model.Quest;
import java.io.PrintStream;
import java.util.List;

public class QuestView {
    private final PrintStream out;

    public QuestView(PrintStream out) {
        this.out = out;
    }

    public void showQuests(List<Quest> quests) {
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
        out.println("|| |           MISIONES DISPONIBLES                | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |                                               | ||");
        for (int i = 0; i < quests.size(); i++) {
            Quest q = quests.get(i);
            out.printf("|| |  [%d] %-28s %4d oro    | ||%n",
                    i + 1, q.getDescription(), q.getGoldReward());
        }
        out.println("|| |                                               | ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
    }
}