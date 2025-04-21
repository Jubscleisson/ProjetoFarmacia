
import java.time.LocalDateTime;
import java.util.List;

public class Venda {
    private int vendaId;
    private List<Medicamento> itensVendidos;
    private double valorTotal;
    private LocalDateTime dataHora;
    private Funcionario funcionario;
    private Cliente cliente;

    public Venda(int vendaId, List<Medicamento> itensVendidos, double valorTotal, 
                 LocalDateTime dataHora, Funcionario funcionario, Cliente cliente) {
        this.vendaId = vendaId;
        this.itensVendidos = itensVendidos;
        this.valorTotal = valorTotal;
        this.dataHora = dataHora;
        this.funcionario = funcionario;
        this.cliente = cliente;
    }
    
    @Override
    public String toString() {
        return String.format("Venda #%d - Total: R$%.2f - Cliente: %s - Data: %s",
            vendaId, valorTotal, cliente.getNome(), dataHora);
    }
}
