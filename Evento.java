import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
public class Evento {
    private int capacidad;
    private ArrayList<Boolean> asientos;
    private String nombre, ubicacion,descripcion, imagen_url, tipo_evento;
    private LocalDate fecha;//Cambiar por una lista

    public Evento(int capacidad, String nombre, String ubicacion, String descripcion, String imagen_url, String tipo_evento, LocalDate fecha) {
        this.capacidad = capacidad;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.imagen_url = imagen_url;
        this.tipo_evento = tipo_evento;
        this.fecha = fecha;
        this.asientos = new ArrayList<>(capacidad);
        for (int i = 0; i < capacidad; i++) {
            asientos.add(true);
        }
    }

    public int getCapacidad() {
        return capacidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagen_url() {
        return imagen_url;
    }

    public String getTipo_evento() {
        return tipo_evento;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public boolean asientoDisponible(int nro_asiento) {
        if (nro_asiento <= 0 || nro_asiento > capacidad)
            return false;
        return this.asientos.get(nro_asiento-1);
    }
    public void seleccionarAsiento(int nro_asiento) {
        if (nro_asiento > 0 || nro_asiento <= capacidad)
            this.asientos.set(nro_asiento-1, false);
    }

}


