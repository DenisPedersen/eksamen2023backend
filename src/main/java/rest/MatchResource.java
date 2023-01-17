package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.LocationDto;
import dtos.MatchDto;
import facades.MatchFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/match")
public class MatchResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final MatchFacade facade = MatchFacade.getInstance(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMatches() {
        return Response.ok().entity(GSON.toJson(facade.getAllMatches())).build();
    }

    @GET
    @Path("location/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getLocationsForMatch(@PathParam("id") Integer id) {
        return Response.ok().entity(GSON.toJson(facade.getAllMatchesFromLocation(id))).build();
    }

    @GET
    @Path("player/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPlayersInMatch(@PathParam("id") Integer id) {
        return Response.ok().entity(GSON.toJson(facade.getAllPlayersInMatch(id))).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMatch(String content) throws Exception {
        MatchDto matchDtoFromJSON = GSON.fromJson(content, MatchDto.class);
        System.out.println(matchDtoFromJSON);
        MatchDto matchDto = facade.createMatch(matchDtoFromJSON);
        return Response.ok().entity(GSON.toJson(matchDto)).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMatch(@PathParam("id") int id) {
        return Response.ok().entity(GSON.toJson(facade.deleteMatch(id))).build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMatch(@PathParam("id") int id, String content) {
        MatchDto matchDto = GSON.fromJson(content, MatchDto.class);
        matchDto.setId(id);
        MatchDto updated = facade.updateMatch(matchDto);
        return Response.ok().entity(GSON.toJson(updated)).build();
    }
}