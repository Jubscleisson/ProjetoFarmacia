
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<String, Cliente> clientesPorCpf = new HashMap<>();
    private static final Map<Integer, Caixa> caixasPorId = new HashMap<>();
    private static final Gerente gerente = new Gerente("Pato Donald", "1", 5000f);
    static {
        caixasPorId.put(gerente.getId(), gerente);
    }
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Medicamento> medicamentosDisponiveis = new ArrayList<>();
    
    public static void main(String[] args) {
        inicializarMedicamentos();
        
        while (true) {
            System.out.println("\n=== SISTEMA DE FARMÁCIA ===");
            System.out.println("1. Cadastrar Caixa");
            System.out.println("2. Cadastrar Cliente");
            System.out.println("3. Realizar Venda");
            System.out.println("4. Menu Administrativo");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    cadastrarCaixa();
                    break;
                case 2:
                    cadastrarCliente();
                    break;
                case 3:
                    realizarVenda();
                    break;
                case 4:
                    menuAdmin();
                    break;
                case 5:
                    System.out.println("Encerrando sistema...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
    
    private static void cadastrarCaixa() {
        System.out.print("Nome do Caixa: ");
        String nome = scanner.nextLine();
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();
        System.out.print("Salário: ");
        float salario = scanner.nextFloat();
        
        Caixa caixa = new Caixa();
        caixa.setNome(nome);
        caixa.setMatricula(matricula);
        caixa.setSalario(salario);
        
        caixasPorId.put(caixa.getId(), caixa);
        System.out.println("Caixa cadastrado com ID: " + caixa.getId());
    }
    
    private static void cadastrarCliente() {
        System.out.print("Nome do Cliente: ");
        String nome = scanner.nextLine();
        System.out.print("CPF (XXX.XXX.XXX-XX): ");
        String cpf = scanner.nextLine();
        
        Cliente cliente = new Cliente(nome);
        try {
            cliente.setCpf(cpf);
            clientesPorCpf.put(cpf, cliente);
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    private static void realizarVenda() {
        if (caixasPorId.isEmpty()) {
            System.out.println("Erro: Nenhum caixa cadastrado!");
            return;
        }
        
        System.out.print("ID do Caixa: ");
        int idCaixa = scanner.nextInt();
        scanner.nextLine();
        
        Caixa caixa = caixasPorId.get(idCaixa);
        if (caixa == null) {
            System.out.println("Erro: Caixa não encontrado!");
            return;
        }
        
        System.out.print("CPF do Cliente: ");
        String cpf = scanner.nextLine();
        
        Cliente cliente = clientesPorCpf.get(cpf);
        if (cliente == null) {
            System.out.println("Cliente não cadastrado. Criando novo cliente sem desconto...");
            System.out.print("Nome do Cliente: ");
            String nome = scanner.nextLine();
            cliente = new Cliente(nome);
        }
        
        List<Medicamento> itensVenda = selecionarMedicamentos();
        if (itensVenda.isEmpty()) {
            System.out.println("Venda cancelada: nenhum item selecionado.");
            return;
        }
        
        double valorTotal = calcularTotal(itensVenda);
        
        if (cliente.getCpf() != null) {
            valorTotal = aplicarDesconto(valorTotal, 4.0);
            System.out.println("Desconto de 4% aplicado para cliente cadastrado!");
        }

        System.out.println("\nDeseja aplicar desconto gerencial? (s/N)");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            System.out.print("ID do Gerente: ");
            String idGerente = scanner.nextLine();
            System.out.print("Senha: ");
            String senhaGerente = scanner.nextLine();

            if (idGerente.equals("1") && senhaGerente.equals("bolinhascofcof")) {
                System.out.print("Digite o percentual de desconto (0-100): ");
                double percentualDesconto = scanner.nextDouble();
                scanner.nextLine();

                if (percentualDesconto == 0) {
                    System.out.println("Aviso: Nenhum desconto será aplicado.");
                } else if (percentualDesconto == 100) {
                    System.out.println("Aviso: Não haverá lucro nesta venda!");
                }

                if (percentualDesconto >= 0 && percentualDesconto <= 100) {
                    valorTotal = aplicarDesconto(valorTotal, percentualDesconto);
                    System.out.printf("Desconto de %.1f%% aplicado pelo gerente!\n", percentualDesconto);
                } else {
                    System.out.println("Percentual de desconto inválido!");
                }
            } else {
                System.out.println("Credenciais inválidas!");
            }
        }
        
        Venda venda = new Venda(1, itensVenda, valorTotal, LocalDateTime.now(), caixa, cliente);
        System.out.println("\nVenda realizada com sucesso!");
        System.out.println(venda);
    }
    
    private static List<Medicamento> selecionarMedicamentos() {
        List<Medicamento> itensSelecionados = new ArrayList<>();
        while (true) {
            exibirMedicamentosDisponiveis();
            System.out.println("\n1. Adicionar medicamento");
            System.out.println("2. Ver carrinho");
            System.out.println("0. Finalizar seleção");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();
            
            switch (opcao) {
                case "0":
                    return itensSelecionados;
                case "1":
                    System.out.print("Digite o ID do medicamento: ");
                    String id = scanner.nextLine();
                    Medicamento med = encontrarMedicamentoPorId(id);
                    if (med != null) {
                        System.out.print("Digite a quantidade: ");
                        int quantidade = scanner.nextInt();
                        scanner.nextLine();
                        try {
                            if (quantidade <= med.getQuantidade()) {
                                for (int i = 0; i < quantidade; i++) {
                                    itensSelecionados.add(med);
                                }
                                System.out.printf("%dx %s adicionado à venda.\n", quantidade, med.getNome());
                            } else {
                                System.out.println("Quantidade indisponível no estoque!");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Quantidade inválida!");
                        }
                    } else {
                        System.out.println("Medicamento não encontrado!");
                    }
                    break;
                case "2":
                    exibirCarrinhoAtual(itensSelecionados);
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void exibirCarrinhoAtual(List<Medicamento> itens) {
        if (itens.isEmpty()) {
            System.out.println("\nCarrinho vazio!");
            return;
        }
        
        System.out.println("\n=== Carrinho Atual ===");
        Map<Medicamento, Integer> quantidades = new HashMap<>();
        for (Medicamento med : itens) {
            quantidades.merge(med, 1, Integer::sum);
        }
        
        double total = 0;
        for (Map.Entry<Medicamento, Integer> entry : quantidades.entrySet()) {
            Medicamento med = entry.getKey();
            int qtd = entry.getValue();
            double subtotal = med.getValor() * qtd;
            System.out.printf("%dx %s - R$ %.2f (un.) = R$ %.2f\n", 
                qtd, med.getNome(), med.getValor(), subtotal);
            total += subtotal;
        }
        System.out.printf("\nTotal: R$ %.2f\n", total);
    }
    
    private static void inicializarMedicamentos() {
        medicamentosDisponiveis.add(new Medicamento("010", "Dipirona", 13, "genérico", 5.00));
        medicamentosDisponiveis.add(new Medicamento("011", "Buscopam", 29, "Eurofarma", 15.00));
        medicamentosDisponiveis.add(new Medicamento("012", "Resfenol", 56, "Hertz", 20.19));
        medicamentosDisponiveis.add(new Medicamento("013", "Paracetamol", 48, "Medley", 12.45));
    }
    
    private static void exibirMedicamentosDisponiveis() {
        System.out.println("\nMedicamentos disponíveis:");
        for (Medicamento med : medicamentosDisponiveis) {
            System.out.printf("%s - %s (R$ %.2f)\n", med.getId(), med.getNome(), med.getValor());
        }
    }
    
    private static Medicamento encontrarMedicamentoPorId(String id) {
        return medicamentosDisponiveis.stream()
            .filter(m -> m.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    private static double calcularTotal(List<Medicamento> itens) {
        return itens.stream()
            .mapToDouble(Medicamento::getValor)
            .sum();
    }
    
    private static double aplicarDesconto(double valor, double percentualDesconto) {
        return valor * (1 - percentualDesconto / 100);
    }

    private static void menuAdmin() {
        System.out.print("ID do Gerente: ");
        String id = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (!id.equals("1") || !senha.equals("bolinhascofcof")) {
            System.out.println("Credenciais inválidas!");
            return;
        }

        while (true) {
            System.out.println("\n=== MENU ADMINISTRATIVO ===");
            System.out.println("1. Consultar Inventário");
            System.out.println("2. Adicionar ao Inventário");
            System.out.println("3. Reduzir Inventário");
            System.out.println("4. Voltar");
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    consultarInventario();
                    break;
                case 2:
                    alterarInventario(true);
                    break;
                case 3:
                    alterarInventario(false);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void consultarInventario() {
        System.out.println("\n=== INVENTÁRIO ATUAL ===");
        for (Medicamento med : medicamentosDisponiveis) {
            System.out.printf("%s - %s: %d unidades (R$ %.2f)\n", 
                med.getId(), med.getNome(), med.getQuantidade(), med.getValor());
        }
    }

    private static void alterarInventario(boolean aumentar) {
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
}
