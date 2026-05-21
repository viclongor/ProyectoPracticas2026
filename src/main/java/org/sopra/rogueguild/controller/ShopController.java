package org.sopra.rogueguild.controller;

import java.util.Scanner;
import org.sopra.rogueguild.controller.dto.SellResponse;
import java.util.List;
import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.view.ViewDisplay;
import org.sopra.rogueguild.controller.dto.BuyResponse;

public class ShopController {
    private final Player player;
    private final ViewDisplay view;
    private final ShopRepository repository;
    private final Scanner sc;

    public ShopController(Player p, ViewDisplay v, ShopRepository r) {
        this.player = p;
        this.view = v;
        this.repository = r;
        this.sc = new Scanner(System.in);
    }

    public void start() {
        int opt;
        do {
            view.landingPage();
            view.playerStatus(player);
            opt = Integer.parseInt(sc.nextLine());
            switch (opt) {
                case 1:
                    view.displayStock(repository.getAllStock(), false);
                    break;
                case 2:
                    view.displayStock(repository.getAllStock(), true);
                    int itemId = Integer.parseInt(sc.nextLine());
                    BuyResponse buyResponse = buyProcess(itemId);
                    view.buyResult(buyResponse);
                    break;
                case 3:
                    SellResponse sellResponse = sellProcess();
                    view.sellResult(sellResponse);
                    break;
                case 4:
                    // TODO Logic to ...
                    break;
                case 0:
                    view.quitMessage();
                    break;
            }
            view.pressKeyMessage();
            sc.nextLine();
        } while (opt != 0);
    }

    private BuyResponse buyProcess(int id) {
        Item item = repository.getItem(id);
        if (item == null) {
            return BuyResponse.notFound(id);
        }
        if (player.getGold() < item.getPrice()) {
            return BuyResponse.notEnoughGold(item, player.getGold());
        }
        item.setOriginalId(id);
        player.buy(item);
        repository.removeItem(id);
        return BuyResponse.success(item);
    }

    private SellResponse sellProcess() {
        List<Item> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            return SellResponse.emptyInventory();
        }
        view.displayInventoryForSale(player);
        int choice;
        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return SellResponse.notFound();
        }
        if (choice < 1 || choice > inventory.size()) {
            return SellResponse.notFound();
        }
        Item item = inventory.get(choice - 1);
        int goldReceived = calcSellPrice(item.getBasePrice());
        player.removeItem(item);
        player.receiveGold(goldReceived);
        repository.addItem(item.getOriginalId(), item);
        return SellResponse.success(item, goldReceived);
    }

    private int calcSellPrice(int basePrice) {
        int raw = (int) Math.round(basePrice * 0.8);
        return (int) (Math.round(raw / 5.0) * 5);
    }
}