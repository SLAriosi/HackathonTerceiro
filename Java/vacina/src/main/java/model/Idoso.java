package model;

import java.util.Date;

public class Idoso {
    private int id; // Adicionando o campo id
    private String nome;
    private String telefone;
    private String cep;
    private String cpf;
    private String numero_casa;
    private String condicoes;
    private Date dataNascimento; // Campo dataNascimento

    // Construtor sem id (para inserção de novo idoso)
    public Idoso(String nome, String telefone, String cep, String cpf, String numero_casa, String condicoes, Date dataNascimento) {
        this.nome = nome;
        this.telefone = telefone;
        this.cep = cep;
        this.cpf = cpf;
        this.numero_casa = numero_casa;
        this.condicoes = condicoes;
        this.dataNascimento = dataNascimento;
    }

    // Construtor com id (para atualização)
    public Idoso(int id, String nome, String telefone, String cep, String cpf, String numero_casa, String condicoes, Date dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.cep = cep;
        this.cpf = cpf;
        this.numero_casa = numero_casa;
        this.condicoes = condicoes;
        this.dataNascimento = dataNascimento;
    }

    // Getters e Setters

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

    public String getNumero_casa() {
        return numero_casa;
    }

    public void setNumero_casa(String numero_casa) {
        this.numero_casa = numero_casa;
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
}
