package model;
public class Medico {
    private long id;
    private String nombre;
    private double tarifaConsulta;
    private String obraSocial;
    public Medico() {
        // Constructor sin argumentos
    }

    public Medico(long id, String nombre, double tarifaConsulta, String obraSocial) {
        this.id = id;
        this.nombre = nombre;
        this.tarifaConsulta = tarifaConsulta;
        this.obraSocial = obraSocial;
    }
    public Medico(String nombre, double tarifaConsulta, String obraSocial) {
        this.nombre = nombre;
        this.tarifaConsulta = tarifaConsulta;
        this.obraSocial=obraSocial;
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

    public String getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tarifaConsulta=" + tarifaConsulta + '\'' +
                ", obraSocial='" + obraSocial +
                '}';
    }

}
