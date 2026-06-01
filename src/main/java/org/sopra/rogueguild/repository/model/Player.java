package org.sopra.rogueguild.repository.model;
import org.sopra.rogueguild.repository.model.Items.*;

import java.util.*;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.Queue;

public class Player {
    private String name;
    private int gold;
    private List<Item> inventory = new ArrayList<>();
    private City currentCity;
    private final Map<ItemCategory, List<Item>> equippedItems = new HashMap<>(Map.of(
            ItemCategory.WEAPON, new ArrayList<>(),
            ItemCategory.ARMOR,  new ArrayList<>(),
            ItemCategory.HELMET, new ArrayList<>(),
            ItemCategory.BOOTS,  new ArrayList<>()
    ));

    private static final Map<ItemCategory, Integer> SLOT_LIMITS = Map.of(
            ItemCategory.WEAPON, 2,
            ItemCategory.ARMOR,  1,
            ItemCategory.HELMET, 1,
            ItemCategory.BOOTS,  1
    );

    public Player(String name, int gold) {
        this.name = name;
        this.gold = gold;
    }

    public String getName() { return name; }
    public int getGold() { return gold; }
    public List<Item> getInventory() { return inventory; }
    public Map<ItemCategory, List<Item>> getEquippedItems() { return equippedItems; }
    public City getCurrentCity() { return currentCity; }
    public void setCurrentCity(City city) { this.currentCity = city; }

    public int getAttack() {
        return equippedItems.getOrDefault(ItemCategory.WEAPON, new ArrayList<>())
                .stream()
                .mapToInt(item -> ((Weapon) item).getDamage())
                .sum();
    }

    public int getArmor() {
        int armorVal = equippedItems.getOrDefault(ItemCategory.ARMOR, new ArrayList<>())
                .stream()
                .mapToInt(item -> ((Armor) item).getArmor())
                .sum();
        int helmetVal = equippedItems.getOrDefault(ItemCategory.HELMET, new ArrayList<>())
                .stream()
                .mapToInt(item -> ((Helmet) item).getArmor())
                .sum();
        return armorVal + helmetVal;
    }

    public void buy(Item item) { this.gold -= item.getPrice(); addItem(item); }
    public void receiveGold(int amount) {
        if((this.gold + amount) <= 500){
            this.gold += amount;
        }
    }
    public void setGold(int gold){
        this.gold = gold;
    }

    public boolean addItem(Item item){
        return inventory.add(item);
    }
    public boolean removeItem(Item item){
        return inventory.remove(item);
    }

    public String showInventory(){
        StringBuilder str = new StringBuilder("||    INVENTARIO     ||");
        for(Item item : inventory){
            str.append("\n");
            str.append(item.toString());
        }
        return str.toString();
    }

    public boolean unequipItem(Item item) {
        ItemCategory cat = item.getCategory();
        List<Item> slots = equippedItems.get(cat);
        if (slots == null || !slots.remove(item)) return false;
        inventory.add(item);
        return true;
    }

    public boolean isEquipped(Item item) {
        List<Item> slots = equippedItems.get(item.getCategory());
        return slots != null && slots.contains(item);
    }

    public boolean equipItem(Item item) {
        ItemCategory cat = item.getCategory();
        if (!SLOT_LIMITS.containsKey(cat)) return false;

        List<Item> slots = equippedItems.get(cat);
        int limit = SLOT_LIMITS.get(cat);

        if (slots.size() < limit) {
            inventory.remove(item);
            slots.add(item);
            return true;
        }
        Item toReplace;
        if (cat == ItemCategory.WEAPON) {
            toReplace = slots.stream()
                    .min(Comparator.comparingInt(i -> ((Weapon) i).getDamage()))
                    .orElse(slots.get(0));
        } else {
            toReplace = slots.get(0);
        }

        slots.remove(toReplace);
        inventory.add(toReplace);
        inventory.remove(item);
        slots.add(item);
        return true;
    }

    public List<City> travelTo(City destination) {
        if (currentCity == null || destination == null) return null;
        if (currentCity == destination) return List.of(currentCity);

        Map<City, City> visited = new LinkedHashMap<>();
        Queue<City> queue = new LinkedList<>();
        queue.add(currentCity);
        visited.put(currentCity, null);

        while (!queue.isEmpty()) {
            City current = queue.poll();
            if (current == destination) {
                List<City> path = new ArrayList<>();
                City step = destination;
                while (step != null) {
                    path.add(0, step);
                    step = visited.get(step);
                }
                currentCity = destination;
                return path;
            }
            for (City neighbor : current.getConnectedCities()) {
                if (!visited.containsKey(neighbor)) {
                    visited.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return null;
    }
}
