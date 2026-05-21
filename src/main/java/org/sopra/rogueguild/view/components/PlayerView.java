package org.sopra.rogueguild.view.components;

import java.io.PrintStream;
import java.util.List;

import static org.sopra.rogueguild.view.utils.Ansi.*;

import org.sopra.rogueguild.repository.model.Item;
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
}
