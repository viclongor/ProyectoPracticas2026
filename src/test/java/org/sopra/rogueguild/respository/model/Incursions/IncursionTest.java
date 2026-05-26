package org.sopra.rogueguild.respository.model.Incursions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.model.Incursions.ConquestIncursion;
import org.sopra.rogueguild.repository.model.Incursions.PillageIncursion;
import org.sopra.rogueguild.repository.model.Incursions.SmallIncursion;
import org.sopra.rogueguild.repository.model.Items.RewardBag;
import org.sopra.rogueguild.service.ItemGenerator;

import static org.junit.jupiter.api.Assertions.*;

class IncursionTest {

    private ItemGenerator itemGenerator;

    @BeforeEach
    void setUp() {
        itemGenerator = new ItemGenerator();
    }

    // HU-07: Definición de incursiones
    @Test
    void incursionTieneCamposRequeridos() {
        ConquestIncursion incursion = new ConquestIncursion("El Santuario", "Descripcion", itemGenerator);
        assertNotNull(incursion.getShortName());
        assertNotNull(incursion.getDescription());
        assertFalse(incursion.getShortName().isEmpty());
        assertFalse(incursion.getDescription().isEmpty());
    }

    @Test
    void goldRewardEsMultiploDeCinco() {
        for (int i = 0; i < 20; i++) {
            SmallIncursion incursion = new SmallIncursion("Test", "Desc", itemGenerator);
            assertEquals(0, incursion.getGoldReward() % 5);
        }
    }

    @Test
    void conquestIncursionTieneItemReward() {
        ConquestIncursion incursion = new ConquestIncursion("Test", "Desc", itemGenerator);
        assertNotNull(incursion.getItemReward());
    }

    @Test
    void pillageIncursionTieneGoldReward() {
        PillageIncursion incursion = new PillageIncursion("Test", "Desc", itemGenerator);
        assertTrue(incursion.getGoldReward() > 0);
    }

    @Test
    void smallIncursionTieneAmbasRecompensas() {
        SmallIncursion incursion = new SmallIncursion("Test", "Desc", itemGenerator);
        assertNotNull(incursion.getItemReward());
        assertTrue(incursion.getGoldReward() > 0);
    }

    // HU-08: Realización de incursiones
    @Test
    void smallIncursionItemPrecioMaximo50() {
        for (int i = 0; i < 20; i++) {
            SmallIncursion incursion = new SmallIncursion("Test", "Desc", itemGenerator);
            assertTrue(incursion.getItemReward().getBasePrice() <= 50,
                    "Precio del item supera 50: " + incursion.getItemReward().getBasePrice());
        }
    }

    @Test
    void smallIncursionOroMaximo30() {
        for (int i = 0; i < 20; i++) {
            SmallIncursion incursion = new SmallIncursion("Test", "Desc", itemGenerator);
            assertTrue(incursion.getGoldReward() <= 30,
                    "Oro supera 30: " + incursion.getGoldReward());
        }
    }

    @Test
    void completeQuestDevuelveRewardBag() {
        SmallIncursion incursion = new SmallIncursion("Test", "Desc", itemGenerator);
        RewardBag bag = incursion.completeQuest();
        assertNotNull(bag);
    }

    // HU-09: Renovación del stock
    @Test
    void refreshShopReemplazaTodoElStock() {
        org.sopra.rogueguild.repository.ShopRepository repository = new org.sopra.rogueguild.repository.ShopRepository();
        var stockAntes = repository.getAllStock().values().stream().toList();
        repository.refreshShop(itemGenerator);
        var stockDespues = repository.getAllStock().values().stream().toList();
        for (var item : stockDespues) {
            assertFalse(stockAntes.contains(item), "El stock nuevo contiene ítems del anterior");
        }
    }

    @Test
    void refreshShopMantieneTresItems() {
        org.sopra.rogueguild.repository.ShopRepository repository = new org.sopra.rogueguild.repository.ShopRepository();
        repository.refreshShop(itemGenerator);
        assertEquals(3, repository.getAllStock().size());
    }

    @Test
    void refreshShopItemsNuevosCumplenRangos() {
        org.sopra.rogueguild.repository.ShopRepository repository = new org.sopra.rogueguild.repository.ShopRepository();
        repository.refreshShop(itemGenerator);
        for (var item : repository.getAllStock().values()) {
            assertTrue(item.getBasePrice() >= item.getCategory().getMinPrice()
                            && item.getBasePrice() <= item.getCategory().getMaxPrice(),
                    "Precio fuera de rango: " + item.getBasePrice());
        }
    }
}