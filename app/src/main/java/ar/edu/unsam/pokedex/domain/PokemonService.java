package ar.edu.unsam.pokedex.domain;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by fedepujol on 14/11/17.
 */

public interface PokemonService {
    @GET("pokemon")
    Call<PokemonFeed> getData();

    @GET("001")
    Call<Pokemon> getPokemon();
}
