package models;

import java.util.Map;

public class JokesParade {
    Map<String,String> joke;
    Map<String,String> comedian;

    public JokesParade(Map<String,String> joke,Map<String,String> comedian){
        this.joke=joke;
        this.comedian=comedian;
    }
}
