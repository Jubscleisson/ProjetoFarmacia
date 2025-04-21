
public class Gerente extends Caixa implements Descontavel {
    
    public Gerente() {
        super();
    }
    
    public Gerente(Float salario) {
        super();
        this.setSalario(salario);
    }
    
    public Gerente(String nome, String matricula, Float salario) {
        super();
        this.setId(1);
        this.setNome(nome);
        this.setMatricula(matricula);
        this.setSalario(salario);
    }
    
    public void cancelarCompra() {
        System.out.println("Compra cancelada");
    }

    @Override
    public String toString() {
        return "Nome: " + this.getNome();
    }

    @Override
    public Double aplicarDesconto(Double percentualDesconto) {
        if (percentualDesconto < 0 || percentualDesconto > 100) {
            throw new IllegalArgumentException("Percentual de desconto inválido");
        }
        return percentualDesconto;
    }
}
