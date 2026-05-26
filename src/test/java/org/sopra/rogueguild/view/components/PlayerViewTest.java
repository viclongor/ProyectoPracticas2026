package org.sopra.rogueguild.view.components;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.model.Items.Armor;
import org.sopra.rogueguild.repository.model.Items.Weapon;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.view.ViewDisplay;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PlayerViewTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp(){
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void muestraInventarioExactamente() {
        Player player = new Player("jugador", 500);

        player.addItem(new Weapon(1,"Espada del Alba",80,30));
        player.addItem(new Armor(2,"Yelmo de la oscuridad",100,40));

        ViewDisplay view = new ViewDisplay();
        view.displayInventoryForSale(player);

        String actual = outputStreamCaptor.toString();

        String esperado =
                "  ___________________________________________________" +System.lineSeparator()+
                " /  _______________________________________________  \\"+System.lineSeparator()+
                "|| /                                               \\ ||" +System.lineSeparator()+
                "|| |            INVENTARIO DEL JUGADOR             | ||"+System.lineSeparator() +
                "|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||"+System.lineSeparator() +
                "|| |                                               | ||"+System.lineSeparator() +
                "|| |  [1] Espada del Alba                65 oro    | ||"+System.lineSeparator() +
                "|| |  [2] Yelmo de la oscuridad          80 oro    | ||"+System.lineSeparator() +
                "|| |                                               | ||"+System.lineSeparator() +
                "|| \\_______________________________________________/ ||"+System.lineSeparator() +
                " \\___________________________________________________/"+System.lineSeparator();

        assertEquals(esperado, actual);
    }
}