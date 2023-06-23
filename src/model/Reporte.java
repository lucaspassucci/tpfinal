package model;

import java.time.LocalDateTime;

public class Reporte {
    private Medico medico;
    private Turno turno;
    private Paciente paciente;
    private LocalDateTime fechaDesde;
    private LocalDateTime fechaHasta;

    public Reporte(Medico medico, LocalDateTime fecha1, LocalDateTime fechaHasta) {
        this.medico = medico;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }

    public Reporte () {
    }

    public long getNombreMedico() {
        return medico.getNombre();
    }

    public long getNombrePaciente() {
        return paciente.getNombre();
    }

    public long getTurnos() {
        return turno.getId();
    }

    public void setFechaDesde(LocalDateTime fechaDesde) {
        this.fechaDesde = fechaDesde;
    }
    //falta traer tarifa

    @Override
    public String toString() {
        return "Reporte{" +
                "nombre=" + nombre +
                ", medico=" + medico +
                ", fechaDesde=" + fechaDesde +
                ", fechaHasta=" + fechaHasta +
                '}';
    }
}
