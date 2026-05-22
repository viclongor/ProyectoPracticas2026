package org.sopra.rogueguild.service;

import org.sopra.rogueguild.repository.model.Items.*;
import util.NumUtil;

import java.util.ArrayList;
import java.util.List;

import static org.sopra.rogueguild.repository.model.Items.ItemCategory.*;

public class ItemGenerator {
    private static List<String> weaponPrefixes = new ArrayList<>( List.of(
            "Espada","Hacha","Daga","Lanza","Mandoble","Arco","Maza","Bastón"
    ));
    private static List<String> armorPrefixes = new ArrayList<>(List.of(
            "Armadura","Cota","Peto","Coraza","Malla"
    ));
    private static List<String> bootsPrefixes = new ArrayList<>(List.of(
            "Botas","Grebas","Sandalias","Escarpines"
    ));
    private static List<String> helmetPrefixes = new ArrayList<>(List.of(
            "Yelmo","Casco","Celada","Capucha","Visera"
    ));
    private static List<String> potionPrefixes  = new ArrayList<>(List.of(
            "Poción","Elixir","Brebaje","Ungüento","Tintura"
    ));

    private static List<String> suffixes = new ArrayList<>(List.of(
            "de fuego", "de hielo", "del rayo", "de la tormenta", "de la sombra", "de la luz",
            "de hierro", "de plata", "de obsidiana", "de acero rúnico", "de bronce antiguo",
            "del dragón", "del fénix", "del caos", "del vacío", "del alba", "de la luna",
            "del norte", "de las ruinas", "del bosque maldito", "de las profundidades", "de la montaña"
    ));

    private ItemCategory category;
    private static List<ItemCategory>availableCategories = new ArrayList<>(List.of(WEAPON, ARMOR, BOOTS, HELMET, POTION));
    private String name;
    private int price;
    private int idCounter=3;
    public ItemGenerator(){

    }

    public Item generate(){
        if(hasAvailableCategories()){
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

                    return new Potion(idCounter,name, price,"<PLACE_HOLDER>",(int) NumUtil.generate(5,100));
                }
                default -> {
                    return null;
                }
            }
        } else{
            return null;
        }

    }

    private int generateRandomPrice(){
        int priceAux = 0;
        switch (category){
            case WEAPON -> priceAux =  (int) NumUtil.generate(WEAPON.getMinPrice() ,WEAPON.getMaxPrice());
            case ARMOR -> priceAux = (int) NumUtil.generate(ARMOR.getMinPrice() ,ARMOR.getMaxPrice());
            case BOOTS -> priceAux = (int) NumUtil.generate(BOOTS.getMinPrice() ,BOOTS.getMaxPrice());
            case HELMET -> priceAux = (int) NumUtil.generate(HELMET.getMinPrice() ,HELMET.getMaxPrice());
            case POTION -> priceAux = (int) NumUtil.generate(POTION.getMinPrice() ,POTION.getMaxPrice());
        }
        priceAux = NumUtil.roundTo5(priceAux);
        return priceAux;
    }
    private String generateName(){
        String nameAux = null;
        int randNumSufix = (int) NumUtil.generate(0,suffixes.size()-1);

        switch (category){
            case WEAPON -> nameAux = weaponPrefixes.remove( NumUtil.generateInt(0,weaponPrefixes.size()-1));
            case ItemCategory.ARMOR -> nameAux = armorPrefixes.remove( NumUtil.generateInt(0,armorPrefixes.size()-1));
            case ItemCategory.BOOTS -> nameAux = bootsPrefixes.remove(NumUtil.generateInt(0,bootsPrefixes.size()-1));
            case ItemCategory.HELMET -> nameAux = helmetPrefixes.remove(NumUtil.generateInt(0,helmetPrefixes.size()-1));
            case ItemCategory.POTION -> nameAux = potionPrefixes.remove(NumUtil.generateInt(0,potionPrefixes.size()-1));
        }
        nameAux += (" " + suffixes.remove(randNumSufix));

        return nameAux;
    }
    private ItemCategory genertateItemType(){

        int randNum = (int) NumUtil.generate(1,availableCategories.size());
        ItemCategory categoryAux = null;
        switch (randNum){
            case 1 -> {
                categoryAux = WEAPON;
                removeEmptyList(weaponPrefixes,WEAPON);
            }
            case 2 -> {
                categoryAux = ARMOR;
                removeEmptyList(armorPrefixes,ARMOR);
            }
            case 3 -> {
                categoryAux = BOOTS;
                removeEmptyList(bootsPrefixes,BOOTS);
            }
            case 4 ->{
                categoryAux = HELMET;
                removeEmptyList(helmetPrefixes,HELMET);
            }
            case 5 ->{
                categoryAux = POTION;
                removeEmptyList(potionPrefixes,POTION);
            }
        }
        return categoryAux;
    }
    private void removeEmptyList(List<String> list, ItemCategory category){
        if (list.isEmpty()){
            availableCategories.remove(category);
        }
    }

    private boolean hasAvailableCategories(){
        return !availableCategories.isEmpty();
    }
}