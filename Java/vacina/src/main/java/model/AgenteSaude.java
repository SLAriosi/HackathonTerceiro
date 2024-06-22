package model;

public class AgenteSaude {
    private Long id;
    private String nome;
    private String username;
    private String password;

    public AgenteSaude(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // New constructor to include username and password
    public AgenteSaude(Long id, String nome, String username, String password) {
        this.id = id;
        this.nome = nome;
        this.username = username;
        this.password = password;
    }

    // Getters and setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
