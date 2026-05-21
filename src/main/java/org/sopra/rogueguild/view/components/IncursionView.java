package org.sopra.rogueguild.view.components;

import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.RewardBag;

import java.io.PrintStream;

public class IncursionView {
    private final PrintStream out;
    public IncursionView(PrintStream out) {this.out = out;}

    public void displayAvailableIncursions(){
        out.println("""
                      ___________________________________________________
                    /  _______________________________________________  \\\\
                   || /                                               \\\\ ||
                   || |            INCURSIONES DISPONIBLES             | ||
                   || | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  | ||
                   || |                                                | ||
                   || |                                                | ||
                   || |      [1] Incursion de conquista                | ||
                   || |      [2] Incursion de saqueo                   | ||
                   || |      [3] Incursion menor                       | ||
                   || |                                                | ||
                   || |                                                | ||
                   || \\\\_______________________________________________/ 
                   \\\\___________________________________________________/
                """);
    }
    public void displayIncursionResults(RewardBag rewardBag){
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
        out.println("|| |          RECOMPENSAS DE LA INCURSION          | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |                                               | ||");
        out.println("|| |    Has encontrado: "+rewardBag.getGold()+" Oro y                                           | ||");
        out.println("|| |         el objeto: "+rewardBag.getItem()+"                                           | ||");
        out.println("|| |                                               | ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
    }

}
