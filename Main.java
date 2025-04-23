import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<String, Cliente> clientesPorCpf = new HashMap<>();
    private static final Map<Integer, Caixa> caixasPorId = new HashMap<>();
    private static final Gerente gerente = new Gerente("Pato Donald", "1", 5000f);
    private static final Scanner scanner = new Scanner(System.in);
    private static int proximoIdVenda = 1;
    private static Estoque estoque;
    
    static {
        caixasPorId.put(gerente.getId(), gerente);
    }

    public static void main(String[] args) {
        estoque = new Estoque();

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

        Caixa caixa = gerente.cadastrarCaixa(nome, matricula, salario);
        caixasPorId.put(caixa.getId(), caixa);
        System.out.println("Caixa cadastrado com ID: " + caixa.getId());
    }

    private static void cadastrarCliente() {
        System.out.print("Nome do Cliente: ");
        String nome = scanner.nextLine();
        String cpf = scanner.nextLine();

        System.out.print("ID do Caixa: ");
        int idCaixa = scanner.nextInt();
        scanner.nextLine();

        Caixa caixa = caixasPorId.get(idCaixa);
        if (caixa == null) {
            System.out.println("Caixa não encontrado!");
            return;
        }

        Cliente cliente = caixa.cadastrarCliente(nome, cpf);
        clientesPorCpf.put(cpf, cliente);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void realizarVenda() {
        Venda venda = new Venda(proximoIdVenda++, null, 0, LocalDateTime.now(), null, null);
        venda.realizarVenda(caixasPorId, clientesPorCpf, estoque.getMedicamentosDisponiveis());
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
                    estoque.consultarInventario();
                    break;
                case 2:
                    estoque.alterarEstoque(true);
                    break;
                case 3:
                    estoque.alterarEstoque(false);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}