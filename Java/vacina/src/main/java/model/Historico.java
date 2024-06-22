package model;

public class Historico {
    private long id;
    private long idosoId;
    private long agendaId;
    private long vacinaId;

    public Historico() {
        // Construtor padrão vazio
    }

    public Historico(long id, long idosoId, long agendaId, long vacinaId) {
        this.id = id;
        this.idosoId = idosoId;
        this.agendaId = agendaId;
        this.vacinaId = vacinaId;
    }

    // Getters e Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdosoId() {
        return idosoId;
    }

    public void setIdosoId(long idosoId) {
        this.idosoId = idosoId;
    }

    public long getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(long agendaId) {
        this.agendaId = agendaId;
    }

    public long getVacinaId() {
        return vacinaId;
    }

    public void setVacinaId(long vacinaId) {
        this.vacinaId = vacinaId;
    }

    // Método toString para representação textual do objeto
    @Override
    public String toString() {
        return "Historico{" +
                "id=" + id +
                ", idosoId=" + idosoId +
                ", agendaId=" + agendaId +
                ", vacinaId=" + vacinaId +
                '}';
    }
}
