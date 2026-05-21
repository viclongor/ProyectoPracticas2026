package org.sopra.rogueguild.controller.dto;

import org.sopra.rogueguild.repository.model.Item;

public class SellResponse {
    public enum Status { SUCCESS, EMPTY_INVENTORY, NOT_FOUND }

    private final Status status;
    private final Item item;
    private final int goldReceived;

    private SellResponse(Status status, Item item, int goldReceived) {
        this.status = status;
        this.item = item;
        this.goldReceived = goldReceived;
    }

    public static SellResponse success(Item item, int goldReceived) {
        return new SellResponse(Status.SUCCESS, item, goldReceived);
    }
    public static SellResponse emptyInventory() {
        return new SellResponse(Status.EMPTY_INVENTORY, null, 0);
    }
    public static SellResponse notFound() {
        return new SellResponse(Status.NOT_FOUND, null, 0);
    }

    public Status getStatus()    { return status; }
    public Item getItem()        { return item; }
    public int getGoldReceived() { return goldReceived; }
}