package model;

import java.util.Date;

public class Idoso {
    private int id;
    private String nome;
    private String telefone;
    private String cep;
    private String cpf;
    private String numeroCasa;
    private String condicoes;
    private Date dataNascimento;

    public Idoso(String nome, String telefone, String cep, String cpf, String numeroCasa, String condicoes, Date dataNascimento) {
        this.nome = nome;
        this.telefone = telefone;
        this.cep = cep;
        this.cpf = cpf;
        this.numeroCasa = numeroCasa;
        this.condicoes = condicoes;
        this.dataNascimento = dataNascimento;
    }

    public Idoso(int id, String nome, String telefone, String cep, String cpf, String numeroCasa, String condicoes, Date dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.cep = cep;
        this.cpf = cpf;
        this.numeroCasa = numeroCasa;
        this.condicoes = condicoes;
        this.dataNascimento = dataNascimento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getCondicoes() {
        return condicoes;
    }

    public void setCondicoes(String condicoes) {
        this.condicoes = condicoes;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return "Idoso{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", cep='" + cep + '\'' +
                ", cpf='" + cpf + '\'' +
                ", numeroCasa='" + numeroCasa + '\'' +
                ", condicoes='" + condicoes + '\'' +
                ", dataNascimento=" + dataNascimento +
                '}';
    }
}
