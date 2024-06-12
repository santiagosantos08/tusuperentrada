
import java.time.LocalDate;
/*
public class Evento {
    public class Evento {
        // Atributos
        private String nombre;
        private LocalDate fecha;
        private String ubicacion;
        private String descripcion;

        // Constructor
        public Evento(String nombre, LocalDate fecha, String ubicacion, String descripcion) {
            this.nombre = nombre;
            this.fecha = fecha;
            this.ubicacion = ubicacion;
            this.descripcion = descripcion;
        }

        // Getters y Setters
        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        public void setFecha(LocalDate fecha) {
            this.fecha = fecha;
        }

        public String getUbicacion() {
            return ubicacion;
        }

        public void setUbicacion(String ubicacion) {
            this.ubicacion = ubicacion;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        // Método toString para imprimir los detalles del evento
        @Override
        public String toString() {
            return "Evento{" +
                    "nombre='" + nombre + '\'' +
                    ", fecha=" + fecha +
                    ", ubicacion='" + ubicacion + '\'' +
                    ", descripcion='" + descripcion + '\'' +
                    '}';
        }
}
*/
import java.util.ArrayList;
public class Evento {
    private int capacidad, id;
    private ArrayList<Boolean> asientos;
    private String nombre, ubicacion,descripcion, imagen_url, tipo_evento;
    private LocalDate fecha;//Cambiar por una lista
    private Organizador org;

    public Evento(int capacidad, String nombre, String ubicacion, String descripcion, String imagen_url, String tipo_evento, LocalDate fecha, int id, Organizador org, ArrayList<Boolean> asientos) {
        //chequear que ninguno de los String tenga comas!!!!!!
        this.capacidad = capacidad;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.imagen_url = imagen_url;
        this.tipo_evento = tipo_evento;
        this.fecha = fecha;
        if (asientos != null){ //cuando se crea desde la interfaz se le tiene que pasar null
            this.asientos = asientos;
        }else {
            this.asientos = new ArrayList<>(capacidad);
        }
        for (int i = 0; i < capacidad; i++) {
            asientos.add(true);
        }
        this.id = id;
        this.org = org;
    }

    public int getCantFunciones(){
        return 1;
        //aca cuando se pase de fecha a lista de fechas hay que hacer q retorne el lenght de la lista de fechas, importantisimo para que se guarde bien!!!
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

    public int getId() {
        return id;
    }

    public ArrayList<Boolean> getListaAsientos(){
        return this.asientos;
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

