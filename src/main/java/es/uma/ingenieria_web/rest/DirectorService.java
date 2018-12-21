package es.uma.ingenieria_web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import es.uma.ingenieria_web.model.Actor;
import es.uma.ingenieria_web.model.Director;
import es.uma.ingenieria_web.model.Movie;
import es.uma.ingenieria_web.service.DataActors;
import es.uma.ingenieria_web.service.DataDirector;
import es.uma.ingenieria_web.service.DataMovie;
import es.uma.ingenieria_web.service.ServiceMySQL;
@Path("/directors")
public class DirectorService {

    @Context
    ServletContext context;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getDirectorByMovie(@QueryParam("name") String name) {

        List<Director> actors = DataDirector.getInstance().getDirectors();
        List<Director> res = new ArrayList<>();
        if (name!=null){
            for (Director p : actors) {
                if (p.getName().equals(name)){
                    res.add(p);
                }
            }
        }else{
            res = actors;
        }

        System.out.println("PUA");

        return Response.ok().entity(res).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getDirectorPerId(@PathParam("id") int id) {

        List<Director> actors = DataDirector.getInstance().getDirectors();
        Director res = null;
        for (Director p : actors) {
            if (p.getId()== id) res = p;
        }
        System.out.println(res);
        return Response.ok().entity(res).build();
    }

    @GET
    @Path("/{id}/movies")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMoviesOfDirector(@PathParam("id") int id) {

        List<Movie> movies = DataDirector.getInstance().getMovie_Director(id);

        System.out.println(movies);

        return Response.ok().entity(movies).build();

    }


    @GET
    @Path("/{id_actor}/movies/{id_movie}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMoviesisReallyOnDirector(@PathParam("id_actor") int id_director, @PathParam("id_movie") int id_movie) {

        List<Director> actors = DataDirector.getInstance().getDirector_movies(id_movie);
        List<Director> ret = new ArrayList<>();
        Integer res = Math.toIntExact(actors.stream().filter(c -> c.getId() == id_director).count());
        System.out.println(res);
        if(res != null) {
            ret = actors.stream().filter(c -> c.getId() == id_director).collect(Collectors.toList());
            if (ret.size() == 0) {
                String str = "El director " + id_director + " no dirije la pelicula " + id_movie;
                System.out.println(str);
                ret.add(new Director(id_director,str));

            } else {

            }
        }

        return Response.ok().entity(ret).build();

    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addDirector(Director director) {
        DataDirector.getInstance().addDirector(director);
        return Response.ok().entity("Director added.").build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(MediaType.TEXT_PLAIN)
    public Response addDirectorForm(
            @FormParam("id") String id,
            @FormParam("nombre") String nombre) {

        int idInt = Integer.valueOf(id);
        DataDirector.getInstance().addDirector(new Director(idInt, nombre));
        return Response.ok().entity("Director added from form.").build();
    }


    @DELETE
    @Path("/{id}")
    public Response deleteDirectorById(@PathParam("id") Integer id)
    {
        DataDirector.getInstance().deleteDirector(id);
        return Response.status(202).entity("Director deleted successfully !!").build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateActor(@PathParam("id") int id, @QueryParam("name") String name
    ) {
        Director updatedDirector = new Director(id, name);
        System.out.println("PUT Update actor");
        Director director = DataDirector.getInstance().getDirectorExists(id);
        if (name!=null){
            director.setName(updatedDirector.getName());
        }else {
            throw new WebApplicationException("No parameter for Updating actor", 404);
        }
        if (director == null) {
            throw new WebApplicationException("Can't find it", 404);
        }
        return Response.noContent().build();
    }

}
