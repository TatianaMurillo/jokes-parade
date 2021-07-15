package tasks;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

import java.util.Map;
import static utils.Constants.COMEDIANS;

public class GetUsers implements Task {

    Response response;
    Gson gson = new Gson();

    String baseUrl;
    int expectedStatusCode;
    Map<String,Object> queryParams;

    public GetUsers setQueryParams(Map<String,Object> queryParams){
        this.queryParams=queryParams;
        return this;
    }

    public GetUsers(String baseUrl,int expectedStatusCode) {
        this.baseUrl = baseUrl;
        this.expectedStatusCode=expectedStatusCode;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        loadUsers();
        actor.attemptsTo(ValidateStatusCode.forCode(response.getStatusCode(),expectedStatusCode));
        Map<String,Object> body = gson.fromJson(response.getBody().asString(),Map.class);
        actor.remember(COMEDIANS,body.get("data"));
    }

    public static GetUsers getUsersData(String baseUrl,int expectedStatusCode){
        return  Tasks.instrumented(GetUsers.class,baseUrl, expectedStatusCode);
    }

    private void loadUsers(){

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
