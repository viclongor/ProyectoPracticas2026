package org.sopra.rogueguild.respository.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.model.Items.Item;
import org.sopra.rogueguild.repository.model.Items.Weapon;
import org.sopra.rogueguild.repository.model.Player;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private Item weapon;

    @BeforeEach
    void setUp() {
        player = new Player("Iñigo", 500);
        weapon = new Weapon(1, "Espada de fuego", 100, 10);
    }

    @Test
    void inventarioVacioAlCrear() {
        assertTrue(player.getInventory().isEmpty());
    }

    @Test
    void addItemAnadeAlInventario() {
        player.addItem(weapon);
        assertEquals(1, player.getInventory().size());
        assertTrue(player.getInventory().contains(weapon));
    }

    @Test
    void addItemAnadeAlFinal() {
        Item weapon2 = new Weapon(2, "Daga de hielo", 50, 5);
        player.addItem(weapon);
        player.addItem(weapon2);
        assertEquals(weapon, player.getInventory().get(0));
        assertEquals(weapon2, player.getInventory().get(1));
    }

    @Test
    void removeItemEliminaDelInventario() {
        player.addItem(weapon);
        player.removeItem(weapon);
        assertTrue(player.getInventory().isEmpty());
    }

    @Test
    void removeItemNoLanzaErrorSiNoExiste() {
        assertDoesNotThrow(() -> player.removeItem(weapon));
    }

    @Test
    void removeItemEliminaPrimeraOcurrencia() {
        player.addItem(weapon);
        player.addItem(weapon);
        player.removeItem(weapon);
        assertEquals(1, player.getInventory().size());
    }

    @Test
    void getInventoryDevuelveListaCompleta() {
        Item weapon2 = new Weapon(2, "Daga de hielo", 50, 5);
        player.addItem(weapon);
        player.addItem(weapon2);
        assertEquals(2, player.getInventory().size());
    }
}
