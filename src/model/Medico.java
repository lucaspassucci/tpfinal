package model;
public class Medico {
    private long id;
    private String nombre;
    private double tarifaConsulta;

    public Medico() {
        // Constructor sin argumentos
    }

    public Medico(long id, String nombre, double tarifaConsulta) {
        this.id = id;
        this.nombre = nombre;
        this.tarifaConsulta = tarifaConsulta;
    }
    public Medico(String nombre, double tarifaConsulta) {
        this.nombre = nombre;
        this.tarifaConsulta = tarifaConsulta;
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

    public double getTarifaConsulta() {
        return tarifaConsulta;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTarifaConsulta(double tarifaConsulta) {
        this.tarifaConsulta = tarifaConsulta;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tarifaConsulta=" + tarifaConsulta +
                '}';
    }
}
