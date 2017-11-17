package ar.edu.unsam.pokedex.domain;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by fedepujol on 14/11/17.
 */

public interface PokemonService {
    @GET("pokemon")
    Call<PokemonFeed> getData();

    @GET("{numeroPokemon}")
    Call<PokemonJson> getPokemon(@Path("numeroPokemon") String numeroPokemon);

}
