package org.sopra.rogueguild.respository.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.model.City;
import org.sopra.rogueguild.repository.model.Player;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityTravelTest {

    private City aldea;
    private City bosque;
    private City ciudad;
    private City ruinas;
    private City puerto;
    private City islaAislada;
    private Player player;

    @BeforeEach
    void setUp() {
        aldea = new City("Aldea del Inicio");
        bosque = new City("Bosque Oscuro");
        ciudad = new City("Ciudad Amurallada");
        ruinas = new City("Ruinas Antiguas");
        puerto = new City("Puerto del Norte");
        islaAislada = new City("Isla Aislada");

        aldea.addConnection(bosque);
        aldea.addConnection(ciudad);
        bosque.addConnection(ruinas);
        ciudad.addConnection(puerto);

        player = new Player("Test", 500);
        player.setCurrentCity(aldea);
    }

    // City
    @Test
    void cityTieneNombre() {
        assertEquals("Aldea del Inicio", aldea.getName());
    }

    @Test
    void cityListaConexionesNoNula() {
        assertNotNull(aldea.getConnectedCities());
    }

    @Test
    void addConnectionEsBidireccional() {
        assertTrue(aldea.getConnectedCities().contains(bosque));
        assertTrue(bosque.getConnectedCities().contains(aldea));
    }

    @Test
    void addConnectionNoDuplica() {
        aldea.addConnection(bosque);
        long count = aldea.getConnectedCities().stream()
                .filter(c -> c == bosque).count();
        assertEquals(1, count);
    }

    @Test
    void ciudadEnRamaTieneUnaConexion() {
        assertEquals(1, ruinas.getConnectedCities().size());
    }

    // Player.currentCity
    @Test
    void playerTieneCurrentCity() {
        assertNotNull(player.getCurrentCity());
        assertEquals("Aldea del Inicio", player.getCurrentCity().getName());
    }

    // travelTo
    @Test
    void travelToActualizaCurrentCity() {
        player.travelTo(bosque);
        assertEquals(bosque, player.getCurrentCity());
    }

    @Test
    void travelToDevuelveRutaValida() {
        List<City> path = player.travelTo(bosque);
        assertNotNull(path);
        assertEquals(aldea, path.get(0));
        assertEquals(bosque, path.get(path.size() - 1));
    }

    @Test
    void travelToEncuentraRutaIndirecta() {
        List<City> path = player.travelTo(ruinas);
        assertNotNull(path);
        assertEquals(ruinas, player.getCurrentCity());
    }

    @Test
    void travelToRutaSigueConexiones() {
        List<City> path = player.travelTo(ruinas);
        assertNotNull(path);
        for (int i = 0; i < path.size() - 1; i++) {
            assertTrue(path.get(i).getConnectedCities().contains(path.get(i + 1)),
                    "Salto inválido: " + path.get(i).getName() + " -> " + path.get(i + 1).getName());
        }
    }

    @Test
    void travelToDevuelveNullSiNoHayRuta() {
        List<City> path = player.travelTo(islaAislada);
        assertNull(path);
    }

    @Test
    void travelToNoActualizaCitysSiNoHayRuta() {
        player.travelTo(islaAislada);
        assertEquals(aldea, player.getCurrentCity());
    }

    @Test
    void travelToMismaciudadDevuelveRutaDeUnElemento() {
        List<City> path = player.travelTo(aldea);
        assertNotNull(path);
        assertEquals(1, path.size());
        assertEquals(aldea, path.get(0));
    }

    @Test
    void travelToNoPuedeSaltarseCiudades() {
        List<City> path = player.travelTo(ruinas);
        assertNotNull(path);
        assertTrue(path.contains(bosque), "La ruta debería pasar por Bosque Oscuro");
    }
}