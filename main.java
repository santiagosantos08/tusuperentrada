import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
CRITERIOS ACEPTACION SIN VERIFICAR:

REGISTRAR:
    Permitir que pueda acceder a actualizar sus datos e intereses en distintos tipos de eventos de forma entendible.
INGRESAR:
    Verificar que sea un usuario ya existente, en caso de no serlo dar la opcion de registrarse (ver user storie TU-29)
    El usuario debe ingresar el numero de documento y contraseña
    Verificar que se valide el numero de documento y su contraseña, en caso de ser erroneos notificar y pedir el documento y la contraseña nuevamente.
RESERVAR:
    Verificar que el comprador este ingresado en la pagina (ver user storie Tu-30)
    Se debe de mostrar el evento y fecha
    Se debe de mostrar el precio y la ubicacion disponibles de las entradas
    Se debe de poder seleccionar la cantidad de entradas que quiere comprar
    Se debe de mostrar las diferentes tarjetas de credito y sus promociones
    Se debe de guardar todo lo que haya saleccionado el usuario,  la reserva dura unos minutos. Pasado el tiempo se liberan las entradas
VER TIPOS ENVIO:
    Verificar que el usuario este registrado en la pagina
    Se debe de mostrar las diferentes formas de entrega de las entradas, estas pueden ser: retiro por sucursal o entrega a domicilio
    Se debe poder seleccionar la opcion de entrega
    Si el comprador selecciona retiro en sucursal de correo,  deberá indicar el código postal
    El sistema debe calcular los costos del envio por correo interactuando con el sitio del correo oficial teniendo en cuenta el codigo postal ingresado.
    confirmar compra (ver user storie Tu-36)
CONFIRMAR:
    Se debe de visualizar la orden de compra completa
    Se debe de mostrar las tarjetas disponibles
    Se debe de confirmar el pago
    Se debe marcar la entrada como no disponible
    Guardar informacion de la compra (Usuario, fecha y entrada) en base de datos
    Se debe notificar la compra exitosa (ver user storie Tu-37)

 */
// demo de app
public class main {
    public static Scanner s = new Scanner(System.in);
    private static CatalogoEventos catalogo;
    private static HashMap<Integer, Evento> eventos;

    private static void printEventoLogueado(Evento e, int nroLista){
        print("==[ "+nroLista+" ]=================================================================================");
        print("  "+e.getNombre()+" @ "+e.getUbicacion()+" $$$ "+e.getPrecio()+" 1erFecha: "+e.getFechas().getFirst());
        print("  "+e.getDescripcion()+"   "+e.getTipo_evento()+" ocup: "+e.getCantButacasOcupadas()+"/"+e.getCapacidad());
    }

    private static void printEventoGeneral(Evento e, int nroLista){
        print("==[ "+nroLista+" ]=================================================================================");
        print("  "+e.getNombre()+" @ "+e.getUbicacion()+" 1erFecha: "+e.getFechas().getFirst());
    }

    private static void print(String s){
        System.out.println(s); // C macros who
    }

    public static void modificarDatosPersonales(String id){
        print("!!!======================================================================================!!!");
        print(" Datos personales: ");
        print(" Ingresá tu email: ");
        String email = s.nextLine(); //este habria que hacerlo diferente para que no pida todo pero por ahora es así nomas.. aproveché el otro y listo para que quede como demo, no es lo mejor pero se pide en el CA.
        Autenticador.getCompradores().getOrDefault(id,null).setEmail(email);
        print(" Ingresá tu fecha de nacimiento en formato <aaaa>-<mm>-<dd> :");
        print(" Ej: 1 de marzo de 2002 = 2002-03-01");
        LocalDate nacimiento = LocalDate.parse(s.nextLine());
        Autenticador.getCompradores().getOrDefault(id,null).setFecha_nacimiento(nacimiento);
        boolean masTiposEventos = true;
        print(" Seleccioná que tipo de eventos te interesan: "); //faltaria hacer que se puedan sacar tambien....
        while(masTiposEventos) {
            ArrayList<String> eventosSinSeleccionar = DispatcherPreferencias.getPreferencias();
            eventosSinSeleccionar.removeAll(Autenticador.getCompradores().getOrDefault(id, null).getPreferencias()); //otra vez habria que chequear que no sea null
            if (eventosSinSeleccionar.size() != 0) {
                print(" Marcá un tipo y presioná enter. ");
                int i = 1;
                for (String s : eventosSinSeleccionar) {
                    print("[" + i + "] " + s);
                    i++;
                }
                String seleccion = s.nextLine(); //chequear que sea valido capaz es una opcion que ni sale!!!
                Autenticador.getCompradores().getOrDefault(id, null).getPreferencias().add(DispatcherPreferencias.getPreferencias().get(i - 1));
                print(" ¿Querés agregar más? [s/n]: ");
                String opt = s.nextLine();
                if (opt.equals("n")) {
                    masTiposEventos = false;
                }
            } else {
                masTiposEventos = false;
            }
        }
    }

