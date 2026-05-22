package org.sopra.rogueguild.repository.model;

import org.sopra.rogueguild.repository.model.Items.ItemCategory;

public class WorldEvent {

    private final ItemCategory affectedCategory; // null → global
    private final double factor;
    private final String description;

    public WorldEvent(ItemCategory affectedCategory, double factor, String description) {
        this.affectedCategory = affectedCategory;
        this.factor = factor;
        this.description = description;
    }

    public ItemCategory getAffectedCategory() { return affectedCategory; }
    public double getFactor()                 { return factor; }
    public String getDescription()            { return description; }
    public boolean isGlobal()                 { return affectedCategory == null; }
}