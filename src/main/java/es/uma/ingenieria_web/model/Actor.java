package es.uma.ingenieria_web.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Actor {

    int oid;
    String nombre;
    String apellido;
    String birth_date;

    public Actor() {
        super();
    }

    public Actor(int oid, String nombre, String apellido) {
        super();
        this.oid = oid;
        this.nombre = nombre;
        this.apellido = apellido; //?? quitar??
        //lista <Movie> (query)
    }



    public int getId() {return oid;}
    public void setId(int oid) {this.oid =oid;}

    public String getName() {return nombre;}
    public void setName(String name) {this.nombre = name;}

    //maybe npt
    public String getApellido() {return apellido;}
    public void setApellido(String apell) {this.apellido = apell;}

    @Override
    public String toString() {
        return "Actor [oid=" + oid + ", name=" + nombre + ", apellido=" + apellido + ", Fecha nacimiento= " + birth_date + "] \n";
    }

}