package ar.edu.unsam.pokedex.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fedepujol on 14/11/17.
 */

public class RepoPokemon {
    private Map<String, Result> pokemonMap = new HashMap<String, Result>();
    private static RepoPokemon instance;

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

}
