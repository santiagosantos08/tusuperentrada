import java.util.Scanner;
import java.util.ArrayList;
import java.time.*;
// demo de app
public class main {
    //...
    static ArrayList<Evento> eventos = new ArrayList<>();

    public static void pantallaCompradorInicioSesion(Comprador comprador) {
        System.out.println("Ingrese la opcion que desea realizar");
        System.out.println("1) Ver Eventos");
        System.out.println("2) Reservar evento");
        Scanner s = new Scanner(System.in);
        String opcion = s.nextLine();

        if (opcion.equals("1")) {
            leerDatosEventos(eventos);
        } else if (opcion.equals("2")) {
            leerDatosEventos(eventos);
            System.out.println("Ingrese el numero del evento del cual desea reservar la entrada");

            while (!s.hasNextInt()) {
                System.out.println("Por favor, ingrese un número válido para el evento:");
                s.next(); // Descarta la entrada no válida
            }
            int e = s.nextInt();
            s.nextLine(); // Consumir el salto de línea

            System.out.println("Ingrese el numero de asiento");
            while (!s.hasNextInt()) {
                System.out.println("Por favor, ingrese un número válido para el asiento:");
                s.next(); // Descarta la entrada no válida
            }
            int asiento = s.nextInt();
            s.nextLine(); // Consumir el salto de línea
            comprador.reservarEvento(e, asiento);

        }
    }

    public static void leerDatosEventos(ArrayList<Evento> eventos) {
            int i = 1;
        for (Evento evento : eventos) {
            System.out.println("Evento " + i);
            System.out.println("Nombre: " + evento.getNombre());
            System.out.println("Ubicacion: " + evento.getUbicacion());
            System.out.println("Descripcion: " + evento.getDescripcion());
            System.out.println("Imagen URL: " + evento.getImagen_url());
            System.out.println("Tipo de Evento: " + evento.getTipo_evento());
            System.out.println("Fecha: " + evento.getFecha());
            System.out.println("Capacidad: " + evento.getCapacidad());
            System.out.println("--------------------------");
            i++;
        }
    }

    public static void pantallaOrganizadorInicioSesion(){
        System.out.println(" Pantalla de ejemplo organizador que ya inició sesión \n");
        System.out.println(" ej actividades de organizador: editar eventos etc; no es para este sprint \n");
    }
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        boolean run = true;

        Evento event1 = new Evento(100, "Concert in the Park", "Central Park", "A live concert featuring local bands.", "http://example.com/concert.jpg", "Music", LocalDate.of(2024, 6, 20), 1);
        Evento event2 = new Evento(200, "Art Exhibition", "City Gallery", "Exhibition of contemporary art.", "http://example.com/art.jpg", "Art", LocalDate.of(2024, 7, 15), 2);
        Evento event3 = new Evento(150, "Tech Conference", "Convention Center", "Annual tech conference with keynotes and workshops.", "http://example.com/tech.jpg", "Conference", LocalDate.of(2024, 8, 10), 3);
        Evento event4 = new Evento(120, "Food Festival", "Downtown Plaza", "A festival showcasing food from around the world.", "http://example.com/food.jpg", "Festival", LocalDate.of(2024, 9, 5), 4);
        Evento event5 = new Evento(80, "Book Fair", "Library", "Book fair with author signings and readings.", "http://example.com/books.jpg", "Fair", LocalDate.of(2024, 10, 25), 5);
        eventos.add(event1);
        eventos.add(event2);
        eventos.add(event3);
        eventos.add(event4);
        eventos.add(event5);
        while(run){

            System.out.println(" Pantalla de Inicio de ejemplo sin iniciar sesión, acá se mostrarian solos los detalles principales de los eventos \n");
            leerDatosEventos(eventos);
            System.out.println(" Presione 1 para inciar sesion \n");
            System.out.println(" Presione 2 para registrarse \n");
            System.out.println(" Presione 3 para salir \n");
            String opcion = s.nextLine();

            if(opcion.equals("1")){
                System.out.println("Ingrese su identificador unico (DNI, CUIT, CUIL)\n");
                String id = s.nextLine();
                System.out.println("Ingrese su contraseña\n");
                String pwd = s.nextLine();
                int status = Autenticador.loginValido(id,pwd);

                if(status == 1){
                    pantallaCompradorInicioSesion();
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás (cerrar sesion) \n");
                }else if(status == 2){
                    pantallaOrganizadorInicioSesion();
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás (cerrar sesion) \n");
                }else if(status == 3){
                    System.out.println(" El usuario con dicho ID existe pero la contraseña es incorrecta \n");
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás y reintentar \n");
                }else if(status == 4){
                    System.out.println(" ID de logitud invalida, por favor verifique los datos \n");
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás y reintentar \n");
                }else if(status == 5){
                    System.out.println(" El usuario con dicho ID no existe, por favor, registrese o verifique los datos \n");
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás y reintentar \n");
                }
            }else if(opcion.equals("2")){
                System.out.println(" Ingrese su nombre \n");
                String nombre = s.nextLine();
                System.out.println(" Ingrese su apellido \n");
                String apellido = s.nextLine();
                System.out.println(" Es usted \n[1] Comprador \n[2] Organizador de eventos\n"); //acá hacerle un loop x si mete opcion invalida
                String tipoUsuario = s.nextLine();
                int status = 0;
                String id = "";
                String password = "";
                if(tipoUsuario.equals("2")){
                    System.out.println(" Ingrese su CUIT/CUIL \n");
                    id = s.nextLine();
                    System.out.println(" Ingrese su contrasenia \n");
                    password = s.nextLine();
                }else if(tipoUsuario.equals("1")){
                    System.out.println(" Ingrese su DNI \n");
                    id = s.nextLine();
                    System.out.println(" Ingrese su contrasenia \n");
                    password = s.nextLine();
                }
                status = Autenticador.registroExitoso(nombre, apellido, id, Integer.parseInt(tipoUsuario), password);
                if(status == 1){
                    System.out.println(" Registro exitoso, por favor vuelva al menu principal para inciar sesion \n");
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás \n");
                }else if(status == 2){
                    System.out.println(" El usuario con dicho ID ya existe, por favor inicie sesion \n");
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás \n");
                }else if(status == 3){
                    System.out.println(" Nombre y apellido no pueden contener los siguientes caracteres: ',' \n");
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás y reintentar \n");
                }else if(status == 4){
                    System.out.println(" Error en el sistema, por favor comuniquese con un administrador \n");
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás y reintentar \n");
                }else if(status == 5){
                    System.out.println(" El Id es invalido debido a su longitud, DNI = 8, CUIT/CUIL = 11 \n");
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás y reintentar \n");
                }
            }else if(opcion.equals("3")){
                run = false;
            }
            else {run = false;}
        }
        Autenticador.guardarDatos();
    }
}
