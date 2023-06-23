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

    public String getNombreMedico() {
        return medico.getNombre();
    }

    public String getNombrePaciente() {
        return paciente.getNombre();
    }

    public long getTurnos() {
        return turno.getId();
    }

    public void setFechaDesde(LocalDateTime fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public void setFechaHasta(LocalDateTime fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    @Override
    public String toString() {
        return "Reporte{" +
                ", medico=" + medico.getNombre() +
                ", paciente=" + paciente.getNombre() +
                ", tarifa=" + turno.getTarifa(medico,paciente) +
                ", fechaDesde=" + fechaDesde +
                ", fechaHasta=" + fechaHasta +
                '}';
    }
}
