package ar.edu.unsam.pokedex.domain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fedepujol.pokedex.PokemonDetailActivity;
import com.example.fedepujol.pokedex.PokemonDetailFragment;
import com.example.fedepujol.pokedex.PokemonListActivity;
import com.example.fedepujol.pokedex.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by fedepujol on 13/11/17.
 */

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private boolean bool;
    private Context context;
    private List<Result> pokemonList;
    private PokemonListActivity pokemonListActivity;
    public final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Result item = (Result) view.getTag();
            if (bool) {
                Bundle arguments = new Bundle();
                arguments.putString(PokemonDetailFragment.ARG_ITEM_ID, item.getName());
                PokemonDetailFragment fragment = new PokemonDetailFragment();
                fragment.setArguments(arguments);
                pokemonListActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.pokemon_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, PokemonDetailActivity.class);
                intent.putExtra(PokemonDetailFragment.ARG_ITEM_ID, item.getName());

                context.startActivity(intent);
            }
        }
    };


    public PokemonAdapter(Context context, List<Result> pokemonList, boolean bool, PokemonListActivity pokemonListActivity) {
        this.context = context;
        this.pokemonList = pokemonList;
        this.bool = bool;
        this.pokemonListActivity = pokemonListActivity;
        initPokemonMap();
    }

    private void initPokemonMap() {
        RepoPokemon.getInstance().agregarAMap(pokemonList);
    }

    @Override
    public PokemonAdapter.PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_list_content, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PokemonAdapter.PokemonViewHolder holder, int position) {
        final StringBuilder pokemonListId = new StringBuilder();
        String IMAGE_BASE_URL = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/";

        Picasso
                .with(context)
                .load(IMAGE_BASE_URL + pokemonList.get(position).getId() + ".png")
                .into(holder.pokemonImage);

        holder.pokemonName.setText(pokemonList.get(position).getName());

        pokemonListId.append("#").append(pokemonList.get(position).getId());
        holder.pokemonId.setText(pokemonListId);

        holder.itemView.setTag(pokemonList.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {

        final ImageView pokemonImage;
        final TextView pokemonName;
        final TextView pokemonId;

        public PokemonViewHolder(View itemView) {
            super(itemView);

            pokemonImage = itemView.findViewById(R.id.pokemon_list_icon);
            pokemonName = itemView.findViewById(R.id.pokemon_list_name);
            pokemonId = itemView.findViewById(R.id.pokemon_list_id);
        }
    }
}