    public static void pantallaCompradorInicioSesion(String id, boolean recienRegistrado){
        if(recienRegistrado){
            print("!!!======================================================================================!!!");
            print(" Tenés que completar tus datos personales: ");
            print(" Ingresá tu email: ");
            String email = s.nextLine();
            Autenticador.getCompradores().getOrDefault(id,null).setEmail(email);
            print(" Ingresá tu fecha de nacimiento en formato <aaaa>-<mm>-<dd> :");
            print(" Ej: 1 de marzo de 2002 = 2002-03-01");
            LocalDate nacimiento = LocalDate.parse(s.nextLine());
            Autenticador.getCompradores().getOrDefault(id,null).setFecha_nacimiento(nacimiento);
            boolean masTiposEventos = true;
            print(" Seleccioná que tipo de eventos te interesan: ");
            while(masTiposEventos){
                ArrayList<String> eventosSinSeleccionar = DispatcherPreferencias.getPreferencias();
                eventosSinSeleccionar.removeAll(Autenticador.getCompradores().getOrDefault(id, null).getPreferencias()); //otra vez habria que chequear que no sea null
                if(eventosSinSeleccionar.size() != 0) {
                    print(" Marcá un tipo y presioná enter. ");
                    int i = 1;
                    for (String s : eventosSinSeleccionar) {
                        print("[" + i + "] " + s);
                        i++;
                    }
                    String seleccion = s.nextLine(); //chequear que sea valido capaz es una opcion que ni sale!!!
                    Autenticador.getCompradores().getOrDefault(id, null).getPreferencias().add(DispatcherPreferencias.getPreferencias().get(i - 1));
                    print(" ¿Querés agregar más? [s/n]: ");
                    String opt = s.nextLine();
                    if (opt.equals("n")) {
                        masTiposEventos = false;
                    }
                }else{masTiposEventos = false;}
            }
        }
        boolean cerrarSesion = false;
        while(!cerrarSesion){
            print(" Bienvenidx "+Autenticador.getCompradores().getOrDefault(id, null).getNombre()+"   Ingresá [d] para mofidicar tus datos personales, o elegí un evento:");
            int i = 1;
            for(Map.Entry<Integer, Evento> entry : eventos.entrySet()){
                Evento e = entry.getValue();
                printEventoLogueado(e, i);
                i++;
            }
            String opcion = s.nextLine();
            if(opcion.equals("d")){modificarDatosPersonales(id);}


        }
    }

    public static void pantallaOrganizadorInicioSesion(){
        System.out.println(" Pantalla de ejemplo organizador que ya inició sesión ");
        System.out.println(" ej actividades de organizador: editar eventos etc; no es para este sprint ");
    }

    public static void main(String[] args){
        boolean run = true;
        while(run){

            System.out.println(" Bienvenidx a TUSUPERENTRADA ");
            System.out.println(" ej ev1: nombre dd/mm/aaaa lugar imagen ");
            System.out.println(" ej ev2: nombre dd/mm/aaaa lugar imagen ");
            System.out.println(" ej ev3: nombre dd/mm/aaaa lugar imagen ");
            System.out.println(" ej ev4: nombre dd/mm/aaaa lugar imagen ");
            System.out.println(" ej ev5: nombre dd/mm/aaaa lugar imagen ");
            System.out.println(" Presione 1 para inciar sesion ");
            System.out.println(" Presione 2 para registrarse ");
            System.out.println(" Presione 3 para salir ");
            String opcion = s.nextLine();

            if(opcion.equals("1")){
                System.out.println("Ingrese su identificador unico (DNI, CUIT, CUIL)");
                String id = s.nextLine();
                System.out.println("Ingrese su contraseña");
                String pwd = s.nextLine();
                int status = Autenticador.loginValido(id,pwd);

                if(status == 1){
                    pantallaCompradorInicioSesion(id,false);
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás (cerrar sesion) ");
                }else if(status == 2){
                    pantallaOrganizadorInicioSesion();
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás (cerrar sesion) ");
                }else if(status == 3){
                    System.out.println(" El usuario con dicho ID existe pero la contraseña es incorrecta ");
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás y reintentar ");
                }else if(status == 4){
                    System.out.println(" ID de logitud invalida, por favor verifique los datos ");
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás y reintentar ");
                }else if(status == 5){
                    System.out.println(" El usuario con dicho ID no existe, por favor, registrese o verifique los datos ");
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás y reintentar ");
                }
            }else if(opcion.equals("2")){
                System.out.println(" Ingrese su nombre ");
                String nombre = s.nextLine();
                System.out.println(" Ingrese su apellido ");
                String apellido = s.nextLine();
                System.out.println(" Es usted [1] Comprador [2] Organizador de eventos"); //acá hacerle un loop x si mete opcion invalida
                String tipoUsuario = s.nextLine();
                int status = 0;
                String id = "";
                String password = "";
                if(tipoUsuario.equals("2")){
                    System.out.println(" Ingrese su CUIT/CUIL ");
                    id = s.nextLine();
                    System.out.println(" Ingrese su contrasenia ");
                    password = s.nextLine();
                }else if(tipoUsuario.equals("1")){
                    System.out.println(" Ingrese su DNI ");
                    id = s.nextLine();
                    System.out.println(" Ingrese su contrasenia ");
                    password = s.nextLine();
                }
                status = Autenticador.registroExitoso(nombre, apellido, id, Integer.parseInt(tipoUsuario), password);
                if(status == 1){
                    System.out.println(" Registro exitoso ");
                    pantallaCompradorInicioSesion(id, true);
                }else if(status == 2){
                    System.out.println(" El usuario con dicho ID ya existe, por favor inicie sesion ");
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás ");
                }else if(status == 3){
                    System.out.println(" Nombre y apellido no pueden contener los siguientes caracteres: ',' ");
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás y reintentar ");
                }else if(status == 4){
                    System.out.println(" Error en el sistema, por favor comuniquese con un administrador ");
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás y reintentar ");
                }else if(status == 5){
                    System.out.println(" El Id es invalido debido a su longitud, DNI = 8, CUIT/CUIL = 11 ");
                    String seleccion = s.nextLine();
                    System.out.println(" Ingrese cualquier caracter para volver atrás y reintentar ");
                }
            }else if(opcion.equals("3")){
                run = false;
            }
        }
        Autenticador.guardarDatos();
    }
}
