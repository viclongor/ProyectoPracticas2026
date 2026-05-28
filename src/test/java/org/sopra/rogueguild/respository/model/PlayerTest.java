package org.sopra.rogueguild.respository.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.controller.dto.BuyResponse;
import org.sopra.rogueguild.controller.MenuController;
import org.sopra.rogueguild.controller.dto.BuyResponse;
import org.sopra.rogueguild.controller.dto.SellResponse;
import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.Items.Armor;
import org.sopra.rogueguild.repository.model.Items.Item;
import org.sopra.rogueguild.repository.model.Items.ItemCategory;
import org.sopra.rogueguild.repository.model.Items.Weapon;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.view.ViewDisplay;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private Item weapon;
    private Item weapon2;
    private Item weapon3;
    private Item armor;
    private Item armor2;
    private Item boots;
    private Item boots2;
    private Item helmet;
    private Item helmet2;

    @BeforeEach
    void setUp() {
        player = new Player("Iñigo", 500);
        weapon = new Weapon(1, "Espada de fuego", 100, 10);
        weapon2 = new Weapon(2, "Espada de Hielo", 150, 20);
        weapon3 = new Weapon(1, "Espada de Rayo", 50, 5);
        armor = new Weapon(1, "armadura de Rayo", 50, 5);
        armor2 = new Weapon(1, "armadura de Dragon", 50, 5);
        boots = new Weapon(1, "Botas de Rayo", 50, 5);
        boots2 = new Weapon(1, "Botas de Dragon", 50, 5);
        helmet = new Weapon(1, "yelmo de Rayo", 50, 5);
        helmet2 = new Weapon(1, "yelmo de Dragon", 50, 5);
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

    @Test
    void playerAddItem() {
        player.addItem(weapon);
        assertEquals(1, player.getInventory().size());
    }

    @Test
    void playerBuyItem() {
        player.buy(weapon);
        assertEquals(400, player.getGold());
    }

    @Test
    void checkItemEquipedWEAPONLimit(){
        player.equipItem(weapon);
        player.equipItem(weapon2);
        player.equipItem(weapon3);
        assertEquals(2, player.getEquippedItems().get(ItemCategory.WEAPON).size());
    }
    @Test
    void checkItemEquipedARMORLimit(){
        player.equipItem(armor);
        player.equipItem(armor2);
        assertEquals(1, player.getEquippedItems().get(ItemCategory.ARMOR).size());
    }
    @Test
    void checkItemEquipedBOOTSLimit(){
        player.equipItem(boots);
        player.equipItem(boots2);
        assertEquals(1, player.getEquippedItems().get(ItemCategory.BOOTS).size());
    }
    @Test
    void checkItemEquipedHELMETLimit(){
        player.equipItem(helmet);
        player.equipItem(helmet2);
        assertEquals(1, player.getEquippedItems().get(ItemCategory.HELMET).size());
    }

}
