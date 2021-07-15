package tasks;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import java.util.ArrayList;
import java.util.Map;

import static utils.Constants.JOKES;

public class GetJokes implements Task {

    Response response;
    Gson gson = new Gson();

    String baseUrl;
    int expectedStatusCode;
    Map<String,Object> queryParams;

    public GetJokes setQueryParams(Map<String,Object> queryParams){
        this.queryParams=queryParams;
        return this;
    }

    public GetJokes(String baseUrl, int expectedStatusCode) {
        this.baseUrl = baseUrl;
        this.expectedStatusCode=expectedStatusCode;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        loadJokes();

        actor.attemptsTo(ValidateStatusCode.forCode(response.getStatusCode(),expectedStatusCode));
        ArrayList<Map<String,String>> jokes = (ArrayList<Map<String,String>>) gson.fromJson(response.getBody().asString(),Object.class);
        actor.remember(JOKES,jokes);
    }
    public static GetJokes getJokesData(String baseUrl,int expectedStatusCode){
        return new GetJokes(baseUrl,expectedStatusCode);
    }
    private void loadJokes(){

        RestAssured.baseURI= baseUrl;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("Accept", "*/*");
        request= setQueryParams(request);
        response= request.get();
    }

    private RequestSpecification setQueryParams(RequestSpecification request ){

        if(queryParams!=null){
            request.queryParams(queryParams);
        }
        return request;
    }


}
