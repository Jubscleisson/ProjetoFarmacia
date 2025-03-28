import java.util.*;

public class Carrinho {
    private Cliente cliente;
    private List<Medicamento> itens;
    
    public Carrinho(Cliente cliente) {
        this.cliente = cliente;
        this.itens = new ArrayList<>();
    }
    
    public void adicionarItem(Medicamento medicamento) {
        itens.add(medicamento);
        System.out.println(medicamento.getNome() + " adicionado ao carrinho.");
    }
    
    public void removerItem(Medicamento medicamento) {
        if (itens.remove(medicamento)) {
            System.out.println(medicamento.getNome() + " removido do carrinho.");
        } else {
            System.out.println("Medicamento não encontrado no carrinho.");
        }
    }
    public void comprar(Gerente gerente) {
        if (itens.isEmpty()) {
            System.out.println("O carrinho está vazio!");
            return;
        }
        
        double total = 0;
        System.out.println("Compra realizada por " + cliente.getNome() + ":");
        for (Medicamento medicamento : itens) {
            System.out.println(" - " + medicamento.getNome() + " | R$ " + medicamento.getValor());
            total += medicamento.getValor();
        }
            System.out.println("Total: " + total);
        
    }
}
