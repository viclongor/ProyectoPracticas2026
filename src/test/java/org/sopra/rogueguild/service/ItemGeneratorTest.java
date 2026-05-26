package org.sopra.rogueguild.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.model.Items.Item;
import org.sopra.rogueguild.repository.model.Items.ItemCategory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemGeneratorTest {

    private ItemGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new ItemGenerator();
    }

    @Test
    void generateDevuelveItemNoNulo() {
        Item item = generator.generate();
        assertNotNull(item);
    }

    @Test
    void itemTieneCategoria() {
        Item item = generator.generate();
        assertNotNull(item.getCategory());
    }

    @Test
    void itemTieneNombreConPrefijoySufijo() {
        Item item = generator.generate();
        assertNotNull(item.getName());
        assertTrue(item.getName().contains(" "));
    }

    @Test
    void precioEsMultiploDeCinco() {
        for (int i = 0; i < 20; i++) {
            Item item = generator.generate();
            assertEquals(0, item.getPrice() % 5,
                    "El precio " + item.getPrice() + " no es múltiplo de 5");
        }
    }

    @Test
    void basePriceYPriceIgualesAlGenerar() {
        for (int i = 0; i < 20; i++) {
            Item item = generator.generate();
            assertEquals(item.getBasePrice(), item.getPrice());
        }
    }

    @Test
    void precioWeaponDentroDeRango() {
        for (int i = 0; i < 30; i++) {
            Item item = generator.generate();
            if (item.getCategory() == ItemCategory.WEAPON) {
                assertTrue(item.getPrice() >= 100 && item.getPrice() <= 300,
                        "Precio WEAPON fuera de rango: " + item.getPrice());
            }
        }
    }

    @Test
    void precioArmorDentroDeRango() {
        for (int i = 0; i < 30; i++) {
            Item item = generator.generate();
            if (item.getCategory() == ItemCategory.ARMOR) {
                assertTrue(item.getPrice() >= 50 && item.getPrice() <= 200,
                        "Precio ARMOR fuera de rango: " + item.getPrice());
            }
        }
    }

    @Test
    void precioBootsDentroDeRango() {
        for (int i = 0; i < 30; i++) {
            Item item = generator.generate();
            if (item.getCategory() == ItemCategory.BOOTS) {
                assertTrue(item.getPrice() >= 20 && item.getPrice() <= 100,
                        "Precio BOOTS fuera de rango: " + item.getPrice());
            }
        }
    }

    @Test
    void precioHelmetDentroDeRango() {
        for (int i = 0; i < 30; i++) {
            Item item = generator.generate();
            if (item.getCategory() == ItemCategory.HELMET) {
                assertTrue(item.getPrice() >= 20 && item.getPrice() <= 150,
                        "Precio HELMET fuera de rango: " + item.getPrice());
            }
        }
    }

    @Test
    void precipoPotionDentroDeRango() {
        for (int i = 0; i < 30; i++) {
            Item item = generator.generate();
            if (item.getCategory() == ItemCategory.POTION) {
                assertTrue(item.getPrice() >= 10 && item.getPrice() <= 40,
                        "Precio POTION fuera de rango: " + item.getPrice());
            }
        }
    }

    @Test
    void noSeRepiteNombreEnSesion() {
        List<String> nombres = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Item item = generator.generate();
            assertFalse(nombres.contains(item.getName()),
                    "Nombre repetido: " + item.getName());
            nombres.add(item.getName());
        }
    }
}