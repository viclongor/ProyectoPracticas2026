package org.sopra.rogueguild.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.model.WorldEvent;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WordEventGeneratorTest {
    WorldEventGenerator generator;

    @BeforeEach
    void setUp(){
        generator = new WorldEventGenerator();
    }

    @Test
    void worldEventHasDescription(){
        WorldEvent event = generator.generate();
        assertNotEquals(null, event.getDescription());
    }

    @Test
    void worldEventHasModifier(){
        WorldEvent event = generator.generate();
        assertNotEquals(null, event.getFactor());
    }
}
