import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class Comprador extends Usuario{
    private String email;
    private LocalDate fecha_nacimiento;
    private ArrayList<String> preferencias;
    private HashMap<Integer, Envio> envios; //de ID evento -> Envio
    private HashMap<Integer, Integer> compras; // de ID evento -> nro butaca
    private HashMap<Integer, Integer> reservas; // eventos que tiene reservados pero no comprados -> nro butaca reservada

    Comprador(String nombre, String apellido, String Id, String contrasenia, String email, LocalDate nacimiento, ArrayList<String> preferencias,
              HashMap<Integer, Envio> envios,HashMap<Integer,Integer> compras, HashMap<Integer,Integer> reservas){
        super(nombre, apellido, Id, contrasenia);
        this.preferencias = preferencias;
        this.email = email;
        this.fecha_nacimiento = nacimiento;
        this.envios = envios;
        this.compras = compras;
        this.reservas = reservas;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public ArrayList<String> getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(ArrayList<String> preferencias) {
        this.preferencias = preferencias;
    }

    public HashMap<Integer, Envio> getEnvios() {
        return envios;
    }

    public void setEnvios(HashMap<Integer, Envio> envios) {
        this.envios = envios;
    }

    public HashMap<Integer, Integer> getCompras() {
        return compras;
    }

    public void setCompras(HashMap<Integer, Integer> compras) {
        this.compras = compras;
    }

    public HashMap<Integer, Integer> getReservas() {
        return reservas;
    }

    public void setReservas(HashMap<Integer, Integer> reservas) {
        this.reservas = reservas;
    }
//set eventos
}
