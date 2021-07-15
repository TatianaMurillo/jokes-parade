package stepdefinitions;



import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;


import questions.ListForTheShowIsReady;
import tasks.CreateShow;
import tasks.GetJokes;
import tasks.GetUsers;


import static utils.Constants.*;

public class NightComediansStepDefinitions {


    @Before
    public void setUp() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("^I am a stand up comedy manager$")
    public void iAmAStandUpComedyManager() {
        OnStage.theActorCalled("user").attemptsTo(
                GetUsers.getUsersData(BASE_URL_FOR_USERS,CODE_STATUS_200),
                GetJokes.getJokesData(BASE_URL_FOR_JOKES,CODE_STATUS_200)
        );
    }
    @When("^I select all comedians and jokes for the show$")
    public void iSelectAllComediansAndJokesForTheShow() {
        OnStage.theActorInTheSpotlight().attemptsTo(CreateShow.withComediansAndJokes(BASE_URL_FOR_JOKES_USER,CODE_STATUS_200));
    }

    @Then("^I have a list with the show presentations$")
    public void iHaveAListWithTheShowPresentations() {
            OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat(ListForTheShowIsReady.andComplete()));
    }
}
