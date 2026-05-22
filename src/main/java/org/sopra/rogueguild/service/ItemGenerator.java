package org.sopra.rogueguild.service;

import org.sopra.rogueguild.repository.model.Items.*;
import util.NumUtil;

import java.util.ArrayList;
import java.util.List;

public class ItemGenerator {
    private List<String> weaponPrefixes = new ArrayList<>( List.of(
            "Espada","Hacha","Daga","Lanza","Mandoble","Arco","Maza","Bastón"
    ));
    private List<String> armorPrefixes = new ArrayList<>(List.of(
            "Armadura","Cota","Peto","Coraza","Malla"
    ));
    private List<String> bootsPrefixes = new ArrayList<>(List.of(
            "Botas","Grebas","Sandalias","Escarpines"
    ));
    private List<String> helmetPrefixes = new ArrayList<>(List.of(
            "Yelmo","Casco","Celada","Capucha","Visera"
    ));
    private List<String> potionPrefixes  = new ArrayList<>(List.of(
            "Poción","Elixir","Brebaje","Ungüento","Tintura"
    ));

    private List<String> suffixes = new ArrayList<>(List.of(
            "de fuego", "de hielo", "del rayo", "de la tormenta", "de la sombra", "de la luz",
            "de hierro", "de plata", "de obsidiana", "de acero rúnico", "de bronce antiguo",
            "del dragón", "del fénix", "del caos", "del vacío", "del alba", "de la luna",
            "del norte", "de las ruinas", "del bosque maldito", "de las profundidades", "de la montaña"
    ));

    private ItemCategory category;

    private String name;
    private int price;
    private int idCounter=3;
    public ItemGenerator(){

    }

    public Item generate(){
        category = genertateItemType();
        name = generateName();
        price = generateRandomPrice();
        idCounter = idCounter+1;
        switch (category){
            case WEAPON -> {

                return new Weapon(idCounter,name, price,(int) NumUtil.generate(5,100));
            }
            case ARMOR -> {

                return new Armor(idCounter,name, price,(int) NumUtil.generate(5,100));
            }
            case BOOTS -> {

                return new Boots(idCounter,name, price,(int) NumUtil.generate(5,100));
            }
            case HELMET -> {

                return new Helmet(idCounter,name, price,(int) NumUtil.generate(5,100));
            }
            case POTION -> {

                return new Potion(idCounter,name, price,"test",(int) NumUtil.generate(5,100));
            }
            default -> {
                return null;
            }
        }
    }

    private int generateRandomPrice(){
        int priceAux = 0;
        switch (category){
            case WEAPON -> priceAux =  (int) NumUtil.generate(50 ,200);
            case ARMOR -> priceAux = (int) NumUtil.generate(20 ,100);
            case BOOTS -> priceAux = (int) NumUtil.generate(20 ,150);
            case HELMET -> priceAux = (int) NumUtil.generate(100 ,300);
            case POTION -> priceAux = (int) NumUtil.generate(10 ,40);
        }
        priceAux = NumUtil.roundTo5(priceAux);
        return priceAux;
    }
    private String generateName(){
        String nameAux = null;
        int randNumSufix = (int) NumUtil.generate(0,suffixes.size()-1);

        switch (category){
            case ItemCategory.WEAPON -> nameAux = weaponPrefixes.remove( NumUtil.generateInt(0,weaponPrefixes.size()-1));
            case ItemCategory.ARMOR -> nameAux = armorPrefixes.remove( NumUtil.generateInt(0,armorPrefixes.size()-1));
            case ItemCategory.BOOTS -> nameAux = bootsPrefixes.remove(NumUtil.generateInt(0,bootsPrefixes.size()-1));
            case ItemCategory.HELMET -> nameAux = helmetPrefixes.remove(NumUtil.generateInt(0,helmetPrefixes.size()-1));
            case ItemCategory.POTION -> nameAux = potionPrefixes.remove(NumUtil.generateInt(0,potionPrefixes.size()-1));
        }
        nameAux += (" " + suffixes.remove(randNumSufix));

        return nameAux;
    }
    private ItemCategory genertateItemType(){
        int randNum = (int) NumUtil.generate(1,5);
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