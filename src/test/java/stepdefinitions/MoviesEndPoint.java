package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import utilities.ConfigReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;


public class MoviesEndPoint {

    Response response;

    Map<String, Object> data = new HashMap<>();

    @Given("the user set the uri")
    public void the_user_set_the_uri() {

        baseURI = ConfigReader.getProperty("rakuten_uri");
    }

    @And("the user sends post request with {string} end point")
    public void theUserSendsPostRequestWithEndPoint(String endPoint,Map<String, Object> data) {

//        Map<String, Object> data = new HashMap<>();
//        data.put("title","Salamanca");
//        data.put("year",1920);
//        data.put("plot","anyword");
//        data.put("duration",110);
//        data.put("audio_qualities","");
//        data.put("video_qualities","");
//        data.put("genres","");

        response = given().accept(ContentType.JSON).contentType(ContentType.JSON).and().body(data).post(endPoint);

    }

    @Given("Verify the status code is {int}")
    public void verify_the_status_code_is(int statusCode) {
        Assert.assertEquals(statusCode,response.statusCode());

    }

    @When("Verify the content Type is {string}")
    public void verify_the_content_type_is(String contentType) {

        Assert.assertEquals(contentType,response.contentType());

    }

    @Then("Print the response body")
    public void print_the_response_body() {
        response.prettyPrint();
    }


    @Given("the user sends get request with {string} end point {int}")
    public void the_user_sends_get_request_with_end_point(String endPoint, int id) {
        response = given().accept(ContentType.JSON).and().pathParam("id",id).when().
                get(endPoint+"/{id}");
        response.prettyPrint();

    }

    @Then("Verify the response body is equal which is you posted")
    public void verify_the_response_body_is_equal_which_is_you_posted() {

        Map<String, Object> data = new HashMap<>();
        data.put("id",1410);
        data.put("title","Salamanca");
        data.put("year",1920);
        data.put("plot","anyword");
        data.put("duration",110);
        data.put("audio_qualities","");
        data.put("video_qualities","");
        data.put("genres","");

        JsonPath jsonPath = response.jsonPath();

        Assert.assertEquals(data.get("id"),jsonPath.getInt("id"));


//        Assert.assertEquals(data.get("id"),response.path("id"));
//        Assert.assertEquals(data.get("title"),response.path("title"));
//        Assert.assertEquals(data.get("year"),response.path("year"));
//        Assert.assertEquals(data.get("plot"),response.path("plot"));
//        Assert.assertEquals(data.get("duration"),response.path("duration"));

    }


    @Given("the user sends put request with {string} end point {int}")
    public void the_user_sends_put_request_with_end_point(String endPoint, int id) {

        data.put("id",1410);
        data.put("title","Madrid");
        data.put("year",1930);
        data.put("plot","anyword");
        data.put("duration",110);
        data.put("audio_qualities","");
        data.put("video_qualities","");
        data.put("genres","");

        response=  given().accept(ContentType.JSON).pathParam("id",id).when().body(data).
                put(endPoint+"/{id}");
    }

    @Then("Verify the response body is equal which is you updated")
    public void verify_the_response_body_is_equal_which_is_you_updated() {

        response = given().get("/movies/1410");

        Assert.assertEquals(data.get("id"),response.path("id"));
        Assert.assertEquals(data.get("title"),response.path("title"));
        Assert.assertEquals(data.get("year"),response.path("year"));
        Assert.assertEquals(data.get("plot"),response.path("plot"));
        Assert.assertEquals(data.get("duration"),response.path("duration"));



    }



}
