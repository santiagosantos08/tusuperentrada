import java.util.HashMap;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static java.util.HashMap.newHashMap;

public class Autenticador {
    // Estructura del CSV:
    // ID[DNI o CUIT/CUIL], nombre, apellido, tipoUsuario[1= Comprador, 2= Organizador], contraseña

    private HashMap<Comprador, String> compradoresLogin; //mapa de cada usuario a su contraseña(super seguro!)
    private HashMap<Organizador, String> organizadoresLogin;

    Autenticador(){
        compradoresLogin = new HashMap<>();
        organizadoresLogin = new HashMap<>();
    }

    public boolean guardarDatos(){
        try (FileWriter writer = new FileWriter("user_data.csv",false)){
            for(Map.Entry<Comprador, String> entry : compradoresLogin.entrySet()){
                Comprador comprador;
                comprador = entry.getKey();
                StringBuilder userLine = new StringBuilder();
                userLine.append(comprador.getId()).append(",").append(comprador.getNombre())
                        .append(",").append(comprador.getApellido()).append(",").append("1")
                        .append(",").append(entry.getValue()).append("\n");
                System.out.println("guardado comprador");
                System.out.println(userLine);
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

    public boolean levantarDatos(){
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

    public int loginValido(){
        return 1; // si es valido y es usuario
        // return 2; // si es valido y es comprador
        // return 3; // si es invalido
    };

    public int registroExitoso(String nombre, String apellido, String id, int tipoUsuario, String contrasenia){
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