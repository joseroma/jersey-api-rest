package es.uma.ingenieria_web.rest;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.uma.ingenieria_web.model.Actor;
import es.uma.ingenieria_web.model.Director;
import es.uma.ingenieria_web.model.Movie;
import es.uma.ingenieria_web.model.Player;
import es.uma.ingenieria_web.service.DataActors;
import es.uma.ingenieria_web.service.DataDirector;
import es.uma.ingenieria_web.service.DataMovie;
import es.uma.ingenieria_web.service.DataService;

import javax.ws.rs.Path;
import javax.ws.rs.core.UriInfo;
import javax.xml.crypto.Data;

@Path("/actors")
public class ActorService {

    @Context
    ServletContext context;
    private UriInfo uriInfo;


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getActorByMovie(@QueryParam("name") String name_portion) {
        List<Actor> actors = DataActors.getInstance().getActors();
        System.out.println("PUA");
        List<Actor> res = new ArrayList<>();
        if (name_portion!=null){
            for (Actor p : actors) {
                if (p.getName().equals( name_portion)) res.add( p);
            }
        }else{
            res=actors;
        }
        return Response.ok().entity(res).build();
    }



    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getActorPerId(@PathParam("id") int id) {
        System.out.println("getActor ID - GET");
        List<Actor> actors = DataActors.getInstance().getActors();
        Actor res = null;
        for (Actor p : actors) {
            if (p.getId()== id) res = p;
        }
        System.out.println(res);
        return Response.ok().entity(res).build();
    }

    @GET
    @Path("/{id}/movies")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMoviesOfActor(@PathParam("id") int id) {
        System.out.println("getMoviesOfActor - GET");
        List<Movie> movies = DataActors.getInstance().getMovie_Actor(id);

        System.out.println(movies);

        return Response.ok().entity(movies).build();

    }


    @GET
    @Path("/{id_actor}/movies/{id_movie}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMoviesisReallyOnActor(@PathParam("id_actor") int id_actor, @PathParam("id_movie") int id_movie) {
        System.out.println("getActorIdMovieId - GET");
        List<Actor> actors = DataActors.getInstance().getActors_movies(id_movie);
        List<Actor> ret = new ArrayList<>();
        Integer res = Math.toIntExact(actors.stream().filter(c -> c.getId() == id_actor).count());
        System.out.println(res);
        if(res != null) {
            ret = actors.stream().filter(c -> c.getId() == id_actor).collect(Collectors.toList());
            if (ret.size() == 0) {
                String str = "El actor " + id_actor + " no aparece en la pelicula " + id_movie;
                System.out.println(str);
                ret.add(new Actor(id_actor,str, Integer.toString(id_movie)));

            } else {

            }
        }

        return Response.ok().entity(ret).build();

    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addActor(Actor actor) {
        System.out.println("Post actor empty");
        DataActors.getInstance().addActor(actor);
        return Response.ok().entity("Actor added.").build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(MediaType.TEXT_PLAIN)
    public Response addActorForm(
            @FormParam("id") String id,
            @FormParam("nombre") String nombre,
            @FormParam("apellido") String apellido) {
        System.out.println("Create actor POST");
        int idInt = Integer.valueOf(id);
        DataActors.getInstance().addActor(new Actor(idInt, nombre, apellido));
        return Response.ok().entity("Actor added from form.").build();
    }



    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateActor(@PathParam("id") int id, @QueryParam("name") String name, @QueryParam("apellido") String apellido
                                   ) {
        Actor updatedActor = new Actor(id, name, apellido);
        System.out.println("PUT Update actor");
        Actor actor = DataActors.getInstance().getActorExists(id);
        if (name!=null & apellido!=null){
            actor.setName(updatedActor.getName());
            actor.setApellido(updatedActor.getApellido());
        }else if(name!=null & apellido == null){
            actor.setName(updatedActor.getName());
        }else if(apellido!=null & name==null){
            actor.setApellido(updatedActor.getApellido());
        }else if (name==null & apellido == null){
            throw new WebApplicationException("No parameter for Updating actor", 404);
        }
        if (actor == null) {
            throw new WebApplicationException("Can't find it", 404);
        }
        return Response.noContent().build();
    }



    @DELETE
    @Path("/{id}")
    public Response deleteActorById(@PathParam("id") Integer id)
    {
        DataActors.getInstance().deleteActor(id);
        return Response.status(202).entity("Actor deleted successfully !!").build();
    }
}
