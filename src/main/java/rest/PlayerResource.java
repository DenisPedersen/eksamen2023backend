package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.LocationDto;
import dtos.PlayerDto;
import facades.PlayerFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/player")
public class PlayerResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PlayerFacade FACADE = PlayerFacade.getInstance(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllPlayers())).build();
    }

    @GET
    @Path("match/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMatchesForPlayer(@PathParam("id") Integer matchId) {
        return Response.ok().entity(GSON.toJson(FACADE.getAllPlayersInAMatch(matchId))).build();
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPlayer(String content) throws Exception {
        PlayerDto playerDtoFromJSON = GSON.fromJson(content, PlayerDto.class);
        System.out.println(playerDtoFromJSON);
        PlayerDto playerDto = FACADE.createPlayer(playerDtoFromJSON);
        return Response.ok().entity(GSON.toJson(playerDto)).build();
    }
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlayer(@PathParam("id") int id) {
        return Response.ok().entity(GSON.toJson(FACADE.deletePlayer(id))).build();
    }
}