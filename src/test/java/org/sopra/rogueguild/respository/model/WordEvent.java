package org.sopra.rogueguild.respository.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.Items.Item;
import org.sopra.rogueguild.repository.model.Items.ItemCategory;
import org.sopra.rogueguild.repository.model.WorldEvent;
import org.sopra.rogueguild.service.WorldEventGenerator;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class WordEvent {
    private ShopRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ShopRepository();
    }

    @Test
    void applyWorldEventCategoryEventOnlyAffectedCategoryPriceChanges() {
        WorldEvent weaponEvent = new WorldEvent(ItemCategory.WEAPON, 2.0,"");
        repository.applyWorldEvent(weaponEvent);

        Map<Integer, Item> stock = repository.getAllStock();

        Item weapon1 = stock.get(1);
        Item weapon2 = stock.get(2);
        Item armor   = stock.get(3);

        assertEquals(300, weapon1.getPrice());
        assertEquals(700, weapon2.getPrice());
        assertEquals(200, armor.getPrice());
    }

    @Test
    void applyWorldEventCategoryEventOtherCategoryPriceUnchanged() {
        WorldEvent armorEvent = new WorldEvent(ItemCategory.ARMOR, 1.5,"");
        repository.applyWorldEvent(armorEvent);

        Map<Integer, Item> stock = repository.getAllStock();

        assertEquals(150, stock.get(1).getPrice());
        assertEquals(350, stock.get(2).getPrice());
        assertEquals(300, stock.get(3).getPrice());
    }

    @Test
    void applyWorldEventGlobalEventAllItemsPriceChanged() {
        WorldEvent globalEvent = new WorldEvent(null, 2.0,"");
        repository.applyWorldEvent(globalEvent);

        Map<Integer, Item> stock = repository.getAllStock();

        assertEquals(300, stock.get(1).getPrice());
        assertEquals(700, stock.get(2).getPrice());
        assertEquals(400, stock.get(3).getPrice());
    }

    @Test
    void applyWorldEventUseBasePriceNotCurrentPrice() {
        WorldEvent event = new WorldEvent(null, 2.0,"");
        repository.applyWorldEvent(event);
        repository.applyWorldEvent(event);

        Map<Integer, Item> stock = repository.getAllStock();

        assertEquals(300, stock.get(1).getPrice());
        assertEquals(700, stock.get(2).getPrice());
        assertEquals(400, stock.get(3).getPrice());
    }

    @Test
    void applyWorldEventDifferentFactorsAlwaysFromBasePrice() {
        WorldEvent firstEvent  = new WorldEvent(null, 3.0,"");
        WorldEvent secondEvent = new WorldEvent(null, 2.0,"");

        repository.applyWorldEvent(firstEvent);
        repository.applyWorldEvent(secondEvent);

        Map<Integer, Item> stock = repository.getAllStock();

        assertEquals(300, stock.get(1).getPrice());
        assertEquals(700, stock.get(2).getPrice());
        assertEquals(400, stock.get(3).getPrice());
    }

    @Test
    void applyWorldEventResultingPriceIsMultipleOfFive() {
        WorldEvent event = new WorldEvent(null, 1.3,"");
        repository.applyWorldEvent(event);

        Map<Integer, Item> stock = repository.getAllStock();
        for (Item item : stock.values()) {
            assertEquals(0, item.getPrice() % 5);
        }
    }


    @Test
    void applyWorldEventBasePriceUnchangedAfterEvent() {
        WorldEvent event = new WorldEvent(null, 3.0,"");
        repository.applyWorldEvent(event);

        Map<Integer, Item> stock = repository.getAllStock();

        assertEquals(150, stock.get(1).getBasePrice());
        assertEquals(350, stock.get(2).getBasePrice());
        assertEquals(200, stock.get(3).getBasePrice());
    }

    @Test
    void applyWorldEventCategoryEventBasePriceUnchanged() {
        WorldEvent event = new WorldEvent(ItemCategory.WEAPON, 5.0,"");
        repository.applyWorldEvent(event);

        Map<Integer, Item> stock = repository.getAllStock();

        assertEquals(150, stock.get(1).getBasePrice());
        assertEquals(350, stock.get(2).getBasePrice());
        assertEquals(200, stock.get(3).getBasePrice());
    }
}
