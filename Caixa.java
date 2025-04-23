public class Caixa extends Funcionario {
    
    public Cliente cadastrarCliente(String nome, String cpf) {
        Cliente cliente = new Cliente(nome);
        cliente.setCpf(cpf);
        return cliente;
    }
}