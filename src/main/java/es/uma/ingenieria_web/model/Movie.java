package es.uma.ingenieria_web.model;

import java.awt.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Movie {

    //A LOS 3 MODELOS MÉTODO PARA COGER POR EL ID

    int oid;
    String titulo;
    String fecha_lanzamiento;
    String genero;
    //(CON QUERY A MYSQL)
    //ACTORES lista<Actor>
    //DIRECTOR  (STRING)
    //ponerle sus gets

    public Movie() {
        super();
    }

    public Movie(int oid, String titulo, String fecha_lanzamiento, String genero) {
        super();
        this.oid = oid;
        this.titulo = titulo;
        this.fecha_lanzamiento= fecha_lanzamiento;
        this.genero = genero;
    }

    public Movie(String titulo) {
        super();
        this.titulo = titulo;
    }

    public int getId() {return oid;}
    public void setId(int oid) {this.oid =oid;}

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}

    public String getGenero() {return genero;}
    public void setGenero(String genero) {this.genero = genero;}

    public String getFechaLanzamiento() {return fecha_lanzamiento;}
    public void setFechaLanzamiento(String fecha_lanzamiento) {this.fecha_lanzamiento = fecha_lanzamiento;}




    @Override
    public String toString() {
        //AÑADIR PELICULAS
        return "Director [oid=" + oid + ", Titulo=" + titulo + ", Fecha_Lanz=" + fecha_lanzamiento +
                ", Genero = " +genero+"] \n";
    }

}