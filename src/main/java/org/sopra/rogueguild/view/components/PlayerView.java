package org.sopra.rogueguild.view.components;

import java.io.PrintStream;
import java.util.List;

import static org.sopra.rogueguild.view.utils.Ansi.*;

import org.sopra.rogueguild.repository.model.Items.Item;
import org.sopra.rogueguild.repository.model.Player;

public class PlayerView {
    private final PrintStream out;

    public PlayerView(PrintStream out) { this.out = out; }

    public void playerStatus(Player player) {
        List<Item> inv = player.getInventory();
        String inventario = "";

        if (inv.isEmpty()) {
            inventario = "Vacío";
        } else {
            for (Item item : inv) {
                inventario += item.getName() + ", ";
            }
        }
        out.println();
        out.println("    +---------------------------------------------------+");
        out.println("    |                 " + c(GRAY, "ESTADO COMPRADOR") + "                  |");
        out.println("    +--+------------------------------------------------+");
        out.println("       | ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        out.println("       | ░    NOMBRE:        " + player.getName());
        out.println("       | ░    ORO:           " + player.getGold() + " monedas");
        out.println("       | ░    INVENTARIO:    "+inventario);
        out.println();
    }
    public void displayInventoryForSale(Player player) {
        List<Item> inv = player.getInventory();
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
        out.println("|| |            INVENTARIO DEL JUGADOR             | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |                                               | ||");
        for (int i = 0; i < inv.size(); i++) {
            Item item = inv.get(i);
            int sellPrice = calcSellPrice(item.getBasePrice());
            out.printf("|| |  [%d] %-28s %4d oro    | ||%n",
                    i + 1, item.getName(), sellPrice);
        }
        out.println("|| |                                               | ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
    }

    private int calcSellPrice(int basePrice) {
        int raw = (int) Math.round(basePrice * 0.8);
        return (int) (Math.round(raw / 5.0) * 5);
    }
}
