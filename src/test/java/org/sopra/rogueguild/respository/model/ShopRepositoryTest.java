package org.sopra.rogueguild.respository.model;

import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.controller.MenuController;
import org.sopra.rogueguild.controller.dto.BuyResponse;
import org.sopra.rogueguild.controller.dto.SellResponse;
import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.Items.Armor;
import org.sopra.rogueguild.repository.model.Items.Item;
import org.sopra.rogueguild.repository.model.Items.Weapon;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.view.ViewDisplay;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopRepositoryTest {
    ShopRepository shop = new ShopRepository();
    Player player = new Player("Player", 500);
    Item item1 = new Weapon(2, "Espada legendaria", 100, 20);
    Item item2 = new Armor(2, "Espada legendaria", 120, 20);

    @Test
    void playerAddItem() {
        shop.removeItem(1);
        assertEquals(2, shop.getAllStock().size());
    }

    @Test
    void buyResponseNotEnoughGold() {
        Item expensiveItem = new Weapon(2, "Espada legendaria", 600, 20);
        BuyResponse response = BuyResponse.notEnoughGold(expensiveItem, 500);
        assertEquals(BuyResponse.Status.NOT_ENOUGH_GOLD, response.getStatus());
        assertEquals(100, response.getMissingGold());
    }

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
}