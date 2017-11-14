package ar.edu.unsam.pokedex.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fedepujol on 14/11/17.
 */

public class PokemonFeed {
    private Integer count;
    private Object previous;
    private List<Result> results = new ArrayList<Result>();
    private String next;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
