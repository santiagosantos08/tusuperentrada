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
    private final int cantValoresFijosCSV = 10; //cantidad de cosas que hay antes de que se almacenen los datos variables en el csv

    public void agregarEvento(Evento evento){
        eventos.put(evento.getId(),evento);
    }

    public boolean inicializarEventos(){ //mover al constructor?? pero despues va a quedar raor tener que usar obligastoriamene a mano el guardar :/
        String line;
        String[] data;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo_eventos))){
            while ((line = br.readLine()) != null) {
                // todo este menjunte tendria que hacer una clase que solamente maneje los archivos pero ya es tarde
                // tengo el mismo codigo en 2 clases pero cambia q almacena cada linea del csv, tendria que agregarle mas IDS y cosas para saber q es cada una..
                // por ahora alcanza y hace lo que necesita hacer
                data = line.split(",");
                int idEvento = Integer.parseInt(data[0]);
                String idOrganizador = data[1];
                int capacidad = Integer.parseInt(data[2]);
                String nombreEvento = data[3];
                String ubicacion = data[4];
                String descripcion = data[5];
                String urlImagen = data[6];
                String tipoEvento = data[7];
                Double precio = Double.parseDouble(data[8]);
                int cantFunciones = Integer.parseInt(data[9]);
                Evento evento = new Evento(capacidad, nombreEvento, ubicacion, descripcion, urlImagen, tipoEvento, idEvento, idOrganizador);
                evento.setPrecio(precio);
                int f = 0;
                int i = 0;
                while (f < cantFunciones){
                    LocalDate fecha = LocalDate.parse(data[cantValoresFijosCSV + i]);
                    evento.agregarFecha(fecha.toString());
                    i++;
                    int butacasOcupadas = Integer.parseInt(data[cantValoresFijosCSV + i]);
                    i++;
                    int j = i + cantValoresFijosCSV;
                    while (cantValoresFijosCSV + i < (j + (butacasOcupadas * 2) - 1)){
                        String id = data[cantValoresFijosCSV + i];
                        i++;
                        int numero = Integer.parseInt(data[cantValoresFijosCSV + i]);
                        evento.getFunciones().get(fecha).put(numero, id);
                        i++;
                    }
                    f++;
                }
                eventos.put(evento.getId(),evento);
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
                eLine.append(e.getCantFunciones()).append(","); // Hasta aca
                for (LocalDate fecha: e.getFechas()){ // dios me quiero matar
                    eLine.append(fecha.toString()).append(",");
                    eLine.append(e.getCantButacasOcupadas(fecha)).append(",");
                    for (Map.Entry<Integer, String> entryButacas : e.getButacasOcupadas(fecha).entrySet()){
                        eLine.append(entryButacas.getValue()).append(",");
                        eLine.append(entryButacas.getKey()).append(",");
                    }
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
