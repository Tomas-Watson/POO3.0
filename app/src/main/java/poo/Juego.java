package poo;

import javax.swing.ImageIcon;

public abstract class Juego {
    private String nombre;
    private String desarrolladores;
    private String version;
    private String descripcion;
    private boolean implementado;
    private ImageIcon imagenPortada;

    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setDesarrolladores(String desarrolladores){
        this.desarrolladores=desarrolladores;
    }

    public String getDesarrolladores(){
        return this.desarrolladores;
    }

    public void setVersion(String version){
        this.version=version;
    }

    public String getVersion(){
        return this.version;
    }

    public void setDescripcion(String descripcion){
        this.descripcion=descripcion;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public void setImplementado(boolean implementado){
        this.implementado=implementado;
    }

    public boolean isImplementado(){
        return this.implementado;
    }

    public void setImgenPortada(ImageIcon imagenPortada){
        this.imagenPortada = imagenPortada;
    }

    public ImageIcon getImagenPortada(){
        return this.imagenPortada;
    }

    public abstract void run();
}
