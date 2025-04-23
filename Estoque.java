import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Estoque {
    private List<Medicamento> medicamentosDisponiveis;
    private Scanner scanner;

    public Estoque() {
        this.medicamentosDisponiveis = new ArrayList<>();
        inicializarMedicamentos();
        this.scanner = new Scanner(System.in);
    }

    private void inicializarMedicamentos() {
        medicamentosDisponiveis.add(new Medicamento("010", "Dipirona", 33, "Genérico", 5.00, 10.0)); // 10% de desconto
        medicamentosDisponiveis.add(new Medicamento("011", "Buscopan", 29, "Sanofi", 15.00, 5.0)); // 5% de desconto
        medicamentosDisponiveis.add(new Medicamento("012", "Resfenol", 56, "Hertz", 20.19, 0.0)); // Sem desconto
        medicamentosDisponiveis.add(new Medicamento("013", "Paracetamol", 48, "Medley", 12.45, 0.0)); // Sem desconto
        medicamentosDisponiveis.add(new Medicamento("014", "Neosaldina", 25, "Takeda", 18.50, 15.0)); // 15% de desconto
        medicamentosDisponiveis.add(new Medicamento("015", "Dorflex", 40, "Sanofi", 16.30, 0.0)); // Sem desconto
        medicamentosDisponiveis.add(new Medicamento("016", "Amoxicilina", 60, "EMS", 22.00, 10.0)); // 10% de desconto
        medicamentosDisponiveis.add(new Medicamento("017", "Azitromicina", 35, "Eurofarma", 28.90, 0.0)); // Sem desconto
        medicamentosDisponiveis.add(new Medicamento("018", "Ibuprofeno", 52, "Genérico", 10.50, 5.0)); // 5% de desconto
        medicamentosDisponiveis.add(new Medicamento("019", "Ranitidina", 20, "Medquímica", 9.80, 0.0)); // Sem desconto
        medicamentosDisponiveis.add(new Medicamento("020", "Omeprazol", 70, "Teuto", 14.99, 0.0)); // Sem desconto
        medicamentosDisponiveis.add(new Medicamento("021", "Losartana", 45, "Prati-Donaduzzi", 17.75, 20.0)); // 20% de desconto
        medicamentosDisponiveis.add(new Medicamento("022", "Simeticona", 50, "Neo Química", 8.25, 0.0)); // Sem desconto
        medicamentosDisponiveis.add(new Medicamento("023", "Hidroxicloroquina", 18, "Apsen", 32.40, 0.0)); // Sem desconto
        medicamentosDisponiveis.add(new Medicamento("024", "Cloroquina", 22, "EMS", 30.00, 0.0)); // Sem desconto
        medicamentosDisponiveis.add(new Medicamento("025", "Cimegripe", 39, "Cimed", 19.90, 0.0)); // Sem desconto
        medicamentosDisponiveis.add(new Medicamento("026", "Loratadina", 66, "Medley", 11.50, 0.0)); // Sem desconto
        medicamentosDisponiveis.add(new Medicamento("027", "Cetirizina", 30, "Teuto", 13.70, 0.0)); // Sem desconto
        medicamentosDisponiveis.add(new Medicamento("028", "Fluconazol", 25, "Eurofarma", 21.20, 0.0)); // Sem desconto
        medicamentosDisponiveis.add(new Medicamento("029", "Prednisona", 41, "Genérico", 7.65, 0.0)); // Sem desconto
    }
    
    public List<Medicamento> getMedicamentosDisponiveis() {
        return medicamentosDisponiveis;
    }    

    public void consultarInventario() {
        System.out.println("\n=== INVENTÁRIO ATUAL ===");
        for (Medicamento med : medicamentosDisponiveis) {
            System.out.printf("%s - %s: %d unidades (R$ %.2f)\n",
                med.getId(), med.getNome(), med.getQuantidade(), med.getValor());
        }
    }

    public void alterarEstoque(boolean aumentar) {
        exibirMedicamentosDisponiveis();
        System.out.print("Digite o ID do medicamento: ");
        String id = scanner.nextLine();

        Medicamento med = encontrarMedicamentoPorId(id);
        if (med == null) {
            System.out.println("Medicamento não encontrado!");
            return;
        }

        System.out.print("Digite a quantidade: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        try {
            if (aumentar) {
                med.aumentarEstoque(quantidade);
                System.out.println("Estoque aumentado com sucesso!");
            } else {
                med.reduzirEstoque(quantidade);
                System.out.println("Estoque reduzido com sucesso!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void exibirMedicamentosDisponiveis() {
        System.out.println("\nMedicamentos disponíveis:");
        for (Medicamento med : medicamentosDisponiveis) {
            System.out.printf("%s - %s (R$ %.2f)\n", med.getId(), med.getNome(), med.getValor());
        }
    }

    private Medicamento encontrarMedicamentoPorId(String id) {
        return medicamentosDisponiveis.stream()
            .filter(m -> m.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
}