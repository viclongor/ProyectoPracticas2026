package org.sopra.rogueguild.service;

import org.sopra.rogueguild.repository.model.*;
import util.NumMalipulator;
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
            case WEAPON -> {

                return new Weapon(name, price,(int) NumMalipulator.generate(5,100));
            }
            case ARMOR -> {

                return new Armor(name, price,(int) NumMalipulator.generate(5,100));
            }
            case BOOTS -> {

                return new Boots(name, price,(int) NumMalipulator.generate(5,100));
            }
            case HELMET -> {

                return new Helmet(name, price,(int) NumMalipulator.generate(5,100));
            }
            case POTION -> {

                return new Potion(name, price,"test",(int) NumMalipulator.generate(5,100));
            }
            default -> {
                return null;
            }
        }
    }

    private int generateRandomPrice(){
        int priceAux = 0;
        switch (category){
            case WEAPON -> priceAux =  (int) NumMalipulator.generate(50 ,200);
            case ARMOR -> priceAux = (int) NumMalipulator.generate(20 ,100);
            case BOOTS -> priceAux = (int) NumMalipulator.generate(20 ,150);
            case HELMET -> priceAux = (int) NumMalipulator.generate(100 ,300);
            case POTION -> priceAux = (int) NumMalipulator.generate(10 ,40);
        }
        priceAux = NumMalipulator.roundTo5(priceAux);
        return priceAux;
    }
    private String generateName(){
        String nameAux = null;
        int randNumSufix = (int) NumMalipulator.generate(1,27);

        switch (category){
            case ItemCategory.WEAPON -> nameAux = WEAPON_PREFIXES.get( NumMalipulator.generateInt(1,8));
            case ItemCategory.ARMOR -> nameAux = ARMOR_PREFIXES.get( NumMalipulator.generateInt(1,5));
            case ItemCategory.BOOTS -> nameAux = BOOTS_PREFIXES.get(NumMalipulator.generateInt(1,4));
            case ItemCategory.HELMET -> nameAux = HELMET_PREFIXES.get(NumMalipulator.generateInt(1,5));
            case ItemCategory.POTION -> nameAux = POTION_PREFIXES.get(NumMalipulator.generateInt(1,5));
        }
        nameAux += (" " + SUFFIXES.get(randNumSufix));

        return nameAux;
    }
    private ItemCategory genertateItemType(){
        int randNum = (int) NumMalipulator.generate(1,5);
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