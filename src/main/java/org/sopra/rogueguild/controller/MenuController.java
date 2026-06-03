package org.sopra.rogueguild.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import org.sopra.rogueguild.controller.dto.QuestResponse;
import org.sopra.rogueguild.controller.dto.SellResponse;
import java.util.List;
import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.City;
import org.sopra.rogueguild.repository.model.Incursions.ConquestIncursion;
import org.sopra.rogueguild.repository.model.Incursions.PillageIncursion;
import org.sopra.rogueguild.repository.model.Incursions.SmallIncursion;
import org.sopra.rogueguild.repository.model.Items.Item;
import org.sopra.rogueguild.repository.model.Items.ItemCategory;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.repository.model.Items.RewardBag;
import org.sopra.rogueguild.repository.model.Quest;
import org.sopra.rogueguild.service.ItemGenerator;
import org.sopra.rogueguild.view.ViewDisplay;
import org.sopra.rogueguild.controller.dto.BuyResponse;
import org.sopra.rogueguild.repository.model.WorldEvent;
import org.sopra.rogueguild.service.WorldEventGenerator;
import util.Input;
import org.sopra.rogueguild.repository.model.Items.Potion;
import util.NumUtil;

public class MenuController {
    private final Player player;
    private final ViewDisplay view;
    private final ShopRepository repository;
    private final Scanner sc;
    private final WorldEventGenerator eventGenerator;
    private final ItemGenerator itemGenerator;
    private final List<Quest> quests;
    private final List<City> cities;
    private WorldEvent event;


    public MenuController(Player p, ViewDisplay v, ShopRepository r,List<City> cities) {
        this.player = p;
        this.view = v;
        this.repository = r;
        this.sc = new Scanner(System.in);
        this.eventGenerator = new WorldEventGenerator();
        this.itemGenerator = new ItemGenerator();
        this.quests = new ArrayList<>(List.of(
                new Quest(
                        "Danza de muerte",
                        150,
                        Map.of(ItemCategory.WEAPON, 2)
                ),
                new Quest(
                        "Caballero del Fénix",
                        300,
                        Map.of(
                                ItemCategory.WEAPON, 1,
                                ItemCategory.HELMET, 1,
                                ItemCategory.ARMOR, 1,
                                ItemCategory.BOOTS, 1
                        )
                ),
                new Quest(
                        "Daño maximo",
                        300,
                        120,
                        0
                ),
                new Quest(
                        "Equipamiento balanceado",
                        300,
                        100,
                        50
                )
        ));
        this.cities = cities;
        event = null;
    }

