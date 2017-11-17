package com.example.fedepujol.pokedex;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ar.edu.unsam.pokedex.domain.Pokemon;
import ar.edu.unsam.pokedex.domain.PokemonJson;
import ar.edu.unsam.pokedex.domain.PokemonService;
import ar.edu.unsam.pokedex.domain.RepoPokemon;
import ar.edu.unsam.pokedex.domain.Result;
import ar.edu.unsam.pokedex.domain.Stat;
import ar.edu.unsam.pokedex.domain.Stat_;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * An activity representing a single Pokemon detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link PokemonListActivity}.
 */
public class PokemonDetailActivity extends AppCompatActivity {

    private String BASE_URL = "http://pokeapi.co/api/v2/pokemon/";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        /*
        * Creo el Retrofit para la llamada al servicio Rest
        * que tiene los datos del Pokemon
        */

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PokemonService pokemonService = retrofit.create(PokemonService.class);
        /*
        * Como necesito el ID del Pokemon para traer los datos del Server
        * Utilizo el ID del Result (primera llamada en PokemonListActivity)
        * Mediante el nombre del Pokemon que se guarda al seleccionar en
        * el mOnClickListener en PokemonAdapter en el mapa del fragment.
        * */
        Result result = RepoPokemon.getInstance().findPokemonByName(getIntent().getStringExtra(PokemonDetailFragment.ARG_ITEM_ID));

        Call<PokemonJson> call = pokemonService.getPokemon(result.getId());

        call.enqueue(new Callback<PokemonJson>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Response<PokemonJson> response, Retrofit retrofit) {
                PokemonJson pokemon = response.body();
                final StringBuilder typesBuilder = new StringBuilder();

                if (savedInstanceState == null) {

                    Bundle arguments = new Bundle();
                    PokemonDetailFragment fragment = new PokemonDetailFragment();
                    String stringExtra = getIntent().getStringExtra(PokemonDetailFragment.ARG_ITEM_ID);

                    /*
                        Lleno el arguments del PokemonDetailFragment con los datos del Pokemon
                        obtenidos por la llamada al servicio mediante Retrofit
                    */

                    arguments.putString(PokemonDetailFragment.ARG_ITEM_ID, stringExtra);
                    arguments.putInt("id", pokemon.getId());

                    /*
                    * Convierto los String de nombre y tipos del Pokemon
                    * para que tengan la primera letra en mayuscula.
                    * charmander -> Charmander
                    * poison -> Poison.
                    */

                    String pokemonName = pokemon.getName().substring(0,1).toUpperCase() + pokemon.getName().substring(1);
                    arguments.putString("name", pokemonName);

                    pokemon.getTypes().forEach((type) -> {
                                typesBuilder.append(type.getType().getName().substring(0, 1).toUpperCase());
                                typesBuilder.append(type.getType().getName().substring(1));
                                typesBuilder.append(" ");
                            });
                    arguments.putString("types", typesBuilder.toString());

                    pokemon.getStats().forEach((s) -> arguments.putInt(s.getStat().getName(), s.getBaseStat()));

                    fragment.setArguments(arguments);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.pokemon_detail_container, fragment)
                            .commit();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("onFailure PDA: ", t.getMessage());
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            navigateUpTo(new Intent(this, PokemonListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
