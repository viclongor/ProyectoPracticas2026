package org.sopra.rogueguild.respository.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.controller.dto.BuyResponse;
import org.sopra.rogueguild.controller.MenuController;
import org.sopra.rogueguild.controller.dto.BuyResponse;
import org.sopra.rogueguild.controller.dto.SellResponse;
import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.Items.*;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.view.ViewDisplay;

import static org.junit.jupiter.api.Assertions.*;
import static org.sopra.rogueguild.repository.model.Items.ItemCategory.*;

class PlayerTest {

    private Player player;
    private Weapon weapon;
    private Weapon weapon2;
    private Weapon weapon3;
    private Armor armor;
    private Armor armor2;
    private Boots boots;
    private Boots boots2;
    private Helmet helmet;
    private Helmet helmet2;

    @BeforeEach
    void setUp() {
        player = new Player("Iñigo", 500);
        weapon = new Weapon(1, "Espada de fuego", 100, 10);
        weapon2 = new Weapon(2, "Espada de Hielo", 150, 20);
        weapon3 = new Weapon(1, "Espada de Rayo", 50, 50);
        armor = new Armor(1, "armadura de Rayo", 50, 5);
        armor2 = new Armor(1, "armadura de Dragon", 50, 5);
        boots = new Boots(1, "Botas de Rayo", 50, 5);
        boots2 = new Boots(1, "Botas de Dragon", 50, 5);
        helmet = new Helmet(1, "yelmo de Rayo", 50, 5);
        helmet2 = new Helmet(1, "yelmo de Dragon", 50, 5);
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
    void equipItemRemovesItFromInventory(){

        player.addItem(weapon);
        player.equipItem(weapon);
        assertEquals(0, player.getInventory().size());

    }
    @Test
    void equipWeaponPutsItInWeaponSlot(){
        player.addItem(weapon);
        player.equipItem(weapon);
        assertEquals(1, player.getEquippedItems().get(WEAPON).size());
        assertEquals(0, player.getEquippedItems().get(ARMOR).size());
        assertEquals(0, player.getEquippedItems().get(HELMET).size());
        assertEquals(0, player.getEquippedItems().get(BOOTS).size());
    }
    @Test
    void equipArmorPutsItInArmorSlot(){
        player.addItem(armor);
        player.equipItem(armor);
        assertEquals(0, player.getEquippedItems().get(WEAPON).size());
        assertEquals(1, player.getEquippedItems().get(ARMOR).size());
        assertEquals(0, player.getEquippedItems().get(HELMET).size());
        assertEquals(0, player.getEquippedItems().get(BOOTS).size());
    }
    @Test
    void equipHelmetPutsItInHelmetSlot(){
        player.addItem(helmet);
        player.equipItem(helmet);
        assertEquals(0, player.getEquippedItems().get(WEAPON).size());
        assertEquals(0, player.getEquippedItems().get(ARMOR).size());
        assertEquals(1, player.getEquippedItems().get(HELMET).size());
        assertEquals(0, player.getEquippedItems().get(BOOTS).size());
    }
    @Test
    void equipBootsPutsItInBootsSlot(){
        player.addItem(boots);
        player.equipItem(boots);
        assertEquals(0, player.getEquippedItems().get(WEAPON).size());
        assertEquals(0, player.getEquippedItems().get(ARMOR).size());
        assertEquals(0, player.getEquippedItems().get(HELMET).size());
        assertEquals(1, player.getEquippedItems().get(BOOTS).size());
    }
    @Test
    void checkItemEquipedWEAPONLimit(){
        player.addItem(weapon);
        player.addItem(weapon2);
        player.addItem(weapon3);
        player.equipItem(weapon);
        player.equipItem(weapon2);
        player.equipItem(weapon3);
        assertEquals(2, player.getEquippedItems().get(WEAPON).size());
        assertEquals(weapon, player.getInventory().get(0));
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
    @Test
    void equipingWeaponRemovesLowestAttack(){
        player.addItem(weapon);
        player.equipItem(weapon);

        player.addItem(weapon2);
        player.equipItem(weapon2);

        player.addItem(weapon3);

        boolean isEquiped = player.equipItem(weapon3);

        assertTrue(isEquiped);

        assertTrue(player.isEquipped(weapon3));

        assertTrue(player.getInventory().contains(weapon));

        assertTrue(player.isEquipped(weapon2));

        assertFalse(player.isEquipped(weapon));
    }
}