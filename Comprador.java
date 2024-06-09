public class Comprador extends Usuario{
    private String email;
    Comprador(String nombre, String apellido, int Id, String email){
        super(nombre, apellido, Id);
        this.email  = email;
    }
}
