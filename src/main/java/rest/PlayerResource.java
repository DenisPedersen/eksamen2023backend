package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
}