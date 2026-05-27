package org.sopra.rogueguild.view.components;

import org.sopra.rogueguild.repository.model.Items.Item;
import org.sopra.rogueguild.repository.model.Items.ItemCategory;
import org.sopra.rogueguild.repository.model.Player;
import java.io.PrintStream;
import java.util.List;

public class EquipView {
    private final PrintStream out;

    public EquipView(PrintStream out) { this.out = out; }

    public void showEquipMenu(Player player) {
        List<Item> inv = player.getInventory();
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
        out.println("|| |            EQUIPAR OBJETO                     | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |                                               | ||");
        for (int i = 0; i < inv.size(); i++) {
            Item item = inv.get(i);
            out.printf("|| |  [%d] %-38s    | ||%n", i + 1, item.getName());
        }
        out.println("|| |                                               | ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
    }

    public void showEquipped(Player player) {
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
        out.println("|| |            OBJETOS EQUIPADOS                  | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |                                               | ||");
        for (ItemCategory cat : player.getEquippedItems().keySet()) {
            List<Item> items = player.getEquippedItems().get(cat);
            if (!items.isEmpty()) {
                for (Item item : items) {
                    out.printf("|| |  [%s] %-38s    | ||%n", cat.name(), item.getName());
                }
            }
        }
        out.println("|| |                                               | ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
    }
}