package org.sopra.rogueguild.view.components;

import org.sopra.rogueguild.repository.model.City;
import org.sopra.rogueguild.repository.model.Player;
import java.io.PrintStream;
import java.util.List;

public class TravelView {
    private final PrintStream out;

    public TravelView(PrintStream out) { this.out = out; }

    public void showMap(Player player) {
        String current = player.getCurrentCity().getName();
        out.println("  ____________________________________________________________________");
        out.println(" /  ___________________________________________________________________ \\");
        out.println("|| /                                                                   \\ ||");
        out.println("|| |               MAPA DEL MUNDO                                      | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |                                                                   | ||");
        out.println("|| |  [Aldea del Inicio] ------ [Bosque Oscuro] -- [Ruinas Antiguas]   | ||");
        out.println("|| |          |                                                        | ||");
        out.println("|| |          |                                                        | ||");
        out.println("|| |  [Ciudad Amurallada] -- [Puerto del Norte]                        | ||");
        out.println("|| |                                                                   | ||");
        out.println("|| |  Ubicacion actual: "+ current+"                                | ||" );
        out.println("|| |                                                                   | ||");
        out.println("|| \\_______________________________________________________________ ___/ ||");
        out.println(" \\_______________________________________________________________________/");
    }

    public void showCityList(List<City> cities) {
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
        out.println("|| |           CIUDADES DISPONIBLES                | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |                                               | ||");
        for (int i = 0; i < cities.size(); i++) {
            out.printf("|| |  [%d] %-38s    | ||%n", i + 1, cities.get(i).getName());
        }
        out.println("|| |                                               | ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
    }

    public void showTravelResult(List<City> path) {
        if (path == null) {
            out.println("|| No existe ruta hacia esa ciudad.");
            return;
        }
        out.print("|| Viajando: ");
        for (int i = 0; i < path.size(); i++) {
            out.print(path.get(i).getName());
            if (i < path.size() - 1) out.print(" -> ");
        }
        out.println();
    }
}