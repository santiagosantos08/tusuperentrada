import java.util.HashSet;

public class Organizador extends Usuario{
    private HashSet<Integer> eventos;
    Organizador(String nombre, String apellido, String Id, String contrasenia){
        super(nombre, apellido, Id, contrasenia);
        eventos = new HashSet<>();
        // this.prefenrecias de pago.. etc completar
        //por ahora solo se creo para poder completar autenticador
    };

    public HashSet<Integer> getEventos() {
        return eventos;
    }

    public void setEventos(HashSet<Integer> eventos) {
        this.eventos = eventos;
    }
}
