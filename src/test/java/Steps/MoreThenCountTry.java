package Steps;

import Entities.Verdict;
import com.google.gson.Gson;
import cucumber.api.java.en.When;
import org.apache.http.HttpStatus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static Steps.StartingSteps.limit;
import static Steps.StartingSteps.userList;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
public class MoreThenCountTry {
    @When("Превышаю лимит по количеству операций")
    public void moreThanCount() throws IOException {
        Gson gson = new Gson();
        int count = 0;
        while (count <= Integer.parseInt(limit.getCount())+1){
            File file = new File("src/main/resources/Operations/smallOperation.json");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
            given().log().body()
                    .contentType("application/json").body(content.toString())
                    .when().post("/users/"+ userList.get(0).getId()+"/operations")
                    .then().log().body().statusCode(HttpStatus.SC_OK);
            if(count == Integer.parseInt(limit.getCount())+1){
                assertThat(given().log().body()
                        .contentType("application/json").body(content.toString())
                        .when().post("/users/"+ userList.get(0).getId()+"/operations").asString())
                        .isEqualTo(gson.fromJson(new FileReader("src/main/resources/Operations/operationError.json"), Verdict.class).toString());
            }
            System.out.println(count);
            count++;
        }
    }

}
