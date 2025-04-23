
public class Medicamento implements Descontavel{
   private String id;
   private String nome;
   private int quantidade;
   private String laboratorio;
   private Double valor;
   private Double desconto;

    public Medicamento(String id, String nome, int quantidade, String laboratorio, Double valor, Double desconto) {
        this.id             = id;
        this.nome           = nome;
        this.quantidade     = quantidade;
        this.laboratorio    = laboratorio;
        this.valor          = valor;
        this.desconto       = desconto;
    }

    public String getId(){
        return id;
    }
    public int getQuantidade() {
        return quantidade;
    }
    
    public String getNome() {
        return nome;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public Double getValor() {
        return valor;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade >= 0) {
            this.quantidade = quantidade;
        } else {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }
    

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    public void aumentarEstoque(int quantidade) {
        if (quantidade > 0) {
            this.quantidade += quantidade;
        } else {
            throw new IllegalArgumentException("Quantidade inválida");
        }
    }

    public void reduzirEstoque(int quantidade) {
        if (this.quantidade >= quantidade) {
            this.quantidade -= quantidade;
        } else {
            throw new IllegalArgumentException("Estoque insuficiente");
        }
    }

    public Double calcularValorComDesconto() {
        if (desconto != 0) {
            return aplicarDesconto(desconto);
        }
        return valor;
    }

    @Override
    public Double aplicarDesconto(Double percentualDesconto) {
        Double valorDesconto = (this.valor * percentualDesconto) / 100;
        return this.valor - valorDesconto;
    }

    @Override
    public String toString() {
        return String.format("Medicamento: Nome=%s, Laboratório=%s, Valor=R$%.2f, Desconto=%.1f%%",
            nome, laboratorio, valor, desconto);
    }
}
