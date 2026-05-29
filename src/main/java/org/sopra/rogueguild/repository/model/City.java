package org.sopra.rogueguild.repository.model;

import java.util.ArrayList;
import java.util.List;

public class City {
    private final String name;
    private final List<City> connectedCities;

    public City(String name) {
        this.name = name;
        this.connectedCities = new ArrayList<>();
    }

    public String getName() { return name; }
    public List<City> getConnectedCities() { return connectedCities; }

    public void addConnection(City city) {
        if (!connectedCities.contains(city)) {
            connectedCities.add(city);
            city.connectedCities.add(this);
        }
    }
}