package com.example.fedepujol.pokedex;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ar.edu.unsam.pokedex.domain.Pokemon;
import ar.edu.unsam.pokedex.domain.RepoPokemon;
import ar.edu.unsam.pokedex.domain.Result;

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
    //private Result mItem;
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
            //mItem = RepoPokemon.getInstance().findPokemonByName(getArguments().getString(ARG_ITEM_ID));
            mItem = RepoPokemon.getInstance().pokemonFromResultName();

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                //appBarLayout.setTitle(mItem.getName());
                appBarLayout.setTitle("Pokemon Detail");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.pokemon_detail, container, false);


        TextView pokemonDetailName = rootView.findViewById(R.id.pokemon_detail_name);
        TextView pokemonDetailStrenght = rootView.findViewById(R.id.pokemon_detail_strenght);
        TextView pokemonDetailId = rootView.findViewById(R.id.pokemon_detail_id);
        ImageView pokemonIcon = rootView.findViewById(R.id.pokemon_detail_icon);

        if (mItem != null) {
            pokemonDetailId.setText("#" + mItem.getId());
            pokemonDetailName.setText(mItem.getName());
            pokemonDetailStrenght.setText(mItem.getWeight());
            Picasso
                    .with(rootView.getContext())
                    .load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/" + mItem.getId() + ".png")
                    .into(pokemonIcon);
        }

        return rootView;
    }

}
