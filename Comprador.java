import java.util.Date;
import java.util.List;

public class Comprador extends Usuario{
    //..
    private String email;
    private Date nacimiento;
    private List<String> preferencias;
    private List<Evento> eventos;
    Comprador(String nombre, String apellido, String Id){
        super(nombre, apellido, Id);
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Date getNacimiento() {
        return nacimiento;
    }
    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }
    public List<String> getPreferencias() {
        return preferencias;
    }
    public void setPreferencias(List<String> preferencias) {
        this.preferencias = preferencias;
    }
    public boolean reservarEvento(Evento e, int nro_asiento) {
        if(e.asientoDisponible(nro_asiento)) {
            e.seleccionarAsiento(nro_asiento);
            this.eventos.add(e);
            return true;
        }
        return false;
    }
}
