import com.jayway.restassured.response.Cookie;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import static com.jayway.restassured.RestAssured.given;


public class MyIssue {
    String sessionId = "";
    String issueId = "";

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
                "        \"summary\": \"rest assured issue test\",\n" +
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
        System.out.println(issueId);
    }

    @Test
    public void getIssue(){
        Response response;
        RestAssured.baseURI="http://soft.it-hillel.com.ua:8080";
        response= given().
                header("Cookie", "JSESSIONID="+sessionId).
                when().
                get("/rest/api/2/issue/QAAUT-459").
                then().
                extract().
                response();
        System.out.println(response);
        response.prettyPrint();
    }

    @Test
    public void addComment(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";
        String body="{\n" +
                "    \"body\": \"vse ok 1111\"\n" +
                "}"                ;
        System.out.println(body);
        given().
                contentType("application/json").
                header("Cookie", "JSESSIONID="+sessionId).
                //header("Content-Type", "application/json").
                body(body).
                when().
                post("/rest/api/2/issue/13619/comment").
                then().
                //extract().
                statusCode(201);
         }

    @Test
    public void updateIssueType(){
        RestAssured.baseURI="http://soft.it-hillel.com.ua:8080";
        String body="{\n" +
                "    \"body\": \"Body comment - test\"\n" +
                "}"
                ;
        System.out.println(body);
        given().
                header("Cookie", "JSESSIONID="+sessionId).
                header("Content-Type", "application/json").
                body(body).
                when().
                post("/rest/api/2/issue/13619/comment").
                then().
                statusCode(201);

    }

    @Test
    public void getFilter(){
        Response response= given().
                header("Cookie", "JSESSIONID="+sessionId).
                when().
                get("/rest/api/2/filter/13619").
                then()
                //contentType(ContentType.JSON)
                .extract().response();
        response.prettyPrint();
    }

    @Test
    public void deleteIssue(){
        given()
                .header("Cookie", "JSESSIONID="+sessionId)
                .when().delete("/rest/api/2/issue/QAAUT-459")
                .then().statusCode(204);
    }

    /*@Test
    public void loginNegative(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";
        String body = "{\n" +
                "    \"username\": \"m.koleboshin\",\n" +
                "    \"password\": \"111\"\n" +
                "}";
        given().
                contentType("application/json").`c8]5200
                body(body).
                //header("", "").
                        when().
                post("/rest/auth/1/session").
                then().
                statusCode(401);
    }*/

}