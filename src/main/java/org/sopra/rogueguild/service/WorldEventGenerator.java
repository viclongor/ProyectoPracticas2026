package org.sopra.rogueguild.service;

import org.sopra.rogueguild.repository.model.Items.ItemCategory;
import org.sopra.rogueguild.repository.model.WorldEvent;
import util.NumUtil;

import java.util.List;

public class WorldEventGenerator {

    private static final List<ItemCategory> ELIGIBLE_CATEGORIES = List.of(
            ItemCategory.WEAPON,
            ItemCategory.ARMOR,
            ItemCategory.BOOTS,
            ItemCategory.HELMET,
            ItemCategory.POTION
    );

    private static final double[][] VARIATIONS = {
            {1.10,  10},
            {1.20,  20},
            {1.30,  30},
            {1.50,  50},
            {0.90, -10},
            {0.80, -20},
            {0.70, -30},
            {0.50, -50},
    };

    private static final List<String> GLOBAL_UP = List.of(
            "Una plaga de dragones ha disparado el precio de toda la mercancía un %d%%.",
            "La guerra en las fronteras ha encarecido todos los artículos un %d%%.",
            "Una escasez de materiales ha subido los precios generales un %d%%.",
            "El gremio de mercaderes ha acordado una subida global del %d%%."
    );
    private static final List<String> GLOBAL_DOWN = List.of(
            "Una sobreproducción ha reducido el precio de toda la mercancía un %d%%.",
            "El mercado negro ha inundado la ciudad y los precios han caído un %d%%.",
            "Una temporada de abundancia ha abaratado todos los artículos un %d%%.",
            "El rey ha decretado precios máximos: toda la mercancía baja un %d%%."
    );
    private static final List<String> CATEGORY_UP = List.of(
            "Los %s han aumentado su precio un %d%%.",
            "La alta demanda de %s ha disparado su valor un %d%%.",
            "Un impuesto especial encarece los %s un %d%%.",
            "Escasean los materiales: los %s cuestan un %d%% más."
    );
    private static final List<String> CATEGORY_DOWN = List.of(
            "Los %s tienen un descuento del %d%%.",
            "Excedente de stock: los %s bajan un %d%% de precio.",
            "El mercado está saturado de %s; su precio cae un %d%%.",
            "El artesano local ofrece %s con un %d%% de rebaja."
    );

    private static String categoryLabel(ItemCategory cat) {
        return switch (cat) {
            case WEAPON -> "armas";
            case ARMOR  -> "armaduras";
            case BOOTS  -> "botas";
            case HELMET -> "yelmos";
            case POTION -> "pociones";
            default     -> "artículos";
        };
    }

    public WorldEvent generate() {
        boolean isGlobal = NumUtil.generateInt(0, 2) == 0;

        ItemCategory category = null;
        if (!isGlobal) {
            int idx = Math.min(NumUtil.generateInt(0, ELIGIBLE_CATEGORIES.size()),
                    ELIGIBLE_CATEGORIES.size() - 1);
            category = ELIGIBLE_CATEGORIES.get(idx);
        }

        int varIdx = Math.min(NumUtil.generateInt(0, VARIATIONS.length),
                VARIATIONS.length - 1);
        double factor   = VARIATIONS[varIdx][0];
        int    pct      = (int) Math.abs(VARIATIONS[varIdx][1]);
        boolean isUp    = VARIATIONS[varIdx][1] > 0;

        String description = buildDescription(isGlobal, category, isUp, pct);
        return new WorldEvent(category, factor, description);
    }

    private String buildDescription(boolean isGlobal, ItemCategory category,
                                    boolean isUp, int pct) {
        if (isGlobal) {
            List<String> t = isUp ? GLOBAL_UP : GLOBAL_DOWN;
            int idx = Math.min(NumUtil.generateInt(0, t.size()), t.size() - 1);
            return String.format(t.get(idx), pct);
        } else {
            List<String> t = isUp ? CATEGORY_UP : CATEGORY_DOWN;
            int idx = Math.min(NumUtil.generateInt(0, t.size()), t.size() - 1);
            return String.format(t.get(idx), categoryLabel(category), pct);
        }
    }
}