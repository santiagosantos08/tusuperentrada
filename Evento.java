
import java.time.LocalDate;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

public class Evento {
    private int capacidad, id;
    private String nombre, ubicacion,descripcion, imagen_url, tipo_evento; //las fotos tiene que ser muchas!!!!!!!!!!!!! en otra entrega será
                                                                            //o de ultima para safar que sean links a google fotos y veo que si no esta logueado le
    private HashMap<LocalDate, HashMap<Integer, String>> fechas;                                                                     //cambio acá algun argumento al link para que solo muestre algunas NO SE
    private String org_id;
    private double precio;
    public Evento(int capacidad, String nombre, String ubicacion, String descripcion, String imagen_url,
                  String tipo_evento, int id, String org_id) {
        //chequear que ninguno de los String tenga comas!!!!!!
        this.capacidad = capacidad;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.imagen_url = imagen_url;
        this.tipo_evento = tipo_evento;
        this.fechas = new HashMap<>();
        this.id = id;
        this.org_id = org_id;
        this.setPrecio();
    }

    public void setPrecio(double precio){
        this.precio = precio;
    }

    public double getPrecio(){
        int retorno = (int) this.precio;
        return retorno;
    }
    private void setPrecio() {
        double min = 100.0;
        double max = 200000.0;
        this.precio = min + (max - min) * Math.random();
    }
    public String getOrg_id(){
        return org_id;
    }

    public int getCantFunciones(){
        return this.fechas.size();
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

    public Set<LocalDate> getFechas() {
        return this.fechas.keySet();
    }

    public int getId() {
        return id;
    }

    public HashMap<LocalDate, HashMap<Integer, String>> getFunciones(){
        return fechas;
    }

    public HashMap<Integer, String> getButacasOcupadas(LocalDate fecha){
        return fechas.get(fecha);
    }
    //acomodar esto!°!
    public boolean asientoDisponible(LocalDate fecha, int nro_asiento) {
        if (nro_asiento <= 0 || nro_asiento > capacidad){
            return false;
        }
        return fechas.get(fecha).containsKey(nro_asiento-1);

    }
    public boolean seleccionarAsiento(int nro_asiento, String idComprador, LocalDate fecha) {
        if (nro_asiento > 0 || nro_asiento <= capacidad) {
            if (asientoDisponible(fecha, nro_asiento)) {
                fechas.get(fecha).put(nro_asiento-1,idComprador);
            }
        }
        return false;
    }
    public int getCantButacasOcupadas(LocalDate fecha){
        return fechas.get(fecha).size();
    }

    public void agregarFecha(String fechastr){
        LocalDate fecha = LocalDate.parse(fechastr);
        fechas.put(fecha, new HashMap<>());
    }

    public void printFechas(){
        for (LocalDate fecha : fechas.keySet()){
            System.out.println(fecha.toString());
        }
    }

    public String toString() {
        String fechasEvento = "";
        for(LocalDate l : fechas.keySet()){
            fechasEvento = fechasEvento.concat(l.toString() + "|");
        }
        return "Evento{" +
                "nombre='" + nombre + '\'' +
                ", fechas=" + fechasEvento +
                ", ubicacion='" + ubicacion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio='" + precio + '\''+
                '}';
    }

}

