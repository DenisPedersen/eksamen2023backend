package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.LocationDto;
import facades.LocationFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/location")
public class LocationResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final LocationFacade FACADE = LocationFacade.getInstance(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo contect;
    @Context
    SecurityContext securityContext;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createLocation(String content) throws Exception {
        LocationDto locationDtoFromJSON = GSON.fromJson(content, LocationDto.class);
        System.out.println(locationDtoFromJSON);
        LocationDto locationDto = FACADE.createLocation(locationDtoFromJSON);
        return Response.ok().entity(GSON.toJson(locationDto)).build();
    }
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLocation(@PathParam("id") int id) {
        return Response.ok().entity(GSON.toJson(FACADE.deleteLocation(id))).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllLocations())).build();
    }
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateLocation(@PathParam("id") int id, String content) {
        LocationDto locationDto = GSON.fromJson(content, LocationDto.class);
        locationDto.setId(id);
        LocationDto updated = FACADE.updateLocation(locationDto);
        return Response.ok().entity(GSON.toJson((updated))).build();
    }
}