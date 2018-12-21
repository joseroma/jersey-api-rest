package es.uma.ingenieria_web.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class ServiceMySQL {


    // assume that conn is an already created JDBC connection (see previous examples)
    public static void getMovies() {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {

            conn =
                    DriverManager.getConnection("jdbc:mysql://localhost/filmAffinity3?" +
                            "user=root&password=nairapass");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT nombre FROM ACTOR");

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            if (stmt.execute("SELECT nombre FROM ACTOR")) {
                rs = stmt.getResultSet();

            }

            while (rs.next())  {
                //COMO CREAR AQUI CLASE

                System.out.println(rs.getString(1));

            }

            // Now do something with the ResultSet ....
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
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

//    public static void main(String [] args) throws ClassNotFoundException{
//        getMovies();
//    }
}