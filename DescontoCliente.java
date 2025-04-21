
public class DescontoCliente implements Descontavel {
    private Cliente cliente;
    
    public DescontoCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    @Override
    public Double aplicarDesconto(Double percentualDesconto) {
        return cliente.getCpf() != null ? 5.0 : 0.0;
    }
}
