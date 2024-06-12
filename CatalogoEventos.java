import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class CatalogoEventos {
    //estructura del CSV de eventos
    //ID evento, ID organizador, capacidad, nombre, ubicacion, descrpcion, iamgen_url, tipo_evento, precio, cant_funciones,cantUsuariosCompraron,
    // fecha/s, IDS de usuarios que lo compraron y el nro de asiento (intercalado)
    private HashMap<Integer, Evento> eventos; // es redundante guardar el ID ya que lo tiene el evento pero hace ams facil buscar por id, quizá haya que sacarlo como atributo de evento.

    public CatalogoEventos(){
        this.eventos = new HashMap<>();
    }

    private final String archivo_eventos = "eventos.csv";
    private final int cantValoresFijosCSV = 11; //cantidad de cosas que hay antes de que se almacenen los datos variables en el csv

    public void agregarEvento(Evento evento){
        eventos.put(evento.getId(),evento);
    }

    public boolean inicializarEventos(){ //mover al constructor?? pero despues va a quedar raor tener que usar obligastoriamene a mano el guardar :/
        String line;
        String[] data;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo_eventos))){
            while ((line = br.readLine()) != null) {
                data = line.split(",");
                int idEvento = Integer.parseInt(data[0]);
                String idOrganizador = data[1];
                int capacidad = Integer.parseInt(data[2]);
                String nombreEvento = data[3];
                String ubicacion = data[4];
                String descripcion = data[5];
                String urlImagen = data[6];
                String tipoEvento = data[7];
                int precio = Integer.parseInt(data[8]);
                int cantFunciones = Integer.parseInt(data[9]);
                int cantCompradores = Integer.parseInt(data[10]);
                ArrayList<LocalDate> fechas = new ArrayList<>();
                HashMap<Integer, String> butacas = new HashMap<>();
                for(int i = 0; i < cantFunciones; i++){
                    fechas.add(LocalDate.parse(data[cantValoresFijosCSV + i]));
                }
                for(int i = 0; i < cantCompradores; i = i+2){
                    butacas.put(Integer.parseInt(data[cantValoresFijosCSV + cantFunciones + i + 1]),data[cantValoresFijosCSV + cantFunciones + i]);
                    //TODO: buscar al usuario con dicho ID y cargarle el evento, por ahora el metodo para buscar usuarios por id es muy malo, cambiar a un  hashShet
                    //aplica tanto para comprador como vendedor
                    // IDEA: hacer que cuando se construye un evento que no tiene organizador ni usuarios null ya el const de dicho evento se encargue de agregarle ahi
                }
                Evento nuevo = new Evento(capacidad,nombreEvento,ubicacion,descripcion,urlImagen,tipoEvento,fechas,idEvento,idOrganizador,butacas);
                eventos.put(nuevo.getId(),nuevo);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public HashMap<Integer, Evento> retornarEventos(){
        return this.eventos;
    }

    public boolean guardarDatos(){
        try (FileWriter writer = new FileWriter(archivo_eventos,false)){
            for(Map.Entry<Integer, Evento> entry : eventos.entrySet()){
                Evento e = entry.getValue();
                StringBuilder eLine = new StringBuilder();
                eLine.append(e.getId()).append(",");
                eLine.append(e.getOrg_id()).append(",");
                eLine.append(e.getCapacidad()).append(",");
                eLine.append(e.getNombre()).append(",");
                eLine.append(e.getUbicacion()).append(",");
                eLine.append(e.getDescripcion()).append(",");
                eLine.append(e.getImagen_url()).append(",");
                eLine.append(e.getTipo_evento()).append(",");
                eLine.append(e.getPrecio()).append(",");
                eLine.append(e.getCantFunciones()).append(",");
                eLine.append(e.getCantButacasOcupadas()).append(",");
                ArrayList<LocalDate> fechas = e.getFechas();
                HashMap<Integer, String> butacas = e.getButacasOcupadas();
                for(LocalDate l : fechas){
                    eLine.append(l.toString()).append(",");
                }
                for(Map.Entry<Integer, String> entryButacas : butacas.entrySet()){
                    eLine.append(entryButacas.getValue()).append(",");
                    eLine.append(entryButacas.getKey()).append(",");
                }
                eLine.setLength(eLine.length() - 1); //saca la ultima coma, haria falta verificar que no sea 0 -> -1 pero buee muy edge case
                eLine.append("\n");
                writer.append(eLine);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    };

}
