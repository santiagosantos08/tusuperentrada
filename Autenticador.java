import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

public class Autenticador {

    // ESTRUCTURA DEL ARCHIVO:
    // [0] = 1 Comprador, = 2 Organizador
    // [1] Nombre
    // [2] Apellido
    // [3] ID
    // [4] Contraseña
    // [5] Cantidad Eventos (ya sea comprados/reservados/organizados)

    // SOLO si es Comprador
    // [6] Email
    // [7] Nacimiento
    // [8] CantPreferencias
    // [9..] Lista de preferencias
    // [...] INTERCALADO x CantidadEventos: ID Evento, nroButaca, true/false=comprada/reservada, si el anterior es true tiene retiro/envio , direccion (ya sea de envio o retiro)

    // SOLO si es Organizador
    // [6..] id eventos
    // y despues en otro sprint el que sea organizadr va a tener las preferencias de pago y todo eso.. definir bien a futuro.

    //nota a futuro: jamás hacer esto otra vez, era mas facil levantar un serverSQL y abrir los puertos en el router..

    private static final int CantidadValoresFijosCSVComprador = 9;
    private static HashMap<String, Comprador> compradores = new HashMap<>();
    private static HashMap<String, Organizador> organizadores = new HashMap<>();

    public static boolean guardarDatos(){
        try (FileWriter writer = new FileWriter("user_data.csv",false)){
            for(Map.Entry<String,Comprador> entry : compradores.entrySet()){
                Comprador comprador;
                comprador = entry.getValue();
                int eventosRelacionados = comprador.getCompras().size() + comprador.getReservas().size();
                StringBuilder userLine = new StringBuilder();
                userLine.append("1").append(",");
                userLine.append(comprador.getNombre()).append(",");
                userLine.append(comprador.getApellido()).append(",");
                userLine.append(comprador.getId()).append(",");
                userLine.append(comprador.getContrasenia()).append(",");
                userLine.append(eventosRelacionados).append(",");
                userLine.append(comprador.getEmail()).append(",");
                userLine.append(comprador.getFecha_nacimiento()).append(",");
                userLine.append(comprador.getPreferencias().size()).append(",");
                // [...] INTERCALADO x CantidadEventos: ID Evento, nroButaca, true/false=comprada/reservada, si el anterior es true tiene retiro/envio , direccion (ya sea de env
                for(Map.Entry<Integer, Integer> e1 : comprador.getCompras().entrySet()){
                    userLine.append(e1.getKey()).append(",");
                    userLine.append(e1.getValue()).append(",");
                    userLine.append("true").append(",");
                    userLine.append(comprador.getEnvios().getOrDefault(e1.getKey(),new Envio(false,"")).isRetiraPorSucursal()).append(",");
                    //el default ese no deberia pasar NUNCA pero prefiero esto antes que crashee el programa
                    userLine.append(comprador.getEnvios().getOrDefault(e1.getKey(),new Envio(false,"")).getDireccion()).append(",");
                }
                for(Map.Entry<Integer, Integer> e1 : comprador.getReservas().entrySet()){
                    userLine.append(e1.getKey()).append(",");
                    userLine.append(e1.getValue()).append(",");
                    userLine.append("false").append(",");
                    userLine.append("false").append(",");
                    userLine.append("N/A").append(",");
                }
                userLine.setLength(userLine.length() - 1);
                userLine.append("\n");
                String entradaCSV = userLine.toString();
                writer.write(entradaCSV);
            }
            for(Map.Entry<String, Organizador> entry : organizadores.entrySet()){
                Organizador organizador;
                organizador = entry.getValue();
                StringBuilder userLine = new StringBuilder();
                userLine.append("2").append(",");
                userLine.append(organizador.getNombre()).append(",");
                userLine.append(organizador.getApellido()).append(",");
                userLine.append(organizador.getId()).append(",");
                userLine.append(organizador.getContrasenia()).append(",");
                userLine.append(organizador.getEventos().size()).append(",");
                for(Integer i : organizador.getEventos()){
                    userLine.append(i).append(",");
                }
                userLine.setLength(userLine.length() - 1);
                userLine.append("\n");
                String entradaCSV = userLine.toString();
                writer.write(entradaCSV);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    };

    public static boolean levantarDatos(){
        String csvFile = "user_data.csv";
        String line;
        String[] data;
        // [0] = 1 Comprador, = 2 Organizador
        // [1] Nombre
        // [2] Apellido
        // [3] ID
        // [4] Contraseña
        // [5] Cantidad Eventos (ya sea comprados/reservados/organizados)
        // SOLO si es Comprador
        // [6] Email
        // [7] Nacimiento
        // [8] CantPreferencias
        // [9..] Lista de preferencias
        // [...] INTERCALADO x CantidadEventos: ID Evento, nroButaca, true/false=comprada/reservada, si el anterior es true tiene retiroTRUE/envioFALSE , direccion (ya sea de envio o retiro)
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            while ((line = br.readLine()) != null) {
                data = line.split(",");
                int tipoUsuario = Integer.parseInt(data[0]);
                String nombre = data[1];
                String apellido = data[2];
                String id = data[3];
                String contrasenia = data[4];
                int cantEventos = Integer.parseInt(data[5]);
                //1 es Comprador, 2 es Organizador
                if(tipoUsuario == 2){
                    organizadores.put(id,new Organizador(nombre,apellido,id,contrasenia));
                }
                if(tipoUsuario == 1){
                    String email = data[6];
                    LocalDate nacimiento = LocalDate.parse(data[7]);
                    int cantPreferencias = Integer.parseInt(data[8]);
                    ArrayList<String> preferencias = new ArrayList<>();
                    HashMap<Integer, Envio> envios = new HashMap<>();
                    HashMap<Integer, Integer> reservas = new HashMap<>();
                    HashMap<Integer, Integer> comprasConfirmadas = new HashMap<>();
                    for(int i = 0; i < cantPreferencias; i++){
                        preferencias.add(data[CantidadValoresFijosCSVComprador + i]);
                    }
                    int i = CantidadValoresFijosCSVComprador + cantPreferencias;
                    while(i < CantidadValoresFijosCSVComprador + cantPreferencias + (cantEventos * 5)){
                        int idEvento = Integer.parseInt(data[i]);
                        int nroButaca = Integer.parseInt(data[i+1]);
                        boolean compraConfirmada = Boolean.parseBoolean(data[i+2]);
                        //despues al guardar acordarme que si no esta confirmada igual hay que tirar 2 valores para no desalinear todo el archivo
                        boolean retiraPorSucursal = Boolean.parseBoolean(data[i+3]);
                        String direccion = data[i+4];
                        if(compraConfirmada){
                            comprasConfirmadas.put(idEvento,nroButaca);
                            envios.put(idEvento, new Envio(retiraPorSucursal, direccion));
                        }else{
                            reservas.put(idEvento, nroButaca);
                        }
                        i = i+5;
                    }
                    compradores.put(id, new Comprador(nombre,apellido,id,contrasenia,email,nacimiento,preferencias,envios,comprasConfirmadas,reservas));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    };

    public static int loginValido(String id, String contrasenia){
        // return 1; // si es valido y es comprador
        // return 2; // si es valido y es organizador
        // return 3; // si es invalido x comb de user/pass
        // return 4 si es invalido x cantidad de caracteres del ID
        // return 5 si el usuario no existe
        if(id.length() != 11 && id.length() != 8){
            return 4; //el id no es ni dni ni cuit/cuil
        }else if(id.length() == 11){ //organizador
            Organizador o = organizadores.getOrDefault(id,null);
            if (o == null){
                return 5;
            }
            if (o.getContrasenia().equals(contrasenia)){
                return 2;
            }
            return 3;
        }else if(id.length() == 8){ //comprador
            Comprador c = compradores.getOrDefault(id,null);
            if (c == null){
                return 5;
            }
            if (c.getContrasenia().equals(contrasenia)){
                return 1;
            }
            return 3;
        }
        return 3; //en realidad no hace falta pero es para no dejar sin return default, es el mas generico.
    };

    public static int registroExitoso(String nombre, String apellido, String id, int tipoUsuario, String contrasenia){
        // return 1 registro existoso
        // return 2 ya existe el usuario
        // return 3 caracteres invalidos.
        // return 4 mal invocada la funcion
        // return 5 logitud de Id incorrecta
        if(nombre.contains(",") || apellido.contains(",")){ return 3; } //si el nombre o el apellido tiene coma retorna
                                                                        //ya que romperia el sofisticado sistema csv
        if(tipoUsuario == 2){
            if(id.length() != 11){return 5;}
            if(!organizadores.containsKey(id)){
                return 2;
            }
            organizadores.put(id, new Organizador(nombre,apellido,id,contrasenia));
            return 1;
        }else if(tipoUsuario == 1){
            if(id.length() != 8){return 5;}
            if(!compradores.containsKey(id)){
                return 2;
            }
            compradores.put(nombre,new Comprador(nombre,apellido,id,contrasenia,"N/A",LocalDate.EPOCH,new ArrayList<>(),
                    new HashMap<>(),new HashMap<>(),new HashMap<>()));
            return 1;
        }
        return 4; // se le pasó un tipoUsuario que no existe a la funcion
    };
}
