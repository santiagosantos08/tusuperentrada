import java.lang.reflect.Array;
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
    // []
    // [9] CantTarjetasCredito
    // [10..] Lista de preferencias
    // [...] INTERCALADO x CantidadEventos: ID Evento, nroButaca, true/false=comprada/reservada, si el anterior es true tiene retiroTRUE/envioFALSE , direccion (ya sea de envio o retiro)
    // [...] INTERCALADO x cantiTarjetasCredito: Numero, mesVto, añoVto, codigoSeg, desc.
    // SOLO si es Organizador
    // [6..] id eventos
    // y despues en otro sprint el que sea organizadr va a tener las preferencias de pago y todo eso.. definir bien a futuro.


    private static final int CantidadValoresFijosCSVComprador = 10;
    private static HashMap<String, Comprador> compradores = new HashMap<>();
    private static HashMap<String, Organizador> organizadores = new HashMap<>();

    public static HashMap<String, Comprador> getCompradores() {
        return compradores;
    }

    public static void setCompradores(HashMap<String, Comprador> compradores) {
        Autenticador.compradores = compradores;
    }


    public static HashMap<String, Organizador> getOrganizadores() {
        return organizadores;
    }

    public static void setOrganizadores(HashMap<String, Organizador> organizadores) {
        Autenticador.organizadores = organizadores;
    }

    private static String lineaComprador(Comprador c){
        int eventosRelacionados = c.getCompras().size();
        StringBuilder userline = new StringBuilder();
        userline.append("1").append(",");
        userline.append(c.getNombre()).append(",");
        userline.append(c.getApellido()).append(",");
        userline.append(c.getId()).append(",");
        userline.append(c.getContrasenia()).append(",");
        userline.append(eventosRelacionados).append(",");
        userline.append(c.getEmail()).append(",");
        userline.append(c.getFecha_nacimiento()).append(",");
        userline.append(c.getPreferencias().size()).append(",");
        userline.append(c.getMetodosPago().size()).append(",");
        for(String s : c.getPreferencias()){
            userline.append(s).append(",");
        }
        // [...] INTERCALADO x CantidadEventos: ID Evento, nroButaca, true/false=comprada/reservada, si el anterior es true tiene retiro/envio , direccion (ya sea de env
        for(Map.Entry<Integer, Integer> e1 : c.getCompras().entrySet()){
            userline.append(e1.getKey()).append(",");
            userline.append(e1.getValue()).append(",");
            userline.append(c.getEnvios().getOrDefault(e1.getKey(),new Envio(false,"")).isRetiraPorSucursal()).append(",");
            //el default ese no deberia pasar NUNCA pero prefiero esto antes que crashee el programa
            userline.append(c.getEnvios().getOrDefault(e1.getKey(),new Envio(false,"")).getDireccion()).append(",");
        }
        for(MetodoPago m : c.getMetodosPago()){
            Tarjeta t = (Tarjeta)m;
            userline.append(t.getNumeroTarjeta()).append(",");
            userline.append(t.getMesVto()).append(",");
            userline.append(t.getAnioVto()).append(",");
            userline.append(t.getCodigoSeg()).append(",");
            userline.append(t.getDescripcion()).append(",");
        }
        userline.setLength(userline.length() - 1);
        userline.append("\n");
        return userline.toString();
    }

    private static String lineaOrganizador(Organizador o){
        StringBuilder userline = new StringBuilder();
        userline.append("2").append(",");
        userline.append(o.getNombre()).append(",");
        userline.append(o.getApellido()).append(",");
        userline.append(o.getId()).append(",");
        userline.append(o.getContrasenia()).append(",");
        userline.append(o.getEventos().size()).append(",");
        for(Integer i : o.getEventos()){
            userline.append(i).append(",");
        }
        userline.setLength(userline.length() - 1);
        userline.append("\n");
        return userline.toString();
    }

    public static boolean guardarDatos(){
        try (FileWriter writer = new FileWriter("user_data.csv",false)){
            for(Map.Entry<String,Comprador> entry : compradores.entrySet()){
                Comprador comprador;
                comprador = entry.getValue();
                writer.write(lineaComprador(comprador));
            }
            for(Map.Entry<String, Organizador> entry : organizadores.entrySet()){
                Organizador organizador;
                organizador = entry.getValue();
                writer.write(lineaOrganizador(organizador));
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    };

    private static void levantarComprador(String[] data){
        // [1] Nombre
        // [2] Apellido
        // [3] ID
        // [4] Contraseña
        // [5] Cantidad Eventos (ya sea comprados/reservados/organizados)
        // [6] Email
        // [7] Nacimiento
        // [8] CantPreferencias
        // [9] CantTarjetasCredito
        // [10..] Lista de preferencias
        // [...] INTERCALADO x CantidadEventos: ID Evento, nroButaca, true/false=comprada/reservada, si el anterior es true tiene retiroTRUE/envioFALSE , direccion (ya sea de envio o retiro)
        // [...] INTERCALADO x cantiTarjetasCredito: Numero, mesVto, añoVto, codigoSeg, desc.
        // En el comprador el metodo de pagoMisCuentas no se guarda nada ya que toda la verificacion la hace el sistema.
        String nombre = data[1];
        String apellido = data[2];
        String id = data[3];
        String contrasenia = data[4];
        int cantEventos = Integer.parseInt(data[5]);
        String email = data[6];
        LocalDate nacimiento = LocalDate.parse(data[7]);
        int cantPreferencias = Integer.parseInt(data[8]);
        int cantTarjetasCredito = Integer.parseInt(data[9]);
        ArrayList<MetodoPago> metodosPago = new ArrayList<>();
        ArrayList<String> preferencias = new ArrayList<>();
        HashMap<Integer, Envio> envios = new HashMap<>();
        HashMap<Integer, Integer> comprasConfirmadas = new HashMap<>();
        for(int i = 0; i < cantPreferencias; i++){
            preferencias.add(data[CantidadValoresFijosCSVComprador + i]);
        }
        int i = CantidadValoresFijosCSVComprador + cantPreferencias;
        while(i < CantidadValoresFijosCSVComprador + cantPreferencias + (cantEventos * 4)){
            int idEvento = Integer.parseInt(data[i]);
            int nroButaca = Integer.parseInt(data[i+1]);
            //despues al guardar acordarme que si no esta confirmada igual hay que tirar 2 valores para no desalinear todo el archivo
            boolean retiraPorSucursal = Boolean.parseBoolean(data[i+2]);
            String direccion = data[i+3];
            comprasConfirmadas.put(idEvento,nroButaca);
            envios.put(idEvento, new Envio(retiraPorSucursal, direccion));
            i = i+4;
        }
        int offsetActual = cantPreferencias + i;
        for(int j = 0; j < cantTarjetasCredito; j++){
            String nroTarjeta = data[j + offsetActual];
            int mes = Integer.parseInt(data[j + offsetActual + 1]);
            int anio = Integer.parseInt(data[j + offsetActual + 2]);
            int ccv = Integer.parseInt(data[j + offsetActual + 3]);
            String desc = data[j + offsetActual + 4];
            Tarjeta t = new Tarjeta(nroTarjeta,ccv,mes,anio);
            t.setDescripcion(desc);
            metodosPago.add(t);
        }
        compradores.put(id, new Comprador(nombre,apellido,id,contrasenia,email,nacimiento,preferencias,envios,comprasConfirmadas,metodosPago));
    }

    private static void levantarOrganizador(String[] data){
        // [1] Nombre
        // [2] Apellido
        // [3] ID
        // [4] Contraseña
        // [5] Cantidad Eventos
        String nombre = data[1];
        String apellido = data[2];
        String id = data[3];
        String contrasenia = data[4];
        int cantEventos = Integer.parseInt(data[5]);
        organizadores.put(id,new Organizador(nombre,apellido,id,contrasenia));
    }

    public static boolean levantarDatos(){
        String csvFile = "user_data.csv";
        String line;
        String[] data;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            while ((line = br.readLine()) != null) {
                data = line.split(",");
                int tipoUsuario = Integer.parseInt(data[0]);
                if (tipoUsuario == 2)
                    levantarOrganizador(data);
                else
                    levantarComprador(data);
            }
        return true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

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
            if(organizadores.containsKey(id)){
                return 2;
            }
            organizadores.put(id, new Organizador(nombre,apellido,id,contrasenia));
            return 1;
        }else if(tipoUsuario == 1){
            if(id.length() != 8){return 5;}
            if(compradores.containsKey(id)){
                return 2;
            }
            compradores.put(id,new Comprador(nombre,apellido,id,contrasenia,"N/A",LocalDate.EPOCH,new ArrayList<>(),
                    new HashMap<>(),new HashMap<>(),new ArrayList<MetodoPago>()));
            return 1;
        }
        return 4; // se le pasó un tipoUsuario que no existe a la funcion
    };
}
