package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.LocationDto;
import dtos.PlayerDto;
import entities.Location;
import entities.Match;
import entities.Player;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class PlayerResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();

    Player p1, p2;
    Match m1, m2;
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdown();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
       /* m1 = new Match("Johns hold", "Arne","slutspil", (byte) 0, 1);
        List<Match> matches = new ArrayList<>();
        matches.add(m1);*/
        p1 = new Player("Kristoffer", "123323", "b@b.dk", "skadet");
        //p1.setMatches(matches);
        p2 = new Player("Bjarne", "555", "c@b.dk", "kampklar");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Player.deleteAllRows").executeUpdate();
            //em.persist(m1);
            em.persist(p1);
            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing server");
        given().when().get("/player").then().statusCode(200);
    }

    @Test
    public void testLogRequest() {
        System.out.println("Testing logging request details");
        given().log().all()
                .when().get("/player")
                .then().statusCode(200);
    }

    @Test
    public void testLogResponse() {
        System.out.println("Testing logging response details");
        given()
                .when().get("/player")
                .then().log().body().statusCode(200);
    }

    @Test
    public void getAllPlayers() {
        List<PlayerDto> playerDtoList;

        playerDtoList = given()
                .contentType("application/json")
                .when()
                .get("/player")
                .then()
                .extract().body().jsonPath().getList("", PlayerDto.class);

        PlayerDto playerDto = new PlayerDto(p1);
        PlayerDto playerDto1 = new PlayerDto(p2);
        System.out.println(playerDtoList);
        assertThat(playerDtoList, containsInAnyOrder(playerDto, playerDto1));
    }
    @Test
    public void create() {
        Player p = new Player("Johnny","159","j@j.dk","skadet");
        PlayerDto playerDto = new PlayerDto(p);
        String requstBody = GSON.toJson(playerDto);

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requstBody)
                .when()
                .post("/player")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo("Johnny"))
                .body("phone", equalTo("159"))
                .body("email", equalTo("j@j.dk"))
                .body("status", equalTo("skadet"));
    }

    @Test
    public void delete() {
        given()
                .contentType(ContentType.JSON)
                .delete("/player/" + p1.getId())
                .then()
                .statusCode(200)
                .extract().response().as(Boolean.class);
    }
}
