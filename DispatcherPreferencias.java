import java.util.ArrayList;
import java.util.HashSet;

public class DispatcherPreferencias { //para que la cantidad y tipo de preferencias sea fija y no pase que el tipo del evento es 'cine de Terror' y el user ingresó 'Cine de terrór' y no matchee
    private static final ArrayList<String> preferencias = new ArrayList<>();
    public static void inicializarPreferencias(){
        //en un futuro estaria bueno que las levante de un archivo...
        preferencias.add("Stand Up");
        preferencias.add("Proyeccion pelicula");
        preferencias.add("Musica en vivo");
        preferencias.add("Danza");
        preferencias.add("Deporte");
        preferencias.add("DJ set");
        preferencias.add("Taller");
        preferencias.add("Pintura");
        preferencias.add("Otro");
    }
    public static ArrayList<String> getPreferencias(){return preferencias;}
}
