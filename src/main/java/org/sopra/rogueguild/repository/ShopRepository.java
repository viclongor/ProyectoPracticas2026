package org.sopra.rogueguild.repository;

import java.util.LinkedHashMap;
import java.util.Map;
import org.sopra.rogueguild.repository.model.WorldEvent;
import org.sopra.rogueguild.service.ItemGenerator;
import util.NumUtil;
import org.sopra.rogueguild.repository.model.Items.Armor;
import org.sopra.rogueguild.repository.model.Items.Item;
import org.sopra.rogueguild.repository.model.Items.Weapon;

public class ShopRepository {
    private Map<Integer, Item> stock;

    public ShopRepository() {
        stock = new LinkedHashMap<>();
        loadInitialStock();
    }

    private void loadInitialStock() {
        stock.put(1, new Weapon(1,"Daga de las Sombras", 150, 10));
        stock.put(2, new Weapon(2,"Espada del Renegado", 350, 15));
        stock.put(3, new Armor(3,"Armadura del Sol Naciente", 200, 5));
    }

    public Item getItem(int id) {
        return stock.get(id);
    }

    public void removeItem(int id) {
        stock.remove(id);
        Map<Integer, Item> reordered = new LinkedHashMap<>();
        int newId = 1;
        for (Item item : stock.values()) {
            item.setOriginalId(newId);
            reordered.put(newId, item);
            newId++;
        }
        stock = reordered;
    }
    public Map<Integer, Item> getAllStock() {
        return stock;
    }

    public void addItem(Item item) {
        int nextId = stock.keySet().stream().mapToInt(Integer::intValue).max().orElse(0) + 1;
        item.setOriginalId(nextId);
        stock.put(nextId, item);
    }
    public void applyWorldEvent(WorldEvent event) {
        for (Item item : stock.values()) {
            boolean affected = event.isGlobal()
                    || item.getCategory() == event.getAffectedCategory();
            if (affected) {
                int newPrice = NumUtil.roundTo5(item.getBasePrice() * event.getFactor());
                item.setPrice(newPrice);
            }
        }
    }
    public void refreshShop(ItemGenerator itemGenerator) {
        stock.clear();
        for (int i = 1; i <= 3; i++) {
            Item item = itemGenerator.generate();
            item.setOriginalId(i);
            stock.put(i, item);
        }
    }
}