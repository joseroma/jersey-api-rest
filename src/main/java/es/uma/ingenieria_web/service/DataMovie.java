package es.uma.ingenieria_web.service;

import es.uma.ingenieria_web.model.Actor;
import es.uma.ingenieria_web.model.Director;
import es.uma.ingenieria_web.model.Movie;
import es.uma.ingenieria_web.model.Player;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

import static sun.tools.java.Constants.NULL;


public class DataMovie {
    private List<Movie> movies = new ArrayList<Movie>();
    private HashMap<Movie, Actor> actors_movies = new HashMap<Movie, Actor>();
    private HashMap<Movie, Director> director_movies = new HashMap<Movie, Director>();

    private static DataMovie instance = new DataMovie();

    private DataMovie() {

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

        Statement stmt1 = null;
        ResultSet rs_movies_actor = null;
        Connection conn1 = null;

        Statement stmt2 = null;
        ResultSet rs_movies_director = null;
        Connection conn2 = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn =
                    DriverManager.getConnection("jdbc:mysql://localhost/filmAffinity3?" +
                            "user=root&password=nairapass");
            conn1 =
                    DriverManager.getConnection("jdbc:mysql://localhost/filmAffinity3?" +
                            "user=root&password=nairapass");
            conn2 =
                    DriverManager.getConnection("jdbc:mysql://localhost/filmAffinity3?" +
                            "user=root&password=nairapass");
            stmt = conn.createStatement();
            stmt1 = conn.createStatement();
            stmt2 = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM PELICULA;");
            rs_movies_actor = stmt1.executeQuery("SELECT * FROM ACTOR a, PELICULA_has_ACTOR m, PELICULA p WHERE p.oid_pelicula = m.PELICULA_oid_pelicula AND a.oid_ACTOR = m.ACTOR_oid_ACTOR;");
            rs_movies_director = stmt2.executeQuery("SELECT * FROM DIRECTOR a, PELICULA_has_DIRECTOR m, PELICULA p WHERE p.oid_pelicula = m.PELICULA_oid_pelicula AND a.oid_director = m.DIRECTOR_oid_director;");

            if (stmt.execute("SELECT * FROM PELICULA;")) {
                rs = stmt.getResultSet();

            }

            if (stmt1.execute("SELECT p.oid_pelicula,  p.titulo,p.fecha_lanzamiento,p.genero, a.oid_ACTOR, a.nombre, a.apellido FROM ACTOR a, PELICULA_has_ACTOR m, PELICULA p WHERE p.oid_pelicula = m.PELICULA_oid_pelicula AND a.oid_ACTOR = m.ACTOR_oid_ACTOR;")) {
                rs_movies_actor = stmt1.getResultSet();
            }

            if (stmt2.execute("SELECT * FROM DIRECTOR a, PELICULA_has_DIRECTOR m, PELICULA p WHERE p.oid_pelicula = m.PELICULA_oid_pelicula AND a.oid_director = m.DIRECTOR_oid_director;")) {
                rs_movies_director = stmt2.getResultSet();
            }



            while (rs.next())  {
                ResultSetMetaData rsmd = rs.getMetaData();
                Integer id_ = rs.getInt(1);
                String tit = rs.getString(2);
                String fechaLanz = rs.getString(4);
                String gen = rs.getString(5);
                movies.add(new Movie(id_, tit, fechaLanz, gen));

            }
            while (rs_movies_actor.next()) {

                ResultSetMetaData rsmd = rs_movies_actor.getMetaData();
                Integer id_mov = rs_movies_actor.getInt(1);
                String tit_mov = rs_movies_actor.getString(2);
                String fec_mov = rs_movies_actor.getString(3);
                String gen_mov = rs_movies_actor.getString(4);
                Integer id_act = rs_movies_actor.getInt(5);
                String act_nom = rs_movies_actor.getString(6);
                String act_apel = rs_movies_actor.getString(7);
                actors_movies.put(new Movie(id_mov, tit_mov, fec_mov, gen_mov), new Actor(id_act,act_nom, act_apel));
            }

            while (rs_movies_director.next()) {

                ResultSetMetaData rsmd = rs_movies_director.getMetaData();
                Integer id_mov = rs_movies_director.getInt(8);
                String tit_mov = rs_movies_director.getString(9);
                String fec_mov = rs_movies_director.getString(11);
                String gen_mov = rs_movies_director.getString(12);
                Integer id_dir = rs_movies_director.getInt(1);
                String nom_dir = rs_movies_director.getString(2);
                director_movies.put(new Movie(id_mov, tit_mov, fec_mov, gen_mov), new Director(id_dir, nom_dir));

            }


            // Now do something with the ResultSet ....
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

            if (rs != null) {
                try { rs.close();
                } catch (SQLException sqlEx) { } rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }
        }


    }

    public static DataMovie getInstance() {
        return instance;
    }

    public List<Movie> getMovies() {
        System.out.println(movies);
        System.out.println("LENGTH:"+movies.size());
        return movies;
    }

    public void addMovies(Movie p) {
        System.out.println("adding "+p);
        movies.add(p);
    }


/*
        Devuelve todos los actores que participen en la película Movie_id
      */

    public List<Actor> getActors_movies(final int Movie_id) {
        List<Actor> pelis_act = new ArrayList<Actor>();
        if (actors_movies.size()!=NULL) {
            pelis_act = actors_movies.entrySet().stream().filter(x->x.getKey().getId()==Movie_id).map(x -> x.getValue()).collect(Collectors.toList());
            System.out.println( pelis_act);
            System.out.println("LENGTH:" + pelis_act.size());

        }else{
            System.out.println("Tiene que dar valor al ID para buscar por ID");

        }
        return pelis_act;
    }

    /*
    Devuelve todas las peliculas en las que ha participado Actor_id
     */

    public List<Movie> getMovie_Actor(final int Actor_id) {
        List<Movie> actor_mov = new ArrayList<Movie>();
        if (actors_movies.size()!=NULL) {
            actor_mov = actors_movies.entrySet().stream().filter(x->x.getValue().getId()==Actor_id).map(x -> x.getKey()).collect(Collectors.toList());
            System.out.println( actor_mov);
            System.out.println("LENGTH:" + actor_mov.size());

        }else{
            System.out.println("Tiene que dar valor al ID para buscar por ID");

        }
        return actor_mov;
    }

    /*
        Devuelve todos los actores que participen en la película Movie_id
      */

    public List<Director> getDirector_movies(final int Movie_id) {
        List<Director> pelis_act = new ArrayList<Director>();
        if (director_movies.size()!=NULL) {
            pelis_act = director_movies.entrySet().stream().filter(x->x.getKey().getId()==Movie_id).map(x -> x.getValue()).collect(Collectors.toList());
            System.out.println( pelis_act);
            System.out.println("LENGTH:" + pelis_act.size());

        }else{
            System.out.println("Tiene que dar valor al ID para buscar por ID");

        }
        return pelis_act;
    }

    public void deleteMovie(Integer id) {
        System.out.println("deleting..." + id);
        for (Movie a:movies){
            if(a.getId() == id){
                movies.remove(a);
            }
        }
    }


    public Movie getMovieExists(Integer id) {
        Movie act = new Movie();
        for (Movie a:movies){
            if(a.getId() == id){
                act = a;
            }
        }
        return act;
    }


    // public static void main(String [] args) throws ClassNotFoundException{
   //     DataMovie.getInstance().getMovies();
   // }

}

