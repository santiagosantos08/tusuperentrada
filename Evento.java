
import java.time.LocalDate;

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
