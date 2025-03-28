public class Cliente {
    private static int contadorId = 1;
    private String nome;
    private int id;
    private Carrinho carrinho;
    
    public Cliente(String nome) {
        this.nome = nome;
        this.id = contadorId++;
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
    

}