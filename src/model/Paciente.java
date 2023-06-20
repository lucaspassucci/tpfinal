package model;

public class Paciente {
    private long id;
    private String nombre;

    public Paciente(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Paciente() {

    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
