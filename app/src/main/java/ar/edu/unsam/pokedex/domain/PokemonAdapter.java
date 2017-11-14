package ar.edu.unsam.pokedex.domain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fedepujol.pokedex.PokemonDetailActivity;
import com.example.fedepujol.pokedex.PokemonDetailFragment;
import com.example.fedepujol.pokedex.PokemonListActivity;
import com.example.fedepujol.pokedex.R;

import java.util.List;

/**
 * Created by fedepujol on 13/11/17.
 */

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private PokemonListActivity pokemonListActivity;
    private List<Result> pokemonList;
    private boolean bool;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Pokemon item = (Pokemon) view.getTag();
            if (bool) {
                Bundle arguments = new Bundle();
                arguments.putInt(PokemonDetailFragment.ARG_ITEM_ID, item.getId());
                PokemonDetailFragment fragment = new PokemonDetailFragment();
                fragment.setArguments(arguments);
                pokemonListActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.pokemon_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, PokemonDetailActivity.class);
                intent.putExtra(PokemonDetailFragment.ARG_ITEM_ID, item.getId());

                context.startActivity(intent);
            }
        }
    };


    public PokemonAdapter(PokemonListActivity pokemonListActivity, List<Result> pokemonList, boolean bool) {
        this.pokemonListActivity = pokemonListActivity;
        this.pokemonList = pokemonList;
        this.bool = bool;
    }

    @Override
    public PokemonAdapter.PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_list_content, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PokemonAdapter.PokemonViewHolder holder, int position) {
        holder.pokemonId.setText(pokemonList.get(position).getUrl());
        holder.pokemonName.setText(pokemonList.get(position).getName());

        holder.itemView.setTag(pokemonList.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {

        final TextView pokemonId;
        final TextView pokemonName;

        public PokemonViewHolder(View itemView) {
            super(itemView);
            pokemonId = itemView.findViewById(R.id.id_text);
            pokemonName = itemView.findViewById(R.id.content);
        }
    }
}
