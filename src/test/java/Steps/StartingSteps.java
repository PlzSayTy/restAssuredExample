package Steps;
import Entities.Limit;
import Entities.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.*;
import org.apache.http.HttpStatus;
import java.io.*;
import java.util.*;
import static org.assertj.core.api.Assertions.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
public class StartingSteps {
    Gson gson = new Gson();
    RequestSpecification requestSpecification;
    public static List<User> userList = new ArrayList<>();
    String json;
    public static Limit limit;
    @Before
    public void setUp(){
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(8888)
                .build();
        RestAssured.requestSpecification = requestSpecification;
    }
    @When("Создаю юзера")
    public void test() throws IOException {
        assertThat(when().get(" /other/getTestUser").getStatusCode()).isEqualTo(200);

    }
    @When("Получаю информачию о нём")
    public void infoTaking() throws IOException {
        json = when().get("/users").getBody().asString();
        userList = gson.fromJson(json, new TypeToken<ArrayList<User>>(){}.getType());
        userList.stream()
                .map(User -> String.format("%s  %s", User.getId(), User.getName()));
        System.out.println(userList.get(0));
    }
    @When("Провожу операцию и проверяю результат")
    public void examleOperation() throws IOException {
        File file = new File("src/main/resources/Operations/example.json");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }
        given().log().body()
                .contentType("application/json").body(content.toString()).
                when().post("/users/"+ userList.get(0).getId()+"/operations")
                .then().log().body().statusCode(HttpStatus.SC_OK);
        //assertThat(new Gson().toJson(content)).isEqualTo(when().get("/users/"+ userList.get(0).getId()+"/operations").getBody().asString());
    }
    @When("Получаю информачию об оставшихся лимитах")
    public void takeLimits(){
        json = when().get("/users/"+ userList.get(0).getId()+"/remain").getBody().asString();
        limit = gson.fromJson(json, Limit.class);
        System.out.println(limit.toString());
    }
}
