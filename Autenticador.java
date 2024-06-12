import java.util.Date;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

//Criterios de Aceptacion:
//
//    LISTO: Verificar que pueda ingresar los datos de manera clara y facil.
//
//    Corroborar que ingrese la informacion necesaria: nombre, apellido, contraseña, nro documento, fecha de nacimiento, email y tipos de eventos que le interesan.
//
//    Permitir que pueda acceder a actualizar sus datos e intereses en distintos tipos de eventos de forma entendible.

public class Autenticador {
    // Estructura del CSV:
    // ID[DNI o CUIT/CUIL], nombre, apellido, tipoUsuario[1= Comprador, 2= Organizador], contraseña, cantidadEventos, IDS de los eventos que compró / es dueño depede el rol,
    // si es comprador tiene cantidadPreferencias, email, nacimiento
    // , lista de preferencias, ,
    // en caso de que sea comprador tiene intercalado el el nro de bbutaca que compró, true si la tiene comprada / false si solo reservada,
    // y si la tiene comprada direccion ya sea de envio o retiro
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
    // y despues en otro sprint el que sea organizadr va a tener las preferencias de pago y todo eso.. definir bien a futuro.

    //nota a futuro: jamás hacer esto otra vez, era mas facil levantar un serverSQL y abrir los puertos en el router..

    private static final int CantidadValoresFijosCSV = 6;
    private static HashMap<Comprador, String> compradoresLogin = new HashMap<>(); //mapa de cada usuario a su contraseña(super seguro!)
    private static HashMap<Organizador, String> organizadoresLogin = new HashMap<>();
    private static HashMap<String, Comprador> compradores = new HashMap<>();
    private static HashMap<String, Organizador> organizadores = new HashMap<>();

    public static boolean guardarDatos(){
        try (FileWriter writer = new FileWriter("user_data.csv",false)){
            for(Map.Entry<Comprador, String> entry : compradoresLogin.entrySet()){
                Comprador comprador;
                comprador = entry.getKey();
                StringBuilder userLine = new StringBuilder();
                userLine.append(comprador.getId()).append(",").append(comprador.getNombre())
                        .append(",").append(comprador.getApellido()).append(",").append("1")
                        .append(",").append(entry.getValue()).append("\n");
                String entradaCSV = userLine.toString();
                writer.write(entradaCSV);
            }
            for(Map.Entry<Organizador, String> entry : organizadoresLogin.entrySet()){
                Organizador organizador;
                organizador = entry.getKey();
                StringBuilder userLine = new StringBuilder();
                userLine.append(organizador.getId()).append(",").append(organizador.getNombre())
                        .append(",").append(organizador.getApellido()).append(",").append("2")
                        .append(",").append(entry.getValue()).append("\n");
                writer.append(userLine);
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
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            while ((line = br.readLine()) != null) {
                data = line.split(",");
                int tipoUsuario = Integer.parseInt(data[3]); //1 es Comprador, 2 es Organizador
                if(tipoUsuario == 2){
                    organizadoresLogin.put(new Organizador(data[1],data[2],data[0]),data[4]);
                }
                if(tipoUsuario == 1){
                    compradoresLogin.put(new Comprador(data[1],data[2],data[0]),data[4]);
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
            for(Map.Entry<Organizador, String> entry : organizadoresLogin.entrySet()){
                Organizador c = entry.getKey();
                if (c.getId().equals(id)) {
                    if(contrasenia.equals(entry.getValue())){
                        return 2;
                    }
                    return 3;
                }
            }
            return 5;
        }else if(id.length() == 8){ //comprador
            for(Map.Entry<Comprador, String> entry : compradoresLogin.entrySet()){
                Comprador c = entry.getKey();
                if (c.getId().equals(id)){
                    if(contrasenia.equals(entry.getValue())){
                        return 1;
                    }
                    return 3;
                }
            }
            return 5;
        }
        return 3; //en realidad no hace falta pero es para no dejar sin return default, es el mas generico.
    };
    public static boolean setDatosExtraComprador(String id, String email, Date fechaNacimiento, List<String> preferencias){
        for(Map.Entry<Comprador, String> entry : compradoresLogin.entrySet()){
            Comprador c = entry.getKey();
            if (c.getId().equals(id)){
                c.setEmail(email);
                c.setNacimiento(fechaNacimiento);
                c.setPreferencias(preferencias);
                return true;
            }
        }
        return false;
    };
    // public static Comprador get //lo comente para poder pushear el intellij no te deja si tiene errores
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
            Organizador nuevoOrg = new Organizador(nombre, apellido, id);
            // aca se podria mejorar pero no se si el containsKey usa el equals que le redefini o no.. asi es mas lento pero anda seguro
            for(Map.Entry<Organizador, String> entry : organizadoresLogin.entrySet()){
                Organizador c = entry.getKey();
                if (c.equals(nuevoOrg)) {
                    return 2;
                }
            }
            organizadoresLogin.put(nuevoOrg, contrasenia);
            return 1;
        }else if(tipoUsuario == 1){
            if(id.length() != 8){return 5;}
            Comprador nuevoComprador = new Comprador(nombre, apellido, id);
            for(Map.Entry<Comprador, String> entry : compradoresLogin.entrySet()){
                Comprador c = entry.getKey();
                if (c.equals(nuevoComprador)){
                    return 2;
                }
            }
            compradoresLogin.put(nuevoComprador, contrasenia);
            return 1;
        }
        return 4; // se le pasó un tipoUsuario que no existe a la funcion
    };
}
