package com.example.fedepujol.pokedex;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fedepujol.pokedex.dummy.DummyContent;

import ar.edu.unsam.pokedex.domain.Pokemon;
import ar.edu.unsam.pokedex.domain.PokemonAdapter;

/**
 * A fragment representing a single Pokemon detail screen.
 * This fragment is either contained in a {@link PokemonListActivity}
 * in two-pane mode (on tablets) or a {@link PokemonDetailActivity}
 * on handsets.
 */
public class PokemonDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Pokemon mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PokemonDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pokemon_detail, container, false);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.pokemon_detail)).setText(mItem.getName());
        }

        return rootView;
    }
}
