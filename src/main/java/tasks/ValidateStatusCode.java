package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

import static utils.Constants.CODE_STATUS_VALIDATION;

public class ValidateStatusCode implements Task {

    int expectedStatusCode;
    int currentStatusCode;

    public ValidateStatusCode(int currentStatusCode,int expectedStatusCode){
        this.expectedStatusCode=expectedStatusCode;
        this.currentStatusCode=currentStatusCode;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
            actor.remember(CODE_STATUS_VALIDATION,expectedStatusCode==currentStatusCode?true:false);
    }

    public static ValidateStatusCode forCode(int currentStatusCode,int expectedStatusCode){
        return Tasks.instrumented(ValidateStatusCode.class,currentStatusCode,expectedStatusCode);
    }
}
