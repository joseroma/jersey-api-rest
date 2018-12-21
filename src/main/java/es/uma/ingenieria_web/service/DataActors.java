package es.uma.ingenieria_web.service;

import es.uma.ingenieria_web.model.Actor;
import es.uma.ingenieria_web.model.Movie;
import es.uma.ingenieria_web.model.Player;
import org.jvnet.hk2.internal.Collector;

import javax.validation.constraints.Null;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static sun.tools.java.Constants.NULL;
import static sun.tools.java.Constants.TRUE;


public class DataActors {
    private List<Actor> actors = new ArrayList<Actor>();
    private HashMap<Movie, Actor> actors_movies = new HashMap<Movie, Actor>();

    private static DataActors instance = new DataActors();

    private DataActors() {

        Statement stmt = null;
        Statement stmt1 = null;
        ResultSet rs = null;
        ResultSet rs_movies = null;
        Connection conn = null;
        Connection conn1 = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            conn =
                    DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/filmAffinity3" ,
                            "root", "nairapass");
            conn1 =
                    DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/filmAffinity3" ,
                            "root", "nairapass");
            stmt = conn.createStatement();
            stmt1 = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM ACTOR");
            rs_movies = stmt1.executeQuery("SELECT * FROM ACTOR a, PELICULA_has_ACTOR m, PELICULA p WHERE p.oid_pelicula = m.PELICULA_oid_pelicula AND a.oid_ACTOR = m.ACTOR_oid_ACTOR;");

            if (stmt.execute("SELECT * FROM ACTOR")) {
                rs = stmt.getResultSet();

            }

            if (stmt1.execute("SELECT p.oid_pelicula,  p.titulo,p.fecha_lanzamiento,p.genero, a.oid_ACTOR, a.nombre, a.apellido FROM ACTOR a, PELICULA_has_ACTOR m, PELICULA p WHERE p.oid_pelicula = m.PELICULA_oid_pelicula AND a.oid_ACTOR = m.ACTOR_oid_ACTOR;")) {
                    rs_movies = stmt1.getResultSet();
            }


            while (rs.next())  {
                ResultSetMetaData rsmd = rs.getMetaData();
                Integer id_ = rs.getInt(1);
                String nom = rs.getString(2);
                String apel = rs.getString(3);
                actors.add(new Actor(id_, nom, apel));
            }
            //System.out.println(actors);


            while (rs_movies.next()) {

                    ResultSetMetaData rsmd = rs_movies.getMetaData();
                    Integer id_mov = rs_movies.getInt(1);
                    String tit_mov = rs_movies.getString(2);
                    String fec_mov = rs_movies.getString(3);
                    String gen_mov = rs_movies.getString(4);
                    Integer id_act = rs_movies.getInt(5);
                    String act_nom = rs_movies.getString(6);
                    String act_apel = rs_movies.getString(7);
                    actors_movies.put(new Movie(id_mov, tit_mov, fec_mov, gen_mov), new Actor(id_act,act_nom, act_apel));
                }
                //System.out.println(actors_movies);



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

    public static DataActors getInstance() {
        return instance;
    }

    /*
    Devuelve todos los actores
     */

    public List<Actor> getActors() {
        System.out.println("LENGTH:"+actors.size());
        return actors;
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


    public void addActor(Actor p) {
        System.out.println("adding "+p);
        actors.add(p);
    }

    public void deleteActor(Integer p) {
        System.out.println("deleting "+p);
        for (Actor a:actors){
            if(a.getId() == p){
                actors.remove(a);
            }
        }
    }

    public Actor getActorExists(Integer id) {
        Actor act = new Actor();
        for (Actor a:actors){
            if(a.getId() == id){
                act = a;
            }
        }
        return act;
    }

    //public static void main(String [] args) throws ClassNotFoundException{
    //    DataActors.getInstance().getMovie_Actor(11);
   // }

}

