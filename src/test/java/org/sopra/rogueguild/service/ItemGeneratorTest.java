package org.sopra.rogueguild.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.model.Items.Item;
import org.sopra.rogueguild.repository.model.Items.Others;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.sopra.rogueguild.repository.model.Items.ItemCategory.*;

class ItemGeneratorTest {

    private ItemGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new ItemGenerator();
    }

    @Test
    void generateReturnsItemNotNull() {
        Item item = generator.generate();
        assertNotNull(item);
    }

    @Test
    void itemHasCategory() {
        Item item = generator.generate();
        assertNotNull(item.getCategory());
    }

    @Test
    void itemHasNameWithPrefixAndSufix() {
        Item item = generator.generate();
        assertNotNull(item.getName());
        assertTrue(item.getName().contains(" "));
    }

    @Test
    void priceIsMultipleOfFive() {
        for (int i = 0; i < 20; i++) {
            Item item = generator.generate();
            assertEquals(0, item.getPrice() % 5,
                    "El precio " + item.getPrice() + " no es múltiplo de 5");
        }
    }

    @Test
    void basePriceAndPriceEqualsOnGenerate() {
        for (int i = 0; i < 20; i++) {
            Item item = generator.generate();
            assertEquals(item.getBasePrice(), item.getPrice());
        }
    }

    @Test
    void priceWeaponInsideRange() {
        for (int i = 0; i < 30; i++) {
            Item item = generator.generate();
            if (item.getCategory() == WEAPON) {
                assertTrue(item.getPrice() >= WEAPON.getMinPrice() && item.getPrice() <= WEAPON.getMaxPrice(),
                        "Precio WEAPON fuera de rango: " + item.getPrice());
            }
        }
    }

    @Test
    void priceArmorInsideRange() {
        for (int i = 0; i < 30; i++) {
            Item item = generator.generate();
            if (item.getCategory() == ARMOR) {
                assertTrue(item.getPrice() >= ARMOR.getMinPrice() && item.getPrice() <= ARMOR.getMaxPrice(),
                        "Precio ARMOR fuera de rango: " + item.getPrice());
            }
        }
    }

    @Test
    void priceBootsInsideRange() {
        for (int i = 0; i < 30; i++) {
            Item item = generator.generate();
            if (item.getCategory() == BOOTS) {
                assertTrue(item.getPrice() >= BOOTS.getMinPrice() && item.getPrice() <= BOOTS.getMaxPrice(),
                        "Precio BOOTS fuera de rango: " + item.getPrice());
            }
        }
    }

    @Test
    void priceHelmetInsideRange() {
        for (int i = 0; i < 30; i++) {
            Item item = generator.generate();
            if (item.getCategory() == HELMET) {
                assertTrue(item.getPrice() >= HELMET.getMinPrice() && item.getPrice() <= HELMET.getMaxPrice(),
                        "Precio HELMET fuera de rango: " + item.getPrice());
            }
        }
    }

    @Test
    void pricePotionInsideRange() {
        for (int i = 0; i < 30; i++) {
            Item item = generator.generate();
            if (item.getCategory() == POTION) {
                assertTrue(item.getPrice() >= POTION.getMinPrice() && item.getPrice() <= POTION.getMaxPrice(),
                        "Precio POTION fuera de rango: " + item.getPrice());
            }
        }
    }
    @Test
    void priceOtherInsideRange() {
        for (int i = 0; i < 30; i++) {
            Item item = generator.generate();
            if (item.getCategory() == OTHERS) {
                assertTrue(item.getPrice() >= OTHERS.getMinPrice() && item.getPrice() <= OTHERS.getMaxPrice(),
                        "Precio OTHER fuera de rango: " + item.getPrice());
            }
        }
    }

    @Test
    void doesntRepeatNamesInSesion() {
        List<String> nombres = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Item item = generator.generate();
            assertFalse(nombres.contains(item.getName()),
                    "Nombre repetido: " + item.getName());
            nombres.add(item.getName());
        }
    }
}