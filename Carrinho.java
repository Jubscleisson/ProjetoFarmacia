
import java.util.*;

public class Carrinho {
    private Cliente cliente;
    private Map<Medicamento, Integer> itens;
    
    public Carrinho(Cliente cliente) {
        this.cliente = cliente;
        this.itens = new HashMap<>();
    }
    
    public void adicionarItem(Medicamento medicamento, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        if (quantidade > medicamento.getQuantidade()) {
            throw new IllegalArgumentException("Quantidade indisponível no estoque");
        }
        
        itens.merge(medicamento, quantidade, Integer::sum);
        System.out.println(quantidade + "x " + medicamento.getNome() + " adicionado ao carrinho.");
    }
    
    public void removerItem(Medicamento medicamento) {
        if (itens.remove(medicamento) != null) {
            System.out.println(medicamento.getNome() + " removido do carrinho.");
        } else {
            System.out.println("Medicamento não encontrado no carrinho.");
        }
    }

    public void exibirCarrinho() {
        if (itens.isEmpty()) {
            System.out.println("Carrinho vazio!");
            return;
        }
        
        System.out.println("\n=== Itens no Carrinho ===");
        double total = 0;
        for (Map.Entry<Medicamento, Integer> entry : itens.entrySet()) {
            Medicamento med = entry.getKey();
            int qtd = entry.getValue();
            double subtotal = med.getValor() * qtd;
            
            System.out.printf("%dx %s - R$ %.2f (un.) = R$ %.2f\n", 
                qtd, med.getNome(), med.getValor(), subtotal);
            total += subtotal;
        }
        System.out.printf("\nTotal: R$ %.2f\n", total);
    }

    public void comprar(Gerente gerente) {
        if (itens.isEmpty()) {
            System.out.println("O carrinho está vazio!");
            return;
        }
        
        double total = 0;
        List<Medicamento> itensVenda = new ArrayList<>();
        System.out.println("Compra realizada por " + cliente.getNome() + ":");
        
        for (Map.Entry<Medicamento, Integer> entry : itens.entrySet()) {
            Medicamento medicamento = entry.getKey();
            int quantidade = entry.getValue();
            
            try {
                medicamento.reduzirEstoque(quantidade);
                double valorUnitario = medicamento.getValor();
                double valorTotal = valorUnitario * quantidade;
                
                System.out.printf(" - %s (x%d)\n", medicamento.getNome(), quantidade);
                System.out.printf("   Valor unitário: R$ %.2f\n", valorUnitario);
                System.out.printf("   Subtotal: R$ %.2f\n", valorTotal);
                
                total += valorTotal;
                itensVenda.add(medicamento);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao processar " + medicamento.getNome() + ": " + e.getMessage());
                return;
            }
        }

        Double desconto = gerente.aplicarDesconto(10.0);
        if (desconto > 0) {
            double valorDesconto = (total * desconto) / 100;
            total -= valorDesconto;
            System.out.printf("Desconto aplicado: R$ %.2f\n", valorDesconto);
        }
            
        System.out.printf("Total final: R$ %.2f\n", total);
        
        Venda venda = new Venda(1, itensVenda, total, java.time.LocalDateTime.now(), gerente, cliente);
        itens.clear();
    }
}
