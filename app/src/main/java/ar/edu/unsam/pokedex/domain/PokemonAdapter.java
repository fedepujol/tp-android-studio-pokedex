package ar.edu.unsam.pokedex.domain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fedepujol.pokedex.PokemonDetailActivity;
import com.example.fedepujol.pokedex.PokemonDetailFragment;
import com.example.fedepujol.pokedex.PokemonListActivity;
import com.example.fedepujol.pokedex.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fedepujol on 13/11/17.
 */

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private boolean bool;
    private PokemonListActivity pokemonListActivity;
    private List<Result> pokemonList;
    public static Map<String, Result> pokemonListMap = new HashMap<String, Result>();

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
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


    public PokemonAdapter(PokemonListActivity pokemonListActivity, List<Result> pokemonList, boolean bool) {
        this.pokemonListActivity = pokemonListActivity;
        this.pokemonList = pokemonList;
        this.bool = bool;
        initPokemonMap();
    }

    private void initPokemonMap(){
        int i = pokemonList.size();

        for (i -= 1; i >= 0; i--) {
            pokemonListMap.put(pokemonList.get(i).getName(), pokemonList.get(i));
        }
    }

    @Override
    public PokemonAdapter.PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_list_content, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PokemonAdapter.PokemonViewHolder holder, int position) {
        /*  Como en la lista de Result, viene una url con el ID del pokemon
        *   uso el ID y para traer la imagen desde la url correcta
        */
        String url = pokemonList.get(position).getUrl();
        String IMAGE_BASE_URL = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/";
        String[] parts = url.split("/");
        String pokemonId = parts[6];

        if(pokemonId.length() < 2){
            IMAGE_BASE_URL = IMAGE_BASE_URL + "00" + pokemonId + ".png";
        } else {
            IMAGE_BASE_URL = IMAGE_BASE_URL + "0" + pokemonId + ".png";
        }

        new DownloadImage(holder.pokemonImage).execute(IMAGE_BASE_URL);

        //holder.pokemonImage.setImageURI(Uri.parse(pokemonList.get(position).getUrl()));
        holder.pokemonName.setText(pokemonList.get(position).getName());

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

        public PokemonViewHolder(View itemView) {
            super(itemView);

            pokemonImage = itemView.findViewById(R.id.pokemonIcon);
            pokemonName = itemView.findViewById(R.id.name);
        }
    }
}
