package org.sopra.rogueguild.service;

import org.sopra.rogueguild.repository.model.*;
import util.RandNumGenerator;

import java.util.List;

public class ItemGenerator {
    private final List<String> WEAPON_PREFIXES = List.of(
        "Espada","Hacha","Daga","Lanza","Mandoble","Arco","Maza","Bastón"
    );
    private final List<String> ARMOR_PREFIXES = List.of(
        "Armadura","Cota","Peto","Coraza","Malla"
    );
    private final List<String> BOOTS_PREFIXES = List.of(
        "Botas","Grebas","Sandalias","Escarpines"
    );
    private final List<String> HELMET_PREFIXES = List.of(
        "Yelmo","Casco","Celada","Capucha","Visera"
    );
    private final List<String> POTION_PREFIXES  = List.of(
        "Poción","Elixir","Brebaje","Ungüento","Tintura"
    );

    private final List<String> SUFFIXES = List.of(
            "de fuego", "de hielo", "del rayo", "de la tormenta", "de la sombra", "de la luz",
            "de hierro", "de plata", "de obsidiana", "de acero rúnico", "de bronce antiguo",
            "del dragón", "del fénix", "del caos", "del vacío", "del alba", "de la luna",
            "del norte", "de las ruinas", "del bosque maldito", "de las profundidades", "de la montaña"
    );
//21 total sufixes
    private ItemCategory category;

    private String name;
    private int price;
    public ItemGenerator(){

    }

    public Item generate(){
        category = genertateItemType();
        name = generateName();
        price = generateRandomPrice();

        switch (category){
            case ItemCategory.WEAPON -> {

                return new Weapon(name, price,(int)RandNumGenerator.generate(5,100));
            }
            case ItemCategory.ARMOR -> {

                return new Armor(name, price,(int)RandNumGenerator.generate(5,100));
            }
            case ItemCategory.BOOTS -> {

                return new Boots(name, price,(int)RandNumGenerator.generate(5,100));
            }
            case ItemCategory.HELMET -> {

                return new Helmet(name, price,(int)RandNumGenerator.generate(5,100));
            }
            case ItemCategory.POTION -> {

                return new Potion(name, price,"test",(int)RandNumGenerator.generate(5,100));
            }
            default -> {
                return null;
            }
        }
    }

    private int generateRandomPrice(){
        int priceAux = 0;
        switch (category){
            case ItemCategory.WEAPON -> priceAux =  (int) RandNumGenerator.generate(50 ,200);
            case ItemCategory.ARMOR -> priceAux = (int) RandNumGenerator.generate(20 ,100);
            case ItemCategory.BOOTS -> priceAux = (int) RandNumGenerator.generate(20 ,150);
            case ItemCategory.HELMET -> priceAux = (int) RandNumGenerator.generate(100 ,300);
            case ItemCategory.POTION -> priceAux = (int) RandNumGenerator.generate(10 ,40);
        }
        priceAux = RandNumGenerator.roundTo5(priceAux);
        return priceAux;
    }
    private String generateName(){
        String nameAux = null;
        int randNumSufix = (int) RandNumGenerator.generate(1,27);
        switch (category){
            case ItemCategory.WEAPON -> nameAux = WEAPON_PREFIXES.get( RandNumGenerator.generateInt(1,8));
            case ItemCategory.ARMOR -> nameAux = ARMOR_PREFIXES.get( RandNumGenerator.generateInt(1,5));
            case ItemCategory.BOOTS -> nameAux = BOOTS_PREFIXES.get(RandNumGenerator.generateInt(1,4));
            case ItemCategory.HELMET -> nameAux = HELMET_PREFIXES.get(RandNumGenerator.generateInt(1,5));
            case ItemCategory.POTION -> nameAux = POTION_PREFIXES.get(RandNumGenerator.generateInt(1,5));
        }
        nameAux += (" " + SUFFIXES.get(randNumSufix));

        return nameAux;
    }
    private ItemCategory genertateItemType(){
        int randNum = (int) RandNumGenerator.generate(1,5);
        ItemCategory categoryAux = null;
        switch (randNum){
            case 1 -> categoryAux = ItemCategory.WEAPON;
            case 2 -> categoryAux = ItemCategory.ARMOR;
            case 3 -> categoryAux = ItemCategory.BOOTS;
            case 4 -> categoryAux = ItemCategory.HELMET;
            case 5 -> categoryAux = ItemCategory.POTION;
        }
        return categoryAux;
    }
}
