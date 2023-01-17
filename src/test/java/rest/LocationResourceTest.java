package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.LocationDto;
import entities.Location;
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
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LocationResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();

    Location l1, l2;

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
        l1 = new Location("Bygaden 44", "Næstved");
        l2 = new Location("Vejgade 1", "Byen");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Location.deleteAllRows").executeUpdate();
            em.persist(l1);
            em.persist(l2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing serverstatus");
        given().when().get("/location").then().statusCode(200);
    }

    @Test
    public void testLogRequest() {
        System.out.println("Testing logging request details");
        given().log().all()
                .when().get("/location")
                .then().statusCode(200);
    }

    @Test
    public void testLogResponse() {
        System.out.println("Testing logging response details");
        given()
                .when().get("/location")
                .then().log().body().statusCode(200);
    }

    @Test
    public void getAll() throws Exception {
        List<LocationDto> locationDtos;

        locationDtos = given()
                .contentType("application/json")
                .when()
                .get("/location")
                .then()
                .extract().body().jsonPath().getList("", LocationDto.class);

        LocationDto locationDto = new LocationDto(l1);
        LocationDto locationDto1 = new LocationDto(l2);
        assertThat(locationDtos, containsInAnyOrder(locationDto1, locationDto));
    }
    @Test
    public void create() {
        Location l = new Location("Stivej 3","Ballerup");
        LocationDto locationDto = new LocationDto(l);
        String requstBody = GSON.toJson(locationDto);

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requstBody)
                .when()
                .post("/location")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue())
                .body("address", equalTo("Stivej 3"))
                .body("city", equalTo("Ballerup"));
    }

    @Test
    public void delete() {
        given()
                .contentType(ContentType.JSON)
                .delete("/location/" + l1.getId())
                .then()
                .statusCode(200)
                .extract().response().as(Boolean.class);
    }
}
