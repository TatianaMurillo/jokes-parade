package tasks;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;


import java.util.ArrayList;
import java.util.Map;

import static utils.Constants.COMEDIANS;
import static utils.Constants.JOKES;


public class SelectComediansForTheShow implements Task {

    Response response;

    String baseUrl;
    int expectedStatusCode;
    Map<String,Object> queryParams;

    public SelectComediansForTheShow setQueryParams(Map<String,Object> queryParams){
        this.queryParams=queryParams;
        return this;
    }

    public SelectComediansForTheShow(String baseUrl,int expectedStatusCode) {
        this.baseUrl = baseUrl;
        this.expectedStatusCode=expectedStatusCode;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        System.out.println("Entró");
        ArrayList<Map<String,String>> jokes=actor.recall(JOKES);
        ArrayList<Map<String,String>> comedians=actor.recall(COMEDIANS);

        System.out.println("Jokes " +jokes);
        System.out.println("Comedians " +comedians);
        createJokesParade();
    }





    public static SelectComediansForTheShow fromData(String baseUrl,int expectedStatusCode){
        return  Tasks.instrumented(SelectComediansForTheShow.class,baseUrl,expectedStatusCode);
    }

    private void createJokesParade(){

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
