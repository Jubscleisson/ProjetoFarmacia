public abstract class Funcionario {
    
   private String nome;
   private String matricula;
   private Float  salario;

    public Float getSalario() {
        return salario;
    }

    public void setSalario(Float salario) {
        this.salario = salario;
    }
  
   
   
   
   public void setNome(String n){
       
       this.nome = n;
     
   }
   
   
   
   public void setMatricula(String mat){
       this.matricula = mat;
   }
   
   public String getNome(){
       return this.nome;
   }
   
   public String getMatricula(){
       return this.matricula;
   }
   
}
