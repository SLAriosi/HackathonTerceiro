package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Agenda {
    private long id;
    private LocalDate data;
    private LocalTime horario;
    private long agenteSaudeId;
    private String vacina;
    private String nome;
    private String cpf;
    private String cep;
    private String telefone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Agenda(long id, LocalDate data, LocalTime horario, long agenteSaudeId, String vacina, String nome, String cpf, String cep, String telefone, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.agenteSaudeId = agenteSaudeId;
        this.vacina = vacina;
        this.nome = nome;
        this.cpf = cpf;
        this.cep = cep;
        this.telefone = telefone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public LocalTime getHorario() { return horario; }
    public void setHorario(LocalTime horario) { this.horario = horario; }

    public long getAgenteSaudeId() { return agenteSaudeId; }
    public void setAgenteSaudeId(long agenteSaudeId) { this.agenteSaudeId = agenteSaudeId; }

    public String getVacina() { return vacina; }
    public void setVacina(String vacina) { this.vacina = vacina; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
