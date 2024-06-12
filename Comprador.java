import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class Comprador extends Usuario{
    private String email;
    private LocalDate fehca_nacimiento;
    private ArrayList<String> preferencias;

    Comprador(String nombre, String apellido, String Id){
        super(nombre, apellido, Id);
        preferencias= new ArrayList<>();
    }

    //set eventos
}
