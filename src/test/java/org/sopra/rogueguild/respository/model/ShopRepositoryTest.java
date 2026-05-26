package org.sopra.rogueguild.respository.model;

import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.ShopRepository;

import static org.junit.jupiter.api.Assertions.*;

class ShopRepositoryTest {
    ShopRepository shop = new ShopRepository();

    @Test
    void playerAddItem() {
        assertEquals(2,shop.getAllStock().size());
    }
}