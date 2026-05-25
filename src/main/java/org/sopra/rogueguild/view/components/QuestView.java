package org.sopra.rogueguild.view.components;

import java.io.PrintStream;

public class QuestView {
    private final PrintStream out;
    public QuestView(PrintStream out) {this.out = out;}

    public void displayAvailableQuests(){
        out.println("""
                      ___________________________________________________
                    /  _______________________________________________  \\\\
                   || /                                               \\\\ ||
                   || |             MISIONES DISPONIBLES               | ||
                   || | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  | ||
                   || |                                                | ||
                   || |                                                | ||
                   || |      [1] Danza de muerte                       | ||
                   || |      [2] Caballero del Fénix                   | ||
                   || |                                                | ||
                   || |                                                | ||
                   || \\\\_______________________________________________/ 
                   \\\\___________________________________________________/
                """);
    }
}
