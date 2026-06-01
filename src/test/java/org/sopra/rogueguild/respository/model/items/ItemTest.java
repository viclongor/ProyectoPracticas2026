package org.sopra.rogueguild.respository.model.items;


import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.model.Items.Item;
import org.sopra.rogueguild.repository.model.Items.Weapon;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void basePriceYPriceEqualsOnCreate() {
        Item item = new Weapon(1, "Espada de fuego", 100, 10);
        assertEquals(item.getBasePrice(), item.getPrice());
    }

    @Test
    void modfyPriceDoesntAlterBasePrice() {
        Item item = new Weapon(1, "Espada de fuego", 100, 10);
        item.setPrice(200);
        assertEquals(100, item.getBasePrice());
    }

    @Test
    void sellPriceCalculatedOnBasePrice() {
        Item item = new Weapon(1, "Espada de fuego", 100, 10);
        item.setPrice(200); // simulamos un evento del mundo
        int sellPrice = (int) (Math.round((item.getBasePrice() * 0.8) / 5.0) * 5);
        assertEquals(80, sellPrice);
    }

    @Test
    void basePriceDoesntChageAfterCreation() {
        Item item = new Weapon(1, "Espada de fuego", 100, 10);
        item.setPrice(50);
        item.setPrice(300);
        assertEquals(100, item.getBasePrice());
    }
}