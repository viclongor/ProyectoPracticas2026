package org.sopra.rogueguild;

import org.sopra.rogueguild.controller.MenuController;
import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.City;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.view.ViewDisplay;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        ShopRepository repository = new ShopRepository();
        ViewDisplay view = new ViewDisplay();

        Player player = new Player( "Iñigo Montolla", 500);
        City aldea = new City("Aldea del Inicio");
        City bosque = new City("Bosque Oscuro");
        City ciudad = new City("Ciudad Amurallada");
        City ruinas = new City("Ruinas Antiguas");
        City puerto = new City("Puerto del Norte");

        aldea.addConnection(bosque);
        aldea.addConnection(ciudad);
        bosque.addConnection(ruinas);
        ciudad.addConnection(puerto);
        player.setCurrentCity(aldea);

        List<City> cities = new ArrayList<>(List.of(aldea, bosque, ciudad, ruinas, puerto));
        MenuController controller = new MenuController(player, view, repository,cities);
        controller.start();
    }
}