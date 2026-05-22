package org.sopra.rogueguild.controller;

import java.util.Scanner;
import org.sopra.rogueguild.controller.dto.SellResponse;
import java.util.List;
import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.Incursions.ConquestIncursion;
import org.sopra.rogueguild.repository.model.Incursions.PillageIncursion;
import org.sopra.rogueguild.repository.model.Incursions.SmallIncursion;
import org.sopra.rogueguild.repository.model.Items.Item;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.repository.model.Items.RewardBag;
import org.sopra.rogueguild.view.ViewDisplay;
import org.sopra.rogueguild.controller.dto.BuyResponse;
import org.sopra.rogueguild.repository.model.WorldEvent;
import org.sopra.rogueguild.service.WorldEventGenerator;
import util.Input;

public class MenuController {
    private final Player player;
    private final ViewDisplay view;
    private final ShopRepository repository;
    private final Scanner sc;
    private final WorldEventGenerator eventGenerator;

    public MenuController(Player p, ViewDisplay v, ShopRepository r) {
        this.player = p;
        this.view = v;
        this.repository = r;
        this.sc = new Scanner(System.in);
        this.eventGenerator = new WorldEventGenerator();
    }

    public void start() {
        WorldEvent event = eventGenerator.generate();
        repository.applyWorldEvent(event);

        int opt;
        do {
            view.landingPage();
            view.showWorldEvent(event);
            view.playerStatus(player);
            opt = Input.getInt();
            switch (opt) {
                case 1:
                    view.displayStock(repository.getAllStock(), false);
                    break;
                case 2:
                    view.displayStock(repository.getAllStock(), true);
                    int itemId = Input.getInt();
                    BuyResponse buyResponse = buyProcess(itemId);
                    view.buyResult(buyResponse);
                    break;
                case 3:
                    SellResponse sellResponse = sellProcess();
                    view.sellResult(sellResponse);
                    break;
                case 4:
                    view.showIncursions();
                    int incursionId = Input.getInt();
                    selectIncursion(incursionId);
                    break;
                case 0:
                    view.quitMessage();
                    break;
                default:
                    System.out.println("Tienes que escribir un numero valido");
                    break;
            }
            view.pressKeyMessage();
            sc.nextLine();
        } while (opt != 0);
    }
    private void selectIncursion(int incursionId){
        RewardBag rewardBag = null;
        switch (incursionId){
            case 1->{
                ConquestIncursion conquestIncursion = new ConquestIncursion("El Santuario olvidado", "Ardua incursion que seguro que aporta objetos caros");
                rewardBag = conquestIncursion.completeQuest();
                view.showIncursionResult(rewardBag);

            }
            case 2->{
                PillageIncursion pillageIncursion = new PillageIncursion("La ciudad prohibida", "Ardua incursion que seguro que aporta grandes riquezas");
                rewardBag = pillageIncursion.completeQuest();
                view.showIncursionResult(rewardBag);

            }
            case 3->{
                SmallIncursion smallIncursion = new SmallIncursion("La campamento de bandidos", "incursion que seguro que aporta tanto objetos baratos como un poco de oro");
                rewardBag = smallIncursion.completeQuest();
                view.showIncursionResult(rewardBag);
            }
            default -> {
                view.showMessage("Esa no es una incursion Valida");
            }
        }
        if(rewardBag != null){
            player.receiveGold(rewardBag.getGold());
            if(rewardBag.getItem() != null){
                player.addItem(rewardBag.getItem());
            }
        }
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
        repository.addItem(item);
        return SellResponse.success(item, goldReceived);
    }

    private int calcSellPrice(int basePrice) {
        int raw = (int) Math.round(basePrice * 0.8);
        return (int) (Math.round(raw / 5.0) * 5);
    }
}