import com.jayway.restassured.response.Cookie;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.given;


public class MyIssue {
    String sessionId;

    @BeforeTest
    public void login(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";


        String body = "{\n" +
                "    \"username\": \"m.koleboshin\",\n" +
                "    \"password\": \"m.koleboshin\"\n" +
                "}";
        sessionId = given().
                contentType("application/json").
                body(body).
                //header("", "").
                when().
                post("/rest/auth/1/session").
                then().
                //statusCode(200);
                extract().
                path("session.value");
        System.out.println("SESSIOID=" + sessionId);

    }

    @Test
    public void createIssue(){

        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";


        String body = "{\n" +
                "\"fields\": {\n" +
                "        \"project\": {\n" +
                "            \"id\": \"10315\"\n" +
                "        },\n" +
                "        \"summary\": \"rest assured\",\n" +
                "        \"issuetype\": {\n" +
                "            \"id\": \"10004\"\n" +
                "        },\n" +
                "        \"assignee\": {\n" +
                "            \"name\": \"m.koleboshin\"\n" +
                "        },\n" +
                "        \"reporter\": {\n" +
                "            \"name\": \"m.koleboshin\"\n" +
                "        }\n" +
                "\t}\n" +
                "}";


        given().
                header("Cookie", "JSESSIONID="+sessionId).
                header("Content-Type", "application/json").
                body(body).
                when().
                post("/rest/api/2/issue").
                then().
                statusCode(201);
    }

}