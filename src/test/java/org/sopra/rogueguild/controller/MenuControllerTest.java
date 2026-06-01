package org.sopra.rogueguild.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.controller.dto.BuyResponse;
import org.sopra.rogueguild.controller.dto.SellResponse;
import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.City;
import org.sopra.rogueguild.repository.model.Items.Armor;
import org.sopra.rogueguild.repository.model.Items.Item;
import org.sopra.rogueguild.repository.model.Items.Weapon;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.view.ViewDisplay;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuControllerTest {
    ShopRepository shop;
    Player player;
    Item item1;
    Item item2;
    ViewDisplay view;
    MenuController controller;
    List<City> cities;

    @BeforeEach
    void setUp() {
        shop = new ShopRepository();
        player = new Player("Player", 500);
        item1 = new Weapon(2, "Espada legendaria", 100, 20);
        item2 = new Armor(2, "Armadura legendaria", 120, 20);
        view = new ViewDisplay();
        cities = new ArrayList<>();
        controller = new MenuController(player, view, shop, cities);
    }

    // ══════════════════════════════════════════
    // BUY
    // ══════════════════════════════════════════

    @Test
    void buyProcessItemAppearsInInventory() {
        Item buyedItem = shop.getItem(1);
        BuyResponse response = controller.buyProcess(1);

        assertTrue(player.getInventory().contains(buyedItem));
        assertEquals(350, player.getGold());
        assertFalse(shop.getAllStock().containsValue(buyedItem));
    }

    @Test
    void buyProccessItemDissapersFromShop() {
        Item buyedItem = shop.getItem(1);
        controller.buyProcess(1);

        assertEquals(2, shop.getAllStock().size());
    }

    @Test
    void buyResponseNotEnoughGold() {
        Item expensiveItem = new Weapon(2, "Espada legendaria", 600, 20);
        BuyResponse response = BuyResponse.notEnoughGold(expensiveItem, 500);

        assertEquals(BuyResponse.Status.NOT_ENOUGH_GOLD, response.getStatus());
        assertEquals(100, response.getMissingGold());
    }

    // ══════════════════════════════════════════
    // SELL — lógica directa (sin Scanner)
    // ══════════════════════════════════════════

    @Test
    void itemRemovedFromPlayerInventory() {
        player.setGold(0);
        player.addItem(item1);
        player.addItem(item2);

        Item item = player.getInventory().get(1);
        int raw = (int) Math.round(item.getBasePrice() * 0.8);
        int goldReceived = (int) (Math.round(raw / 5.0) * 5);

        player.removeItem(item);
        player.receiveGold(goldReceived);
        shop.addItem(item);

        assertEquals(1, player.getInventory().size());
    }

    @Test
    void sellGoldAmount() {
        player.setGold(0);
        player.addItem(item1);
        player.addItem(item2);

        Item item = player.getInventory().get(1);
        int raw = (int) Math.round(item.getBasePrice() * 0.8);
        int goldReceived = (int) (Math.round(raw / 5.0) * 5);

        player.removeItem(item);
        player.receiveGold(goldReceived);
        shop.addItem(item);

        assertEquals(95, player.getGold());
    }

    @Test
    void itemSoldItemAddedToShop() {
        player.addItem(item1);
        player.addItem(item2);

        Item item = player.getInventory().get(1);
        int raw = (int) Math.round(item.getBasePrice() * 0.8);
        int goldReceived = (int) (Math.round(raw / 5.0) * 5);

        player.removeItem(item);
        player.receiveGold(goldReceived);
        shop.addItem(item);

        assertEquals(4, shop.getAllStock().size());
    }

    @Test
    void sellProcessShowsConfirmationWithNameAndGold() {
        player = new Player("Player", 400);
        player.addItem(item1);

        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        controller = new MenuController(player, view, shop, cities);

        SellResponse response = controller.sellProcess();

        assertEquals(SellResponse.Status.SUCCESS, response.getStatus());
        assertEquals(item1, response.getItem());
        assertEquals(80, response.getGoldReceived());
        assertEquals(480, player.getGold());
    }

    @Test
    void sellProcessEmptyInventory_returnsEmptyStatus() {
        SellResponse response = controller.sellProcess();

        assertEquals(SellResponse.Status.EMPTY_INVENTORY, response.getStatus());
        assertEquals(500, player.getGold());
        assertTrue(player.getInventory().isEmpty());
    }

    @Test
    void sellProcessInvalidId_doesNotModifyInventoryOrGold() {
        player.addItem(item1);

        System.setIn(new ByteArrayInputStream("99\n".getBytes()));
        controller = new MenuController(player, view, shop, cities);

        SellResponse response = controller.sellProcess();

        assertEquals(SellResponse.Status.NOT_FOUND, response.getStatus());
        assertEquals(500, player.getGold());
        assertTrue(player.getInventory().contains(item1));
    }
}