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
    private Pokemon mItem = new Pokemon();

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

            StringBuilder pokemonId = new StringBuilder();
            Integer intId = getArguments().getInt("id");
            if(intId < 10){
                pokemonId.append("00");
            } else{
                pokemonId.append("0");
            }
            pokemonId.append(String.valueOf(intId));
            mItem.setId(pokemonId.toString());

            mItem.setName(getArguments().getString("name"));
            mItem.setTypes(getArguments().getString("types"));
            mItem.setAttack(getArguments().getInt("attack"));
            mItem.setDefense(getArguments().getInt("defense"));
            mItem.setSpeed(getArguments().getInt("speed"));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.pokemon_detail, container, false);

        TextView pokemonDetailId = rootView.findViewById(R.id.pokemon_detail_id);
        TextView pokemonDetailName = rootView.findViewById(R.id.pokemon_detail_name);
        TextView pokemonDetailTypes = rootView.findViewById(R.id.pokemon_detail_types);
        TextView pokemonDetailStrength = rootView.findViewById(R.id.pokemon_detail_attack);
        TextView pokemonDetailSpeed = rootView.findViewById(R.id.pokemon_detail_speed);
        TextView pokemonDetailDefense = rootView.findViewById(R.id.pokemon_detail_defense);
        ImageView pokemonIcon = rootView.findViewById(R.id.pokemon_detail_icon);

        if (mItem != null) {
            pokemonDetailId.setText("#" + mItem.getId());
            pokemonDetailName.setText(mItem.getName());
            pokemonDetailTypes.setText(mItem.getTypes());
            pokemonDetailStrength.setText("Fuerza: " + mItem.getSpeed());
            pokemonDetailSpeed.setText("Velocidad: " + mItem.getAttack());
            pokemonDetailDefense.setText("Defensa: " + mItem.getDefense());
            Picasso
                    .with(rootView.getContext())
                    .load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/" + mItem.getId() + ".png")
                    .into(pokemonIcon);

        }

        return rootView;
    }

}