    public void start() {
        event = eventGenerator.generate();
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
                case 5:
                    questProcess();
                    break;
                case 6:
                    equipProcess();
                    break;
                case 7:
                    usePotionProcess();
                    break;
                case 8:
                    travelProcess();
                    event = eventGenerator.generate();
                    repository.applyWorldEvent(event);
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
                ConquestIncursion conquestIncursion = new ConquestIncursion("El Santuario olvidado", "Ardua incursion que seguro que aporta objetos caros",itemGenerator);
                rewardBag = conquestIncursion.completeQuest();
                view.showIncursionResult(rewardBag);

            }
            case 2->{
                PillageIncursion pillageIncursion = new PillageIncursion("La ciudad prohibida", "Ardua incursion que seguro que aporta grandes riquezas",itemGenerator);
                rewardBag = pillageIncursion.completeQuest();
                view.showIncursionResult(rewardBag);

            }
            case 3->{
                SmallIncursion smallIncursion = new SmallIncursion("La campamento de bandidos", "incursion que seguro que aporta tanto objetos baratos como un poco de oro",itemGenerator);
                rewardBag = smallIncursion.completeQuest();
                view.showIncursionResult(rewardBag);
            }
            default -> {
                view.showMessage("Esa no es una incursion Valida");
                return;
            }
        }
        if(rewardBag != null){
            player.receiveGold(rewardBag.getGold());
            if(rewardBag.getItem() != null){
                player.addItem(rewardBag.getItem());
            }
            repository.refreshShop(itemGenerator);
        }
    }
     BuyResponse buyProcess(int id) {
        Item item = repository.getItem(id);

        if (item == null) {
            return BuyResponse.notFound(id);
        }

        if (player.getGold() < item.getPrice()) {
            return BuyResponse.notEnoughGold(item, player.getGold());
        }

        item.setOriginalId(id);

        player.spendGold(item.getPrice());
        repository.removeItem(id);

        player.addItem(item);

        return BuyResponse.success(item);
    }

     SellResponse sellProcess() {
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
        if (player.isEquipped(item)) {
            return SellResponse.itemEquipped();
        }
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

    private void questProcess() {
        List<Quest> pending = quests.stream()
                .filter(q -> !q.isCompleted())
                .collect(java.util.stream.Collectors.toList());

        if (pending.isEmpty()) {
            view.showQuestResult(QuestResponse.noQuests());
            return;
        }

        view.showQuests(pending);
        int choice = Input.getInt();

        if (choice < 1 || choice > pending.size()) {
            view.showQuestResult(QuestResponse.notFound());
            return;
        }

        Quest quest = pending.get(choice - 1);

        if (quest.complete(player)) {
            view.showQuestResult(QuestResponse.success(quest));
        } else if(quest.getRequiredItems() == null){
            int misingAttack = quest.getRequiredAttack() - player.getAttack();
            int misingArmor = quest.getRequiredArmor() - player.getArmor();

            view.showQuestResult(QuestResponse.requirementsNotMet(quest, misingAttack, misingArmor));

        } else{
            Map<ItemCategory, Integer> missing = new java.util.HashMap<>();
            for (Map.Entry<ItemCategory, Integer> entry : quest.getRequiredItems().entrySet()) {
                ItemCategory category = entry.getKey();
                int needed = entry.getValue();
                long has = player.getInventory().stream()
                        .filter(item -> item.getCategory() == category)
                        .count();
                int lack = needed - (int) has;
                if (lack > 0) missing.put(category, lack);
            }
            view.showQuestResult(QuestResponse.requirementsNotMet(quest, missing));
        }
    }

    private void equipProcess() {
        if (player.getInventory().isEmpty()) {
            view.showMessage("No tienes objetos en el inventario para equipar.");
            return;
        }
        view.showEquipMenu(player);
        int choice = Input.getInt();
        if (choice < 1 || choice > player.getInventory().size()) {
            view.showMessage("Opción no válida.");
            return;
        }
        Item item = player.getInventory().get(choice - 1);
        boolean equipped = player.equipItem(item);
        if (equipped) {
            view.showMessage("[+] Has equipado: " + item.getName());
            view.showEquipped(player);
        } else {
            view.showMessage("[!] No puedes equipar ese tipo de objeto.");
        }
    }

    private void travelProcess() {
        view.showMap(player);
        List<City> destinations = player.getCurrentCity().getConnectedCities();
        view.showCityList(destinations);
        int choice = Input.getInt();
        if (choice < 1 || choice > destinations.size()) {
            view.showMessage("Ciudad no válida.");
            return;
        }
        City destination = destinations.get(choice - 1);
        List<City> path = player.travelTo(destination);

        view.showTravelResult(path);


        if(encounterChance()){
            int damage = NumUtil.generateDamage(player.getArmor());
            player.takeDamage(damage);
            view.showMessage("Te han atacado por el camino has perdido: "+damage+" puntos de vida");
            if(checkDeath()){
                view.showMessage("GAME OVER");
                System.exit(0);
            };
        }

        if (path != null) {
            view.showMessage("Has llegado a: " + player.getCurrentCity().getName());
        }
    }
    boolean encounterChance(){
        int encounterChance = NumUtil.generateInt(1,100);
        return encounterChance <= 20;
    }
    boolean checkDeath(){
        return player.getHitPoints() <=0;
    }
    public WorldEvent getCurrentWorldEvent(){
        return event;
    }

    private void usePotionProcess() {

        List<Item> potions = player.getInventory().stream()
                .filter(item -> item instanceof Potion)
                .toList();

        if (potions.isEmpty()) {
            view.showMessage("No tienes pociones.");
            return;
        }

        for (int i = 0; i < potions.size(); i++) {
            Potion potion = (Potion) potions.get(i);

            view.showMessage(
                    (i + 1) + ". " +
                            potion.getName() +
                            " (+" + potion.getHealPoint() + " HP)"
            );
        }

        int choice = Input.getInt();

        if (choice < 1 || choice > potions.size()) {
            view.showMessage("Opción no válida.");
            return;
        }

        Potion potion = (Potion) potions.get(choice - 1);

        player.usePotion(potion);

        view.showMessage("Has usado " + potion.getName());
    }
}