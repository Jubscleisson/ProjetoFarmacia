
public class Medicamento implements Descontavel{
   private String nome;
   private String laboratorio;
   private Double valor;

    public Medicamento(String nome, String laboratorio, Double valor) {
        this.nome           = nome;
        this.laboratorio    = laboratorio;
        this.valor          = valor;
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public Double aplicarDesconto(Double percentualDesconto) {
        Double valorDesconto = 
                (this.valor * percentualDesconto) / 100;
        
        Double valor = this.valor - valorDesconto;
        return valor;
    
    }



}
