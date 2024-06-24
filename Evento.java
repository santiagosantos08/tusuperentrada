
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Evento {
    private int capacidad, id, precio;
    private String nombre, ubicacion,descripcion, imagen_url, tipo_evento; //las fotos tiene que ser muchas!!!!!!!!!!!!! en otra entrega será
                                                                            //o de ultima para safar que sean links a google fotos y veo que si no esta logueado le
                                                                            //cambio acá algun argumento al link para que solo muestre algunas NO SE
    private ArrayList<LocalDate> fechas;
    private HashMap<Integer, String> butacasOcupadas; //mapa de nroButaca -> Id comprador que la tiene.
    private String org_id;

    public Evento(int capacidad, String nombre, String ubicacion, String descripcion, String imagen_url,
                  String tipo_evento, ArrayList<LocalDate> fechas, int id, String org_id, HashMap<Integer,String> asientos) {
        //chequear que ninguno de los String tenga comas!!!!!!
        this.capacidad = capacidad;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.imagen_url = imagen_url;
        this.tipo_evento = tipo_evento;
        if(fechas != null){
            this.fechas = fechas;
        }else{
            this.fechas = new ArrayList<>();
        }
        if (asientos != null){ //cuando se crea desde la interfaz se le tiene que pasar null
            this.butacasOcupadas = asientos;
        }else{
            this.butacasOcupadas = new HashMap<>();
        }
        this.id = id;
        this.org_id = org_id;
    }

    public int getPrecio(){
        return this.precio;
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

    public ArrayList<LocalDate> getFechas() {
        return this.fechas;
    }

    public int getId() {
        return id;
    }

    public HashMap<Integer, String> getButacasOcupadas(){
        return this.butacasOcupadas;
    }
    //acomodar esto!°!
    public boolean asientoDisponible(int nro_asiento) {
        if (nro_asiento <= 0 || nro_asiento > capacidad){
            return false;
        }
        return butacasOcupadas.containsKey(nro_asiento-1);

    }
    public boolean seleccionarAsiento(int nro_asiento, String idComprador) {
        if (nro_asiento > 0 || nro_asiento <= capacidad) {
            if (asientoDisponible(nro_asiento)) {
                butacasOcupadas.put(nro_asiento-1,idComprador);
            }
        }
        return false;
    }
    public int getCantButacasOcupadas(){
        return butacasOcupadas.size();
    }

    public String toString() {
        String fechasEvento = "";
        for(LocalDate l : fechas){
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

