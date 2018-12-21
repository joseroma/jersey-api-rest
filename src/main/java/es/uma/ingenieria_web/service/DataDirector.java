package es.uma.ingenieria_web.service;

import es.uma.ingenieria_web.model.Actor;
import es.uma.ingenieria_web.model.Director;
import es.uma.ingenieria_web.model.Movie;
import es.uma.ingenieria_web.model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static sun.tools.java.Constants.NULL;


public class DataDirector {
    private List<Director> directors = new ArrayList<Director>();
    private HashMap<Movie, Director> director_movies = new HashMap<Movie, Director>();

    private static DataDirector instance = new DataDirector();

    private DataDirector() {

        Statement stmt = null;
        Statement stmt1 = null;
        ResultSet rs = null;
        ResultSet rs_movies = null;
        Connection conn = null;
        Connection conn1 = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            conn =
                    DriverManager.getConnection("jdbc:mysql://localhost/filmAffinity3?" +
                            "user=root&password=nairapass");

            conn1 =
                    DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/filmAffinity3" ,
                            "root", "nairapass");

            stmt = conn.createStatement();
            stmt1 = conn.createStatement();

            rs = stmt.executeQuery("SELECT * FROM DIRECTOR");
            rs_movies = stmt1.executeQuery("SELECT * FROM DIRECTOR a, PELICULA_has_DIRECTOR m, PELICULA p WHERE p.oid_pelicula = m.PELICULA_oid_pelicula AND a.oid_director = m.DIRECTOR_oid_director;");


            if (stmt.execute("SELECT * FROM DIRECTOR")) {
                rs = stmt.getResultSet();

            }

            if (stmt1.execute("SELECT * FROM DIRECTOR a, PELICULA_has_DIRECTOR m, PELICULA p WHERE p.oid_pelicula = m.PELICULA_oid_pelicula AND a.oid_director = m.DIRECTOR_oid_director;")) {
                rs_movies = stmt1.getResultSet();
            }


            while (rs.next())  {

                ResultSetMetaData rsmd = rs.getMetaData();
                Integer id_ = rs.getInt(1);
                String nom = rs.getString(2);
                //ArrayList<Movie> pelis_dir= new ArrayList<Movie>();
                //for (int i=3; i<rsmd.getColumnCount();i++){
                    //Aqui tendrian que ir las peliculas que tiene un director
                    //PREGUNTAR NAIRA
                //    pelis_dir.add(new Movie(rs.getString(i)));

                //}
                directors.add(new Director(id_, nom));
            }

            while (rs_movies.next()) {

                    ResultSetMetaData rsmd = rs_movies.getMetaData();
                    Integer id_mov = rs_movies.getInt(8);
                    String tit_mov = rs_movies.getString(9);
                    String fec_mov = rs_movies.getString(11);
                    String gen_mov = rs_movies.getString(12);
                    Integer id_dir = rs_movies.getInt(1);
                    String nom_dir = rs_movies.getString(2);
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

    public static DataDirector getInstance() {
        return instance;
    }
    /*
       Devuelve todos los directores
    */
    public List<Director> getDirectors() {
        System.out.println("LENGTH:"+directors.size());
        return directors;
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

    /*
    Devuelve todas las peliculas en las que ha participado Actor_id
     */

    public List<Movie> getMovie_Director(final int Actor_id) {
        List<Movie> actor_mov = new ArrayList<Movie>();
        if (director_movies.size()!=NULL) {
            actor_mov = director_movies.entrySet().stream().filter(x->x.getValue().getId()==Actor_id).map(x -> x.getKey()).collect(Collectors.toList());
            System.out.println( actor_mov);
            System.out.println("LENGTH:" + actor_mov.size());

        }else{
            System.out.println("Tiene que dar valor al ID para buscar por ID");

        }
        return actor_mov;
    }


    public void addDirector(Director p) {
        System.out.println("adding "+p);
        directors.add(p);
    }

    public void deleteDirector(Integer id) {
        System.out.println("adding "+id);
        for (Director a:directors){
            if(a.getId() == id){
                directors.remove(a);
            }
        }

    }



    public Director getDirectorExists(Integer id) {
        Director act = new Director();
        for (Director a:directors){
            if(a.getId() == id){
                act = a;
            }
        }
        return act;
    }


    //   public static void main(String [] args) throws ClassNotFoundException{
 //       DataDirector.getInstance().getDirectors();
 //   }

}

