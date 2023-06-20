package model;
import java.time.LocalDateTime;

public class Turno {
    private long id;
    private Medico medico;
    private Paciente paciente;
    private LocalDateTime fechaHora;

    public Turno(long id, Medico medico, Paciente paciente, LocalDateTime fechaHora) {
        this.id = id;
        this.medico = medico;
        this.paciente = paciente;
        this.fechaHora = fechaHora;
    }

    public long getId() {
        return id;
    }

    public Medico getMedico() {
        return medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", medico=" + medico +
                ", paciente=" + paciente +
                ", fechaHora=" + fechaHora +
                '}';
    }
}
