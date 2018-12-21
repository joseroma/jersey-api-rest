package es.uma.ingenieria_web.model;

import java.awt.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Director {

    int oid;
    String nombre;
    ArrayList<Movie> peliculas = new ArrayList<Movie>();
    Movie pelicula;

    public Director() {
        super();
    }

    public Director(int oid, String nombre) {
        super();
        this.oid = oid;
        this.nombre = nombre;
    }

    public int getId() {return oid;}
    public void setId(int oid) {this.oid =oid;}

    public String getName() {return nombre;}
    public void setName(String name) {this.nombre = name;}






    @Override
    public String toString() {
        //AÑADIR PELICULAS
        return "Director [oid=" + oid + ", name=" + nombre +  "] \n";
    }

}