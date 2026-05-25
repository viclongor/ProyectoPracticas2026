package org.sopra.rogueguild.view;
import java.io.PrintStream;
import java.util.Map;
import org.sopra.rogueguild.controller.dto.SellResponse;
import org.sopra.rogueguild.repository.model.Items.RewardBag;
import org.sopra.rogueguild.view.components.*;
import org.sopra.rogueguild.repository.model.Items.Item;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.controller.dto.BuyResponse;
import org.sopra.rogueguild.repository.model.WorldEvent;


public class ViewDisplay {
    private final BannerView banner;
    private final MessageView messages;
    private final PlayerView playerView;
    private final StockView stockView;
    private final BuyResultView buyResultView;
    private final SellResultView sellResultView;
    private final IncursionView incursionView;
    private final QuestView questView;


    public ViewDisplay() {
        this(System.out, 59);
    }

    public ViewDisplay(PrintStream out, int width) {
        this.banner = new BannerView(out);
        this.messages = new MessageView(out, width);
        this.playerView = new PlayerView(out);
        this.stockView = new StockView(out);
        this.buyResultView = new BuyResultView(messages);
        this.sellResultView = new SellResultView(messages);
        this.incursionView = new IncursionView(out);
        this.questView = new QuestView(out);
    }

    public void landingPage() { banner.landingPage(); }
    public void showMessage(String msg) { messages.showMessage(msg); }
    public void pressKeyMessage() { messages.pressKeyMessage(); }
    public void quitMessage() { messages.quitMessage(); }
    public void showPrompt(String prompt) { messages.showPrompt(prompt); }

    public void playerStatus(Player player) { playerView.playerStatus(player); }

    public void displayStock(Map<Integer, Item> itemMap, boolean isInPurchaseProcess) {
        stockView.displayStock(itemMap, isInPurchaseProcess);
    }

    public void buyResult(BuyResponse r) {
        buyResultView.show(r);
    }

    public void displayInventoryForSale(Player player) {
        playerView.displayInventoryForSale(player);
    }
    public void sellResult(SellResponse r) { sellResultView.show(r); }
    public void showWorldEvent(WorldEvent event) {
        messages.showMessage("** EVENTO DEL MUNDO **");
        messages.showMessage(event.getDescription());
    }
    public void showIncursions(){incursionView.displayAvailableIncursions();}
    public void showIncursionResult(RewardBag rewardBag){incursionView.displayIncursionResults(rewardBag);}
    public void showQuests(){questView.displayAvailableQuests();}
}
