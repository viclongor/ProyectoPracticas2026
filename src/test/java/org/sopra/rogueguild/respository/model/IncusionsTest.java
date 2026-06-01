package org.sopra.rogueguild.respository.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.model.Incursions.ConquestIncursion;
import org.sopra.rogueguild.repository.model.Incursions.PillageIncursion;
import org.sopra.rogueguild.repository.model.Incursions.SmallIncursion;
import org.sopra.rogueguild.service.ItemGenerator;

import static org.junit.jupiter.api.Assertions.*;

class IncursionTest {

    ItemGenerator itemGenerator;

    @BeforeEach
    void setUp() {
        itemGenerator = new ItemGenerator();
    }

    @Test
    void incursionHasFourRequiredFields() {
        SmallIncursion incursion = new SmallIncursion("Bandidos", "Descripcion", itemGenerator);

        assertNotNull(incursion.getShortName());
        assertNotNull(incursion.getDescription());
        assertTrue(incursion.getGoldReward() >= 0);
        assertNotNull(incursion.getItemReward());
    }

    @Test
    void pillageIncursionAlwaysHasGold() {
        PillageIncursion incursion = new PillageIncursion("Pillaje", "Descripcion", itemGenerator);
        assertTrue(incursion.getGoldReward() > 0);
    }

    @Test
    void conquestIncursionAlwaysHasItem() {
        ConquestIncursion incursion = new ConquestIncursion("Conquista", "Descripcion", itemGenerator);
        assertNotNull(incursion.getItemReward());
    }

    @Test
    void smallIncursionAlwaysHasBoth() {
        SmallIncursion incursion = new SmallIncursion("Bandidos", "Descripcion", itemGenerator);
        assertTrue(incursion.getGoldReward() > 0);
        assertNotNull(incursion.getItemReward());
    }

    @Test
    void pillageIncursionGoldRewardIsMultipleOfFive() {
        for (int i = 0; i < 20; i++) {
            PillageIncursion incursion = new PillageIncursion("Pillaje", "Descripcion", itemGenerator);
            assertEquals(0, incursion.getGoldReward() % 5,
                    "goldReward debe ser múltiplo de 5, fue: " + incursion.getGoldReward());
        }
    }

    @Test
    void conquestIncursionGoldRewardIsMultipleOfFiveWhenPresent() {
        for (int i = 0; i < 20; i++) {
            ConquestIncursion incursion = new ConquestIncursion("Conquista", "Descripcion", itemGenerator);
            if (incursion.getGoldReward() > 0) {
                assertEquals(0, incursion.getGoldReward() % 5,
                        "goldReward debe ser múltiplo de 5, fue: " + incursion.getGoldReward());
            }
        }
    }

    @Test
    void smallIncursionGoldRewardIsMultipleOfFive() {
        for (int i = 0; i < 20; i++) {
            SmallIncursion incursion = new SmallIncursion("Bandidos", "Descripcion", itemGenerator);
            assertEquals(0, incursion.getGoldReward() % 5,
                    "goldReward debe ser múltiplo de 5, fue: " + incursion.getGoldReward());
        }
    }

    @Test
    void incursionShortNameAndDescriptionAreNotEmpty() {
        SmallIncursion incursion = new SmallIncursion("Bandidos", "Campamento de bandidos peligrosos", itemGenerator);

        assertFalse(incursion.getShortName().isBlank());
        assertFalse(incursion.getDescription().isBlank());
    }

    @Test
    void incursionShortNameAndDescriptionMatchConstructorValues() {
        String name = "El campamento";
        String desc  = "Una incursión difícil";
        SmallIncursion incursion = new SmallIncursion(name, desc, itemGenerator);

        assertEquals(name, incursion.getShortName());
        assertEquals(desc, incursion.getDescription());
    }
}