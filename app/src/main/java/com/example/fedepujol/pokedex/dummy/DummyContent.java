package com.example.fedepujol.pokedex.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.unsam.pokedex.domain.Pokemon;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Pokemon> ITEMS = new ArrayList<Pokemon>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Pokemon> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(Pokemon item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.getId()), item);
    }

    private static Pokemon createDummyItem(int position) {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(position);
        return pokemon;
    }
}
