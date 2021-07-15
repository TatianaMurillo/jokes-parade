package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.util.ArrayList;
import java.util.Map;

import static utils.Constants.*;

public class ListForTheShowIsReady implements Question<Boolean> {

    boolean listValidation;

    @Override
    public Boolean answeredBy(Actor actor) {
       listValidation=true;

        ArrayList<Map<String,String>> jokes=actor.recall(JOKES);
        ArrayList<Map<String,String>> comedians=actor.recall(COMEDIANS);
        ArrayList<Map<String,Object>> jokeComedianList = actor.recall(JOKES_USERS_LIST);

        for (Map<String,Object> jokeComedian:jokeComedianList) {
            validateList(jokes.contains(jokeComedian.get("joke")));
            validateList(comedians.contains(jokeComedian.get("comedian")));
        }
        return listValidation;

    }
    public static ListForTheShowIsReady andComplete(){
        return new ListForTheShowIsReady();
    }
    private  void validateList(boolean validation){
        listValidation= !validation?false:listValidation;
    }
}
