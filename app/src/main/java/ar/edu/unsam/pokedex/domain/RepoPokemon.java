package ar.edu.unsam.pokedex.domain;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by fedepujol on 14/11/17.
 */

public class RepoPokemon {
    private Map<String, Result> pokemonMap = new HashMap<String, Result>();
    private static RepoPokemon instance;
    private Pokemon pokemonRetrofit;
    private String BASE_URL = "http://pokeapi.co/api/v2/";

    private RepoPokemon() {

    }

    public static RepoPokemon getInstance() {
        if (instance == null) {
            instance = new RepoPokemon();
        }
        return instance;
    }

    public void agregarAMap(List<Result> resultList) {
        for (int i = 0; i < resultList.size(); i++) {

            changePokemonId(resultList.get(i), i);
            changePokemonName(resultList.get(i));
            pokemonMap.put(resultList.get(i).getName(), resultList.get(i));
        }
    }

    private void changePokemonId(Result result, int position) {
        String idAux;
        if (position < 9) {
            idAux = "00";
        } else {
            idAux = "0";
        }

        result.setId(idAux + String.valueOf(position + 1));
    }

    private void changePokemonName(Result result){
        StringBuilder pokemonNameAux = new StringBuilder();

        pokemonNameAux.append(result.getName().substring(0, 1).toUpperCase());
        pokemonNameAux.append(result.getName().substring(1));

        result.setName(pokemonNameAux.toString());
    }

    public Result findPokemonByName(String name) {
        return pokemonMap.get(name);
    }


    public Pokemon pokemonFromResultName(){
        //Result resultAux = pokemonMap.get(name);

        //Retrofit intent
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PokemonService pokemonService = retrofit.create(PokemonService.class);

        Call<Pokemon> call = pokemonService.getPokemon();
        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Response<Pokemon> response, Retrofit retrofit) {
                try {
                    Pokemon pokemon = response.body();
                    boolean r = pokemon == null;
                    Log.e("r", String.valueOf(r));
                } catch (Exception e){
                    Log.e("Exception onResponse", e.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Pokemon Service Error: ", t.getMessage());
            }
        });
        //

        return pokemonRetrofit;
    }

}
