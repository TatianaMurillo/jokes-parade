package tasks;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.JokesParade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static utils.Constants.*;

public class CreateShow implements Task {

    Response response;
    Gson gson = new Gson();

    String baseUrl;
    int expectedStatusCode;
    Map<String,Object> queryParams;

    public CreateShow(String baseUrl, int expectedStatusCode) {
        this.baseUrl = baseUrl;
        this.expectedStatusCode=expectedStatusCode;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        callMethod(actor);
        ArrayList<JokesParade> list = (ArrayList<JokesParade>) gson.fromJson(response.getBody().asString(),Object.class);
        actor.remember(JOKES_USERS_LIST,list);
    }

    public static CreateShow withComediansAndJokes(String baseUrl,int expectedStatusCode){
        return new CreateShow(baseUrl,expectedStatusCode);
    }


    private  ArrayList<JokesParade> generateRequest(Actor actor){

        ArrayList<Map<String,String>> jokes=actor.recall(JOKES);
        ArrayList<Map<String,String>> comedians=actor.recall(COMEDIANS);
        Random random = new Random();
        boolean generateRandom = jokes.size()<comedians.size()?true:false;

        ArrayList<JokesParade> list = new ArrayList<>();
        for (Map<String,String> comedian:comedians) {
            if(generateRandom){
                list.add(new JokesParade(jokes.get(comedians.indexOf(random.nextInt(jokes.size()))),comedian));
            }else{
                list.add(new JokesParade(jokes.get(comedians.indexOf(comedian)),comedian));
            }
        }
        return list;
    }

    private void callMethod(Actor actor){

        RestAssured.baseURI= baseUrl;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("Accept", "*/*");
        request= setQueryParams(request);
        request.body(generateRequest(actor));
        response= request.post();
    }

    private RequestSpecification setQueryParams(RequestSpecification request ){

        if(queryParams!=null){
            request.queryParams(queryParams);
        }
        return request;
    }

}
