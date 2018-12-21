package es.uma.ingenieria_web.rest;

import es.uma.ingenieria_web.model.Actor;
import es.uma.ingenieria_web.model.Director;
import es.uma.ingenieria_web.model.Movie;
import es.uma.ingenieria_web.model.Player;
import es.uma.ingenieria_web.service.DataActors;
import es.uma.ingenieria_web.service.DataDirector;
import es.uma.ingenieria_web.service.DataMovie;
import es.uma.ingenieria_web.service.DataService;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/movies")
public class MovieService {

    @Context
    ServletContext context;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getOrderMovie(
                                  @QueryParam("title") String title,
                                  @QueryParam("genre") String genre,
                                  @QueryParam("year") String year) {

        List<Movie> movies = DataMovie.getInstance().getMovies();
        System.out.println("PUA");
        List<Movie> res = new ArrayList<>();
        if (title == null & genre ==null & year==null){ res = movies;}
        else if (title != null & genre !=null & year!=null){
            for (Movie p : movies) {
                if (p.getTitulo().equals(title) & p.getGenero().equals(genre) & p.getFechaLanzamiento().equals(year)) res.add(p);
            }
        }else if (title!=null){
            for (Movie p : movies) {
                if (p.getTitulo().equals(title)) res.add(p);
            }
        }else if (genre!=null){
            for (Movie p : movies) {
                if (p.getGenero().equals(genre)) res.add(p);
            }
        }else if (year!=null){
            for (Movie p : movies) {
                String anio = p.getFechaLanzamiento().split("-")[0];
                if (anio.equals(year)) res.add(p);
            }
        }


        return Response.ok().entity(res).build();
    }


    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMoviePerId(@PathParam("id") int id) {

        List<Movie> movies = DataMovie.getInstance().getMovies();
        Movie res = null;
        for (Movie p : movies) {
            if (p.getId()== id) res = p;
        }
        System.out.println(res);
        return Response.ok().entity(res).build();
    }

    @GET
    @Path("/{id}/actors")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getactorsofMovie(@PathParam("id") int id) {

        List<Actor> movies = DataMovie.getInstance().getActors_movies(id);

        System.out.println(movies);

        return Response.ok().entity(movies).build();

    }


    @GET
    @Path("/{id_movie}/actors/{id_actor}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMoviesisReallyOnActor(@PathParam("id_actor") int id_actor, @PathParam("id_movie") int id_movie) {

        List<Actor> actors = DataMovie.getInstance().getActors_movies(id_actor);
        List<Actor> ret = new ArrayList<>();
        Integer res = Math.toIntExact(actors.stream().filter(c -> c.getId() == id_actor).count());
        System.out.println(res);
        if(res != null) {
            ret = actors.stream().filter(c -> c.getId() == id_movie).collect(Collectors.toList());
            if (ret.size() == 0) {
                String str = "El actor " + id_actor + " no aparece en la pelicula " + id_movie;
                System.out.println(str);
                ret.add(new Actor(id_actor,str, Integer.toString(id_movie)));

            } else {

            }
        }

        return Response.ok().entity(ret).build();

    }

    @GET
    @Path("/{id}/director")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMoviesOfDirector(@PathParam("id") int id) {

        List<Director> directors = DataMovie.getInstance().getDirector_movies(id);

        System.out.println(directors);

        return Response.ok().entity(directors).build();

    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addMovie(Movie movie) {
        DataMovie.getInstance().addMovies(movie);
        return Response.ok().entity("Movie added.").build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(MediaType.TEXT_PLAIN)
    public Response addMovieForm(
            @FormParam("id") String id,
            @FormParam("title") String title,
            @FormParam("fecha") String fecha,
            @FormParam("genero") String genre) {

        int idInt = Integer.valueOf(id);
        DataMovie.getInstance().addMovies(new Movie(idInt, title, fecha, genre));
        return Response.ok().entity("Movie added from form.").build();
    }


    @DELETE
    @Path("/{id}")
    public Response deleteMovieById(@PathParam("id") Integer id)
    {
        DataMovie.getInstance().deleteMovie(id);
        return Response.status(202).entity("Movie deleted successfully !!").build();
    }



    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMovieFull(@PathParam("id") int id, @QueryParam("title") String name, @QueryParam("year") String fecha,
                                @QueryParam("genero") String genero
    ) {
        Movie updatedMovie = new Movie(id, name, fecha, genero);
        System.out.println("PUT Update movie");
        Movie mov = DataMovie.getInstance().getMovieExists(id);
        if (mov == null) {
            throw new WebApplicationException("Can't find it", 404);
        }
        else if (name!=null && fecha!=null && genero!=null){
            mov.setTitulo(updatedMovie.getTitulo());
            mov.setFechaLanzamiento(updatedMovie.getFechaLanzamiento());
            mov.setGenero(updatedMovie.getGenero());

        }else if (fecha!=null){

            mov.setFechaLanzamiento(updatedMovie.getFechaLanzamiento());

        }else if (genero!=null){

            mov.setGenero(updatedMovie.getGenero());

        }else if (name!=null){
            mov.setTitulo(updatedMovie.getTitulo());
        }

        return Response.noContent().build();
    }


}
