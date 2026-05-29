package org.sopra.rogueguild.repository.model;

import java.util.List;

public class City {
    List<City> connectedCities;
    String name;

    public City(List<City> connectedCities, String name) {
        this.connectedCities = connectedCities;
        this.name = name;
    }

    public List<City> getConnectedCities() {return connectedCities;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public boolean addConnectiontoCity(City city){
        return connectedCities.add(city);
    }
}
