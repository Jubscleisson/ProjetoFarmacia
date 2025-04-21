
public class Medicamento implements Descontavel{
   private String id;
   private String nome;
   private int quantidade;
   private String laboratorio;
   private Double valor;

    public Medicamento(String id, String nome, int quantidade, String laboratorio, Double valor) {
        this.id             = id;
        this.nome           = nome;
        this.quantidade     = quantidade;
        this.laboratorio    = laboratorio;
        this.valor          = valor;
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

    public void reduzirEstoque(int quantidade) {
        if (this.quantidade >= quantidade) {
            this.quantidade -= quantidade;
        } else {
            throw new IllegalArgumentException("Estoque insuficiente");
        }
    }

    public void aumentarEstoque(int quantidade) {
        if (quantidade > 0) {
            this.quantidade += quantidade;
        } else {
            throw new IllegalArgumentException("Quantidade inválida");
        }
    }
    
    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public Double aplicarDesconto(Double percentualDesconto) {
        Double valorDesconto = (this.valor * percentualDesconto) / 100;
        return this.valor - valorDesconto;
    }
    
    @Override
    public String toString() {
        return String.format("Medicamento: Nome=%s, Laboratório=%s, Valor=R$%.2f",
            id, nome, quantidade,laboratorio, valor);
    }



}
