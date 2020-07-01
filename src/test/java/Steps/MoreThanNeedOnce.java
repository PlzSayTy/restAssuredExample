package Steps;
import Entities.Operation;
import Entities.Verdict;
import com.google.gson.Gson;
import cucumber.api.java.en.When;
import org.apache.http.HttpStatus;

import java.io.*;

import static Steps.StartingSteps.limit;
import static Steps.StartingSteps.userList;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
public class MoreThanNeedOnce {
    Gson gson = new Gson();
    @When("Проверяю лимит по \"(.*)\", \"(.*)\", \"(.*)\"")
    public void limitTest(String account, String method, String value) throws IOException {
        int newValue = Integer.parseInt(value);
        if(account.equals("CARD") && method.equals("SUM") && newValue==1001){
            assertThat(given().log().body()
                    .contentType("application/json").body(new Gson().toJson(new Operation(newValue, account)))
                    .when().post("/users/" + userList.get(0).getId() + "/operations").asString())
                    .isEqualTo(gson.fromJson(new FileReader("src/main/resources/Operations/operationError.json"), Verdict.class).toString());
        }
        if(account.equals("ACCOUNT") && method.equals("SUM") && newValue==1001){
            assertThat(given().log().body()
                    .contentType("application/json").body(new Gson().toJson(new Operation(newValue, account)))
                    .when().post("/users/" + userList.get(0).getId() + "/operations").asString())
                    .isEqualTo(gson.fromJson(new FileReader("src/main/resources/Operations/operationError.json"), Verdict.class).toString());
        }
        if(account.equals("CARD") && method.equals("SUM") && newValue==400){
            assertThat(given().log().body()
                    .contentType("application/json").body(new Gson().toJson(new Operation(newValue, account)))
                    .when().post("/users/" + userList.get(0).getId() + "/operations").asString())
                    .isEqualTo(gson.fromJson(new FileReader("src/main/resources/Operations/operationConfirm.json"), Verdict.class).toString());
        }
        if(account.equals("ACCOUNT") && method.equals("SUM") && newValue==400){
            assertThat(given().log().body()
                    .contentType("application/json").body(new Gson().toJson(new Operation(newValue, account)))
                    .when().post("/users/" + userList.get(0).getId() + "/operations").asString())
                    .isEqualTo(gson.fromJson(new FileReader("src/main/resources/Operations/operationConfirm.json"), Verdict.class).toString());
        }
        if(account.equals("ACCOUNT") && method.equals("COUNT") && newValue==10){
            int count = 0;
            System.out.println(count);
            File file = new File("src/main/resources/Operations/smallOperation.json");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line;
            while (count <= Integer.parseInt(limit.getCount())){
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
                given().log().body()
                        .contentType("application/json").body(content.toString())
                        .when().post("/users/"+ userList.get(0).getId()+"/operations")
                        .then().log().body().statusCode(HttpStatus.SC_OK);
                if(count == Integer.parseInt(limit.getCount())){
                    assertThat(given().log().body()
                            .contentType("application/json").body(content.toString())
                            .when().post("/users/"+ userList.get(0).getId()+"/operations").asString())
                            .isEqualTo(gson.fromJson(new FileReader("src/main/resources/Operations/operationError.json"), Verdict.class).toString());
                }
                count++;
            }
        }

    }
}
