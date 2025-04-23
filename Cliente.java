public class Cliente {
    private static int contadorId = 1;
    private String nome;
    private int id;
    private String cpf;
    private Carrinho carrinho;
    
    public Cliente(String nome) {
        this.nome = nome;
        this.id = contadorId++;
        this.cpf = cpf;
        this.carrinho = new Carrinho(this);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null) {
            throw new IllegalArgumentException("CPF não pode ser nulo");
        }
        
        String cpfNumerico = cpf.replaceAll("[^0-9]", "");
        
        if (cpfNumerico.length() != 11) {
            throw new IllegalArgumentException("CPF deve ter 11 dígitos");
        }
        
        this.cpf = cpfNumerico;
    }
}