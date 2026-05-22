package org.sopra.rogueguild.repository;

import java.util.LinkedHashMap;
import java.util.Map;
import org.sopra.rogueguild.repository.model.WorldEvent;
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

    public void removeItem(int id) { stock.remove(id); }

    public Map<Integer, Item> getAllStock() {
        return stock;
    }

    public void addItem(int id, Item item) {
        stock.put(id, item);
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
}