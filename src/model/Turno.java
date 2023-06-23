package model;
import java.time.LocalDateTime;

public class Turno {
    private long id;
    private Medico medico;
    private Paciente paciente;
    private LocalDateTime fechaHora;
    private double tarifa;

    public Turno(Medico medico, Paciente paciente, LocalDateTime fechaHora) {
        this.medico = medico;
        this.paciente = paciente;
        this.fechaHora = fechaHora;
    }

    public Turno() {
    }

    public long getId() {
        return id;
    }

    public long getIdMedico() {
        return medico.getId();
    }

    public long getIdPaciente() {
        return paciente.getId();
    }

    public void setId(long id) {
        this.id = id;
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

    public double getTarifa(Medico medico, Paciente paciente) {
        this.tarifa = medico.getTarifaConsulta();
        if((medico.getObraSocial()).equals(paciente.getObraSocial()))
        {
            tarifa= tarifa*0.5;
        }
        return tarifa;
    }



    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", medico=" + medico +
                ", paciente=" + paciente +
                ", fechaHora=" + fechaHora +
                ", tarifa=" + getTarifa(medico,paciente) +
                '}';
    }
}
