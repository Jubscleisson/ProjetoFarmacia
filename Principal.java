
public class Principal {

    
    public static void main(String[] args) {
        Gerente f1;
        f1 = new Gerente("Pato Donald", "007", 5000f);
        Caixa f2;
        f2 = new Caixa();
        f2.setNome("Tio Patinhas");
        f2.setMatricula("0007");
        f2.setSalario(3000f);
        
        Medicamento m1,m2,m3;
        
        m1 = new Medicamento("Dipirona","genérico",5.00);
        m2 = new Medicamento("Buscopam","Eurofarma",15.00);
        m3 = new Medicamento("Resfenol","Hertz",20.19);
        
        Cliente cliente = new Cliente("Jonas");
        cliente.getCarrinho().adicionarItem(m3);
        cliente.getCarrinho().adicionarItem(m2);
        
        cliente.getCarrinho().comprar(f1);
        
/*        Double valorFinal = m1.getValor() + m2.getValor() + m3.getValor();
        System.out.println("Valor a pagar sem desconto : " + valorFinal);
        
        valorFinal = m1.getValor() + m2.getValor() + m3.aplicarDesconto(10.0);
        System.out.println("Valor a pagar com desconto "
                + "no resfenol: " + valorFinal); */

        
       
    }
    
}
