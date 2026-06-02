package org.sopra.rogueguild.respository.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.model.Items.Potion;
import org.sopra.rogueguild.repository.model.Player;

import static org.junit.jupiter.api.Assertions.*;

class PotionHealTest {

    private Player player;
    private Potion potion;

    @BeforeEach
    void setUp() {
        player = new Player("Test", 500);
        potion = new Potion(1, "Poción de fuego", 20, 10);
    }

    @Test
    void hitPointsInicialesAre20() {
        assertEquals(20, player.getHitPoints());
    }

    @Test
    void healIncrementaHitPoints() {
        player.takeDamage(10);
        player.heal(5);
        assertEquals(15, player.getHitPoints());
    }

    @Test
    void healNoSuperaMaximo() {
        player.heal(50);
        assertEquals(20, player.getHitPoints());
    }

    @Test
    void healConValorExactoAlMaximo() {
        player.takeDamage(5);
        player.heal(5);
        assertEquals(20, player.getHitPoints());
    }

    @Test
    void healConValorQueExcede() {
        player.takeDamage(5);
        player.heal(100);
        assertEquals(20, player.getHitPoints());
    }

    @Test
    void potionTieneHealPoint() {
        assertEquals(10, potion.getHealPoint());
    }

    @Test
    void potionExtiendeItem() {
        assertNotNull(potion.getName());
        assertNotNull(potion.getCategory());
    }

    @Test
    void comprarPotionCuraAlJugador() {
        player.takeDamage(10);
        int hpAntes = player.getHitPoints();
        player.heal(potion.getHealPoint());
        assertTrue(player.getHitPoints() > hpAntes);
    }

    @Test
    void comprarPotionNoLaGuardaEnInventario() {
        player.spendGold(potion.getPrice());
        player.heal(potion.getHealPoint());
        assertFalse(player.getInventory().contains(potion));
    }

    @Test
    void comprarPotionDescuentaOro() {
        int goldAntes = player.getGold();
        player.spendGold(potion.getPrice());
        assertEquals(goldAntes - potion.getPrice(), player.getGold());
    }
}