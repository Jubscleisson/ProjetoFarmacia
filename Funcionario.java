public abstract class Funcionario {
    private static int nextId = 1;
    private int id;
    private String nome;
    private String matricula;
    private Float salario;
    
    public Funcionario() {
        this.id = nextId++;
    }
    
    protected void setId(int id) {
        this.id = id;
        if (id >= nextId) {
            nextId = id + 1;
        }
    }
    
    public int getId() {
        return id;
    }

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