import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Venda {
    private int vendaId;
    private List<Medicamento> itensVendidos;
    private double valorTotal;
    private LocalDateTime dataHora;
    private Funcionario funcionario;
    private Cliente cliente;
    private static final Scanner scanner = new Scanner(System.in);

    public Venda(int vendaId, List<Medicamento> itensVendidos, double valorTotal,
        LocalDateTime dataHora, Funcionario funcionario, Cliente cliente) {
        this.vendaId = vendaId;
        this.itensVendidos = itensVendidos;
        this.valorTotal = valorTotal;
        this.dataHora = dataHora;
        this.funcionario = funcionario;
        this.cliente = cliente;
    }

    public void realizarVenda(Map<Integer, Caixa> caixasPorId, Map<String, Cliente> clientesPorCpf, List<Medicamento> medicamentosDisponiveis) {
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

        Cliente cliente = null;

        if (cpf != null && !cpf.isEmpty()) {
            cliente = clientesPorCpf.get(cpf);
        }

        if (cliente == null) {
            if (cpf != null && !cpf.isEmpty()) {
                System.out.println("Cliente não cadastrado. Criando novo cliente...");
                System.out.print("Nome do Cliente: ");
                String nome = scanner.nextLine();
                cliente = new Cliente(nome);
                cliente.setCpf(cpf);
                clientesPorCpf.put(cpf, cliente);
            } else {
                System.out.println("Prosseguindo sem cliente...");
            }
        } else {
            System.out.println("Cliente cadastrado: " + cliente.getNome());
        }

        this.cliente = cliente;

        List<Medicamento> itensVenda = selecionarMedicamentos(medicamentosDisponiveis);
        if (itensVenda.isEmpty()) {
            System.out.println("Venda cancelada: nenhum item selecionado.");
            return;
        }

        Map<Medicamento, Integer> quantidades = new HashMap<>();
        for (Medicamento med : itensVenda) {
            quantidades.merge(med, 1, Integer::sum);
        }

        double valorTotal = 0.0;

        System.out.println("\n=== Detalhes da Venda ===");
        for (Map.Entry<Medicamento, Integer> entry : quantidades.entrySet()) {
            Medicamento med = entry.getKey();
            int qtd = entry.getValue();
            double valorUnitario = med.getValor();
            double subtotal = valorUnitario * qtd;
            double valorComDesconto = med.calcularValorComDesconto();
            double totalComDesconto = valorComDesconto * qtd;

            if (med.getDesconto() > 0) {
                System.out.printf("%dx %s - Valor unitário: R$ %.2f - Subtotal: R$ %.2f - Desconto: %.1f%% - Subtotal com desconto: R$ %.2f\n",
                    qtd, med.getNome(), valorUnitario, subtotal, med.getDesconto(), totalComDesconto);
            } else {
                System.out.printf("%dx %s - Valor unitário: R$ %.2f - Subtotal: R$ %.2f\n",
                    qtd, med.getNome(), valorUnitario, subtotal);
            }

            valorTotal += totalComDesconto;
        }

        System.out.printf("\nTotal da venda: R$ %.2f\n", valorTotal);

        if (cliente != null && cliente.getCpf() != null) {
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
        
        escolherFormaPagamento(valorTotal);

        this.itensVendidos = itensVenda;
        this.valorTotal = valorTotal;
        System.out.println("\nVenda realizada com sucesso!");
        System.out.println(this);
    }

    public List<Medicamento> selecionarMedicamentos(List<Medicamento> medicamentosDisponiveis) {
        List<Medicamento> itensSelecionados = new ArrayList<>();
        while (true) {
            exibirMedicamentosDisponiveis(medicamentosDisponiveis);
            System.out.println("\n1. Adicionar medicamento");
            System.out.println("2. Remover medicamento");
            System.out.println("3. Ver carrinho");
            System.out.println("0. Finalizar seleção");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "0":
                    return itensSelecionados;
                case "1":
                    System.out.print("Digite o ID do medicamento: ");
                    String idAdicionar = scanner.nextLine();
                    Medicamento medAdicionar = encontrarMedicamentoPorId(idAdicionar, medicamentosDisponiveis);
                    if (medAdicionar != null) {
                        System.out.print("Digite a quantidade: ");
                        int quantidadeAdicionar = scanner.nextInt();
                        scanner.nextLine();
                        try {
                            if (quantidadeAdicionar <= medAdicionar.getQuantidade()) {
                                for (int i = 0; i < quantidadeAdicionar; i++) {
                                    itensSelecionados.add(medAdicionar);
                                }
                                System.out.printf("%dx %s adicionado à venda.\n", quantidadeAdicionar, medAdicionar.getNome());
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
                    System.out.print("Digite o ID do medicamento a ser removido: ");
                    String idRemover = scanner.nextLine();
                    Medicamento medRemover = encontrarMedicamentoPorId(idRemover, medicamentosDisponiveis);
                    if (medRemover != null) {
                        System.out.print("Digite a quantidade a ser removida (pressione Enter para remover todas): ");
                        String quantidadeInput = scanner.nextLine();
                        int quantidadeRemover = 0;

                        if (!quantidadeInput.isEmpty()) {
                            try {
                                quantidadeRemover = Integer.parseInt(quantidadeInput);
                            } catch (NumberFormatException e) {
                                System.out.println("Quantidade inválida!");
                                continue;
                            }
                        }

                        if (itensSelecionados.contains(medRemover)) {
                            if (quantidadeRemover > 0) {
                                for (int i = 0; i < quantidadeRemover; i++) {
                                    if (!itensSelecionados.remove(medRemover)) {
                                        System.out.println("Não há unidades suficientes no carrinho para remover.");
                                        break;
                                    }
                                }
                                System.out.printf("%d unidade(s) de %s removida(s) do carrinho.\n", quantidadeRemover, medRemover.getNome());
                            } else {
                                itensSelecionados.removeIf(m -> m.equals(medRemover));
                                System.out.printf("Todas as unidades de %s removidas do carrinho.\n", medRemover.getNome());
                            }
                        } else {
                            System.out.println("Medicamento não encontrado no carrinho.");
                        }
                    } else {
                        System.out.println("Medicamento não encontrado!");
                    }
                    break;
                case "3":
                    exibirCarrinhoAtual(itensSelecionados);
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    public void exibirCarrinhoAtual(List<Medicamento> itens) {
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

    public void exibirMedicamentosDisponiveis(List<Medicamento> medicamentosDisponiveis) {
        System.out.println("\nMedicamentos disponíveis:");
        for (Medicamento med : medicamentosDisponiveis) {
            System.out.printf("%s - %s (R$ %.2f)\n", med.getId(), med.getNome(), med.getValor());
        }
    }

    public Medicamento encontrarMedicamentoPorId(String id, List<Medicamento> medicamentosDisponiveis) {
        return medicamentosDisponiveis.stream()
            .filter(m -> m.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    private void escolherFormaPagamento(double valorTotal) {
        System.out.println("\n=== Escolha a Forma de Pagamento ===");
        System.out.println("1. Dinheiro");
        System.out.println("2. Cartão de Crédito");
        System.out.println("3. Cartão de Débito");
        System.out.println("4. PIX");
        System.out.print("Escolha uma opção: ");

        int opcaoPagamento = scanner.nextInt();
        scanner.nextLine();

        switch (opcaoPagamento) {
            case 1:
                System.out.print("Digite a quantia dada pelo cliente: R$ ");
                double quantiaDada = scanner.nextDouble();
                scanner.nextLine();

                if (quantiaDada < valorTotal) {
                    System.out.println("Quantia dada é insuficiente para cobrir o total da venda.");
                } else {
                    double troco = quantiaDada - valorTotal;
                    System.out.printf("Troco a ser devolvido: R$ %.2f\n", troco);
                }
                break;
            case 2:
                System.out.println("Pagamento em Cartão de Crédito selecionado.");
                break;
            case 3:
                System.out.println("Pagamento em Cartão de Débito selecionado.");
                break;
            case 4:
                System.out.println("Pagamento via PIX selecionado.");
                break;
            default:
                System.out.println("Opção inválida! Pagamento não realizado.");
                break;
        }
    }
    
    public double calcularTotal(List<Medicamento> itens) {
        return itens.stream()
            .mapToDouble(Medicamento::calcularValorComDesconto)
            .sum();
    }

    public double aplicarDesconto(double valor, double percentualDesconto) {
        return valor * (1 - percentualDesconto / 100);
    }

    @Override
    public String toString() {
        return String.format("Venda #%d - Total: R$%.2f - Cliente: %s - Data: %s",
            vendaId, valorTotal, cliente != null ? cliente.getNome() : "Não cadastrado", dataHora);
    }
}