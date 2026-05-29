package org.sopra.rogueguild.respository.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.model.Items.Armor;
import org.sopra.rogueguild.repository.model.Items.Helmet;
import org.sopra.rogueguild.repository.model.Items.Weapon;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.repository.model.Quest;

import static org.junit.jupiter.api.Assertions.*;

class QuestStatsTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Test", 500);
    }

    @Test
    void goldRewardEsMultiploDeCinco() {
        Quest quest = new Quest("Test", 103, 10, 10);
        assertEquals(0, quest.getGoldReward() % 5);
    }

    @Test
    void isCompletedFalseAlCrear() {
        Quest quest = new Quest("Test", 100, 10, 10);
        assertFalse(quest.isCompleted());
    }

    @Test
    void checkRequirementFalseConInventarioVacio() {
        Quest quest = new Quest("Test", 100, 50, 30);
        assertFalse(quest.checkRequirement(player));
    }

    @Test
    void checkRequirementTrueConAtaqueSuficiente() {
        Weapon weapon = new Weapon(1, "Espada", 100, 60);
        player.addItem(weapon);
        player.equipItem(weapon);

        Quest quest = new Quest("Test", 100, 50, 0);
        assertTrue(quest.checkRequirement(player));
    }

    @Test
    void checkRequirementFalseConAtaqueInsuficiente() {
        Weapon weapon = new Weapon(1, "Espada", 100, 20);
        player.addItem(weapon);
        player.equipItem(weapon);

        Quest quest = new Quest("Test", 100, 50, 0);
        assertFalse(quest.checkRequirement(player));
    }

    @Test
    void checkRequirementTrueConArmorSuficiente() {
        Armor armor = new Armor(1, "Armadura", 100, 40);
        player.addItem(armor);
        player.equipItem(armor);

        Quest quest = new Quest("Test", 100, 0, 30);
        assertTrue(quest.checkRequirement(player));
    }

    @Test
    void checkRequirementFalseConArmorInsuficiente() {
        Armor armor = new Armor(1, "Armadura", 100, 10);
        player.addItem(armor);
        player.equipItem(armor);

        Quest quest = new Quest("Test", 100, 0, 30);
        assertFalse(quest.checkRequirement(player));
    }

    @Test
    void checkRequirementTrueConAmbasEstadisticas() {
        Weapon weapon = new Weapon(1, "Espada", 100, 60);
        Armor armor = new Armor(2, "Armadura", 100, 40);
        player.addItem(weapon);
        player.addItem(armor);
        player.equipItem(weapon);
        player.equipItem(armor);

        Quest quest = new Quest("Test", 100, 50, 30);
        assertTrue(quest.checkRequirement(player));
    }

    @Test
    void armorSumaHelmetYArmor() {
        Armor armor = new Armor(1, "Armadura", 100, 20);
        Helmet helmet = new Helmet(2, "Yelmo", 100, 15);
        player.addItem(armor);
        player.addItem(helmet);
        player.equipItem(armor);
        player.equipItem(helmet);

        Quest quest = new Quest("Test", 100, 0, 35);
        assertTrue(quest.checkRequirement(player));
    }

    @Test
    void misionCompletadaNoSePuedeRepetir() {
        Weapon weapon = new Weapon(1, "Espada", 100, 60);
        player.addItem(weapon);
        player.equipItem(weapon);

        Quest quest = new Quest("Test", 100, 50, 0);
        quest.complete(player);
        assertFalse(quest.checkRequirement(player));
    }

    @Test
    void completarMisionDaOroAlJugador() {
        Weapon weapon = new Weapon(1, "Espada", 100, 60);
        player.addItem(weapon);
        player.equipItem(weapon);
        player.setGold(0);

        Quest quest = new Quest("Test", 100, 50, 0);
        quest.complete(player);
        assertEquals(100, player.getGold());
    }
}
