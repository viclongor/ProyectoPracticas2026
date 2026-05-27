package org.sopra.rogueguild.view.components;

import org.sopra.rogueguild.controller.dto.SellResponse;

public class SellResultView {
    private final MessageView messages;

    public SellResultView(MessageView messages) {
        this.messages = messages;
    }

    public void show(SellResponse r) {
        switch (r.getStatus()) {
            case SUCCESS ->
                    messages.showMessage("[+] Has vendido " + r.getItem().getName()
                            + " por " + r.getGoldReceived() + " monedas.");
            case EMPTY_INVENTORY ->
                    messages.showMessage("[!] Tu inventario está vacío. No tienes nada que vender.");
            case NOT_FOUND ->
                    messages.showMessage("[!] Ese objeto no existe en tu inventario.");
            case ITEM_EQUIPPED ->
                    messages.showMessage("[!] Ese objeto está equipado. Desequípalo antes de venderlo.");
        }
    }
}