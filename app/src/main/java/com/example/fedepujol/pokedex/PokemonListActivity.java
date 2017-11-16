package com.example.fedepujol.pokedex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ar.edu.unsam.pokedex.domain.PokemonAdapter;
import ar.edu.unsam.pokedex.domain.PokemonFeed;
import ar.edu.unsam.pokedex.domain.PokemonService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * An activity representing a list of Pokemons. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PokemonDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class PokemonListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    String BASE_URL = "http://pokeapi.co/api/v2/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.pokemon_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            mTwoPane = true;
        }

        final RecyclerView recyclerView = findViewById(R.id.pokemon_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        //recyclerView.setAdapter(new PokemonAdapter(this, DummyContent.ITEMS, mTwoPane));
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PokemonService pokemonService = retrofit.create(PokemonService.class);

        Call<PokemonFeed> call = pokemonService.getData();
        call.enqueue(new Callback<PokemonFeed>() {
            @Override
            public void onResponse(Response<PokemonFeed> response, Retrofit retrofit) {
                PokemonFeed data = response.body();
                recyclerView.setAdapter(new PokemonAdapter(getApplicationContext(), data.getResults(), mTwoPane, PokemonListActivity.this));
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Pokemon Service Error: ", t.getMessage());
            }
        });

    }
}
