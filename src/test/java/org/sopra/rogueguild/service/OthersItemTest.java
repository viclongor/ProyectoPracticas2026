package org.sopra.rogueguild.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.model.Items.Item;
import org.sopra.rogueguild.repository.model.Items.ItemCategory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OthersItemTest {

    private ItemGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new ItemGenerator();
    }

    @Test
    void othersExisteEnItemCategory() {
        ItemCategory others = ItemCategory.OTHERS;
        assertNotNull(others);
    }

    @Test
    void othersRangoPrecioCorrecto() {
        assertEquals(250, ItemCategory.OTHERS.getMinPrice());
        assertEquals(300, ItemCategory.OTHERS.getMaxPrice());
    }

    @Test
    void othersItemPrecioEntreRango() {
        for (int i = 0; i < 200; i++) {
            Item item = generator.generate();
            if (item.getCategory() == ItemCategory.OTHERS) {
                assertTrue(item.getPrice() >= 250 && item.getPrice() <= 300,
                        "Precio OTHERS fuera de rango: " + item.getPrice());
            }
        }
    }

    @Test
    void othersItemPrecioMultiploDeCinco() {
        for (int i = 0; i < 200; i++) {
            Item item = generator.generate();
            if (item.getCategory() == ItemCategory.OTHERS) {
                assertEquals(0, item.getPrice() % 5,
                        "Precio OTHERS no es múltiplo de 5: " + item.getPrice());
            }
        }
    }

    @Test
    void othersApareceCon5PorcientoProbabilidad() {
        int total = 1000;
        int othersCount = 0;
        for (int i = 0; i < total; i++) {
            Item item = generator.generate();
            if (item.getCategory() == ItemCategory.OTHERS) {
                othersCount++;
            }
        }
        double probability = (double) othersCount / total * 100;
        assertTrue(probability >= 1 && probability <= 15,
                "Probabilidad OTHERS fuera de rango esperado: " + probability + "%");
    }

    @Test
    void othersNombreUnico() {
        List<String> nombres = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            Item item = generator.generate();
            if (item.getCategory() == ItemCategory.OTHERS) {
                assertFalse(nombres.contains(item.getName()),
                        "Nombre OTHERS repetido: " + item.getName());
                nombres.add(item.getName());
            }
        }
    }

    @Test
    void othersItemTieneNombre() {
        for (int i = 0; i < 200; i++) {
            Item item = generator.generate();
            if (item.getCategory() == ItemCategory.OTHERS) {
                assertNotNull(item.getName());
                assertFalse(item.getName().isEmpty());
            }
        }
    }
}