package org.sopra.rogueguild.service;

import org.sopra.rogueguild.repository.model.Items.*;
import util.NumUtil;

import java.util.ArrayList;
import java.util.List;

import static org.sopra.rogueguild.repository.model.Items.ItemCategory.*;

public class ItemGenerator {
    private final List<String> ORIGINAL_WEAPON_PREFIXES = new ArrayList<>( List.of(
            "Espada","Hacha","Daga","Lanza","Mandoble","Arco","Maza","Bastón"
    ));
    private final List<String> ORIGINAL_ARMOR_PREFIXES = new ArrayList<>(List.of(
            "Armadura","Cota","Peto","Coraza","Malla"
    ));
    private final List<String> ORIGINAL_BOOTS_PREFIXES = new ArrayList<>(List.of(
            "Botas","Grebas","Sandalias","Escarpines"
    ));
    private final List<String> ORIGINAL_HELMET_PREFIXES = new ArrayList<>(List.of(
            "Yelmo","Casco","Celada","Capucha","Visera"
    ));
    private final List<String> ORIGINAL_POTIONS_PREFIXES  = new ArrayList<>(List.of(
            "Poción","Elixir","Brebaje","Ungüento","Tintura"
    ));

    private final List<String> ORIGINAL_SUFIXES = new ArrayList<>(List.of(
            "de fuego", "de hielo", "del rayo", "de la tormenta", "de la sombra", "de la luz",
            "de hierro", "de plata", "de obsidiana", "de acero rúnico", "de bronce antiguo",
            "del dragón", "del fénix", "del caos", "del vacío", "del alba", "de la luna",
            "del norte", "de las ruinas", "del bosque maldito", "de las profundidades", "de la montaña"
    ));
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
    private static final List<ItemCategory>availableCategories = new ArrayList<>(List.of(WEAPON, ARMOR, BOOTS, HELMET, POTION));
    private int idCounter=3;
    public ItemGenerator(){

    }

    public Item generate(){
            category = genertateItemType();
        String name = generateName();
        int price = generateRandomPrice();
            idCounter = idCounter+1;
            switch (category){
                case WEAPON -> {

                    return new Weapon(idCounter, name, price,(int) NumUtil.generate(5,100));
                }
                case ARMOR -> {

                    return new Armor(idCounter, name, price,(int) NumUtil.generate(5,100));
                }
                case BOOTS -> {

                    return new Boots(idCounter, name, price,(int) NumUtil.generate(5,100));
                }
                case HELMET -> {

                    return new Helmet(idCounter, name, price,(int) NumUtil.generate(5,100));
                }
                case POTION -> {

                    return new Potion(idCounter, name, price,"<PLACE_HOLDER>",(int) NumUtil.generate(5,100));
                }
                default -> {
                    return null;
                }
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
                refreshEmptyList(WEAPON);
            }
            case 2 -> {
                categoryAux = ARMOR;
                refreshEmptyList(ARMOR);
            }
            case 3 -> {
                categoryAux = BOOTS;
                refreshEmptyList(BOOTS);
            }
            case 4 ->{
                categoryAux = HELMET;
                refreshEmptyList(HELMET);
            }
            case 5 ->{
                categoryAux = POTION;
                refreshEmptyList(POTION);
            }
        }
        return categoryAux;
    }
    private void refreshEmptyList(ItemCategory category){

        switch (category){
            case WEAPON -> {
                if(weaponPrefixes.isEmpty()){
                    weaponPrefixes = new ArrayList<>(ORIGINAL_WEAPON_PREFIXES);
                }
            }
            case ARMOR -> {
                if(armorPrefixes.isEmpty()){
                    armorPrefixes = new ArrayList<>(ORIGINAL_ARMOR_PREFIXES);
                }
            }
            case BOOTS -> {
                if(bootsPrefixes.isEmpty()){
                    bootsPrefixes = new ArrayList<>(ORIGINAL_BOOTS_PREFIXES);
                }
            }
            case HELMET -> {
                if(helmetPrefixes.isEmpty()){
                    helmetPrefixes = new ArrayList<>(ORIGINAL_HELMET_PREFIXES);
                }
            }
            case POTION -> {
                if(potionPrefixes.isEmpty()){
                    potionPrefixes = new ArrayList<>(ORIGINAL_POTIONS_PREFIXES);
                }
            }
        }
        if(suffixes.isEmpty()){
            suffixes = new ArrayList<>(ORIGINAL_SUFIXES);
        }
    }
}