package org.sopra.rogueguild.repository.model;

import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.model.Items.Item;
import org.sopra.rogueguild.repository.model.Items.Weapon;

import static org.junit.jupiter.api.Assertions.*;
import static org.sopra.rogueguild.repository.model.Items.ItemCategory.WEAPON;

class PlayerTest {
    Weapon item1 = new Weapon(1,"botas",100,25);
    Player player1 = new Player("Jugador1",100);



}