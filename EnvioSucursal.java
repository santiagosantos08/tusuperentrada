import java.lang.reflect.Array;
import java.util.ArrayList;

public class EnvioSucursal extends Envio {
    private ArrayList<String> puntosDeVenta;

    public EnvioSucursal(String nombre, String apellido, String dni) {
        super(nombre, apellido, dni);
        // Inicializamos la lista de puntos de venta
        this.puntosDeVenta = new ArrayList<>();
        // Agregamos algunos puntos de venta de ejemplo
        puntosDeVenta.add("Punto de Lanus - Dirección Lanus");
        puntosDeVenta.add("Punto de CABA - Dirección CABA");
        puntosDeVenta.add("Punto de General Rodriguez - Dirección General Rodriguez");
    }

    @Override
    public double gastoEnvio() {
        return 0; // Retiro en punto de venta es gratuito
    }

    public void listarOpciones() {
        System.out.println("Listado de puntos de venta disponibles:");
        for (int i = 0; i < puntosDeVenta.size(); i++) {
            System.out.println((i + 1) + ". " + puntosDeVenta.get(i));
        }
    }
}
