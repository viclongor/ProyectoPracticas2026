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
    private static List<String> weaponPrefixes = new ArrayList<>();

    private final List<String> ORIGINAL_ARMOR_PREFIXES = new ArrayList<>(List.of(
            "Armadura","Cota","Peto","Coraza","Malla","Placas"
    ));
    private static List<String> armorPrefixes = new ArrayList<>();
    private final List<String> ORIGINAL_BOOTS_PREFIXES = new ArrayList<>(List.of(
            "Botas","Grebas","Sandalias","Escarpines","Alpargata"
    ));
    private static List<String> bootsPrefixes = new ArrayList<>();
    private final List<String> ORIGINAL_HELMET_PREFIXES = new ArrayList<>(List.of(
            "Yelmo","Casco","Celada","Capucha","Visera","Morrion"
    ));
    private static List<String> helmetPrefixes = new ArrayList<>();
    private final List<String> ORIGINAL_POTIONS_PREFIXES  = new ArrayList<>(List.of(
            "Poción","Elixir","Brebaje","Ungüento","Tintura","Jarabe"
    ));
    private static List<String> potionPrefixes  = new ArrayList<>();

    private final List<String> ORIGINAL_OTHER_PREFIXES = new ArrayList<>(List.of(
            "Acero","Metal","Hierro","Piedra","Gema","Cristal","Pluma","Esencia"
    ));
    private static List<String> otherPrefixes = new ArrayList<>();

    private final List<String> ORIGINAL_SUFIXES = new ArrayList<>(List.of(
            "de fuego", "de hielo", "del rayo", "de la tormenta", "de la sombra", "de la luz",
            "de hierro", "de plata", "de obsidiana", "de acero rúnico", "de bronce antiguo",
            "del dragón", "del fénix", "del caos", "del vacío", "del alba", "de la luna",
            "del norte", "de las ruinas", "del bosque maldito", "de las profundidades", "de la montaña"
    ));
    private static List<String> suffixes = new ArrayList<>();

    private ItemCategory category;
    private int idCounter=3;

    public ItemGenerator(){}

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
                    return new Potion(idCounter, name, price, (int) NumUtil.generate(5, 100));
                }case OTHERS -> {
                    return new Others(idCounter, name, price);
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
            case OTHERS -> priceAux = (int) NumUtil.generate(OTHERS.getMinPrice() ,OTHERS.getMaxPrice());
        }
        priceAux = NumUtil.roundTo5(priceAux);
        return priceAux;
    }
    
    private String generateName(){
        String nameAux = null;
        int randNumSufix = (int) NumUtil.generate(0,suffixes.size()-1);

        switch (category){
            case WEAPON -> nameAux = weaponPrefixes.remove( NumUtil.generateInt(0,weaponPrefixes.size()-1));
            case ARMOR -> nameAux = armorPrefixes.remove( NumUtil.generateInt(0,armorPrefixes.size()-1));
            case BOOTS -> nameAux = bootsPrefixes.remove(NumUtil.generateInt(0,bootsPrefixes.size()-1));
            case HELMET -> nameAux = helmetPrefixes.remove(NumUtil.generateInt(0,helmetPrefixes.size()-1));
            case POTION -> nameAux = potionPrefixes.remove(NumUtil.generateInt(0,potionPrefixes.size()-1));
            case OTHERS -> nameAux = otherPrefixes.remove(NumUtil.generateInt(0,otherPrefixes.size()-1));
        }
        nameAux += (" " + suffixes.remove(randNumSufix));

        return nameAux;
    }

    // IN PROGRESS
    private ItemCategory genertateItemType(){

        int randNum = (int) NumUtil.generate(1,100);
        ItemCategory categoryAux = null;
        if(randNum <= 19){
            categoryAux = WEAPON;
            refreshEmptyList(WEAPON);
        } else if(randNum <= 38){
            categoryAux = ARMOR;
            refreshEmptyList(ARMOR);
        } else if(randNum <= 57){
            categoryAux = BOOTS;
            refreshEmptyList(BOOTS);
        } else if(randNum <= 76){
            categoryAux = HELMET;
            refreshEmptyList(HELMET);
        } else if(randNum <= 95){
            categoryAux = POTION;
            refreshEmptyList(POTION);
        } else {
            categoryAux = OTHERS;
            refreshEmptyList(OTHERS);
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

            case OTHERS -> {
                if(otherPrefixes.isEmpty()){
                    otherPrefixes = new ArrayList<>(ORIGINAL_OTHER_PREFIXES);
                }
            }
        }
        if(suffixes.isEmpty()){
            suffixes = new ArrayList<>(ORIGINAL_SUFIXES);
        }
    }
}