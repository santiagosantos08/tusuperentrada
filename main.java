import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TimerTask;
import java.util.Timer;

// demo de app
public class main {
    private static Scanner s = new Scanner(System.in);
    private static boolean reserva_caducada = false;
    private static CatalogoEventos catalogo = new CatalogoEventos();
    private static HashMap<Integer, Evento> eventos;

    private static void inicializarSistema(){
        Autenticador.levantarDatos();
        catalogo.inicializarEventos();
        eventos = catalogo.retornarEventos();
    }

    private static void manejarReservaExpirada(Evento e, LocalDate fecha, ArrayList<Integer> butacas){
        print(" La reserva expiró.");
        for (Integer butaca : butacas){
            e.getFunciones().get(fecha).remove(butaca);
        }
        butacas.clear();
        reserva_caducada = false;
    }

    private static void manejarCompraExitosa(Evento e, LocalDate fecha, ArrayList<Integer> butacas, boolean retiro, String envio, String idUsuario){
        Envio env = new Envio(retiro,envio);
        for(Integer i : butacas){
            e.getFunciones().get(fecha).put(i,idUsuario);
            Autenticador.getCompradores().get(idUsuario).addCompra(e.getId(),i);
            }
        Autenticador.getCompradores().get(idUsuario).addEnvio(e.getId(),env);
        print("Compra Exitosa c:");
    }

    private static void confirmarCompra(String idUsuario, Tarjeta tarjeta, LocalDate fecha, Evento e, ArrayList<Integer> butacas, Timer t){
        print(e.getNombre()+" butacas: "+butacas.toString());
        double precio_base = butacas.size()*e.getPrecio();
        print(" Monto total de entradas ->  $"+precio_base + " | Con descuento por tarjeta -> $" + (precio_base - ((tarjeta.getPorcentajeDescuento()*precio_base)/100)));
        print(" ¿Quiere [r]etirar sus entradas o un [e]nvio a domicilio? ");
        print(" Aún puede cancelar la compra con [c] ");
        String opcion = s.nextLine();
        String envio;
        if(opcion.equals("r")){
            print(" Indique su codigo postal, la terminal de retiro mas cercana a ud será enviada por email"); //es mentira!!! todo mockup
            envio = s.nextLine();
            if (!reserva_caducada) {
                manejarCompraExitosa(e, fecha, butacas, reserva_caducada, envio, idUsuario);
                t.cancel();
            } else 
                manejarReservaExpirada(e, fecha, butacas);
        }else if(opcion.equals("e")){
            print(" Indique su dirección en formato Cod.postal-Calle-Nro-Piso-Dpto");
            envio = s.nextLine();
            if (!reserva_caducada) {
                manejarCompraExitosa(e, fecha, butacas, false, envio, idUsuario);
                print(" Costo de envio $$$ se cobrará cuando llegue el paquete."); //ver como calcular esto, dice que se usa un sistema del correo en el enunciado (?? supongo se podrá inventar cualq numero para la demo
                t.cancel();
            } else 
                manejarReservaExpirada(e, fecha, butacas);
        }else{
            print(" Compra Cancelada.");
            for (Integer butaca : butacas){
                e.getFunciones().get(fecha).remove(butaca);
            }
            t.cancel();
        }
    }

    private static Tarjeta nuevaTarjeta(Tarjeta tarjeta, Comprador c){
        System.out.println(" Ingrese número de tarjeta de credito: ");
        String nroTarjeta = s.nextLine();
        System.out.println(" Ingrese mes vto de la tarjeta: ");
        int mVto = Integer.parseInt(s.nextLine());
        System.out.println(" Ingrese año vto de la tarjeta: ");
        int aVto = Integer.parseInt(s.nextLine());
        System.out.println(" Ingrese codigo de seguridad de la tarjeta: ");
        int ccv = Integer.parseInt(s.nextLine());
        tarjeta = new Tarjeta(nroTarjeta, ccv, mVto, aVto);
        c.agregarMetodosPago(tarjeta);
        print("Su tarjeta tendrá un descuento de %" + tarjeta.getPorcentajeDescuento() + " en la compra!!!");
        return tarjeta;
    }

    private static void countDownReserva(String idUsuario, Evento e, ArrayList<Integer> butacas, LocalDate fecha){
        Timer t = new Timer();
        TimerTask cuenta_reserva = new TimerTask() {
            @Override
            public void run(){
                reserva_caducada = true;
                t.cancel();
            }
        };
        int espera = 90; // timer puesto en 90 segundos 
        t.schedule(cuenta_reserva, espera * 1000);
        System.out.println(" Reservando para "+e.getNombre());
        Comprador c = Autenticador.getCompradores().get(idUsuario);
        ArrayList<MetodoPago> aux = c.getMetodosPago();
        Tarjeta tarjeta = null;
        String aux1 = "-";
        if (aux.size() > 0) {
            print("Usted ya posee metodos de pago, si desea seleccionar alguno de ellos ingrese[s]." +
                    "Puede agregar un nuevo metodo de pago presionando cualquier otra tecla.");
            aux1 = s.nextLine();
            if(aux1.toLowerCase().equals("s")){
                print("Ingrese la opcion correspondiente");
                for (int i = 0; i<aux.size(); i++){
                    tarjeta = (Tarjeta) aux.get(i);
                    print("Opcion " + (i+1) + " Tarjeta terminada en: " + tarjeta.getNumeroTarjeta().substring(tarjeta.getNumeroTarjeta().length() - 4));
                    print("Con descuento del " + tarjeta.getPorcentajeDescuento() + "%");
                }
                int opcion = Integer.parseInt(s.nextLine());
                while(opcion < 1 || opcion > aux.size()) {
                    print("ingrese una opcion valida");
                    opcion = Integer.parseInt(s.nextLine());
                }
                tarjeta = (Tarjeta) aux.get(opcion - 1);
            } else {
                tarjeta = nuevaTarjeta(tarjeta, c);
            }
        }
        else {
            print("usted no posee metodos de pago debera ingresar uno");
            tarjeta = nuevaTarjeta(tarjeta, c);
        }
        // hacer validacion, por ahora pasa todo je
        System.out.println(" Ingrese [c] para cancelar la reserva [s] para confirmar el pago");
        String opcion = s.nextLine();
        if(opcion.equals("s")){
            if (!reserva_caducada) {
                confirmarCompra(idUsuario, tarjeta, fecha, e, butacas, t);
            } else {
                manejarReservaExpirada(e, fecha, butacas);

            }
        }
    }

    private static void pantallaReservar(String idUsuario, Evento e, LocalDate fecha){
        print(" Asientos disponibles en "+e.getNombre()+" para "+ fecha.toString());
        //TODO: verificar que si no hay lugares se imprima antes un aviso que esta full
        for(int i = 0; i < e.getCapacidad(); i++){
            if(i % 15 == 0){print("");}
            if(!e.getFunciones().get(fecha).containsKey(i)){
                System.out.print(" " + i + " ");
            }
        }
        System.out.println("");
        print(" Ingrese [r] si desea hacer una reserva. Ingrese cualquier valor para volver atrás");
        String opcion = s.nextLine();
        if (opcion.equals("r")){
            print(" Ingrese cantidad de entradas que quiere comprar. "); //TODO: verificar que no sea mayor a la cantidad disponible..
            String cantidad = s.nextLine();
            ArrayList<Integer> butacasReservadas = new ArrayList<>();
            for(int i = 0; i < Integer.parseInt(cantidad); i++){
                print(" Ingrese numero de butaca para su entrada n "+(i+1));
                Integer butaca = Integer.parseInt(s.nextLine());
                butacasReservadas.add(butaca);
                e.getFunciones().get(fecha).put(butaca ,idUsuario);
            }
            countDownReserva(idUsuario, e, butacasReservadas, fecha);
        }
    }

    private static void printEventoLogueado(Evento e, int nroLista){
        for (LocalDate fecha : e.getFechas()){
            print("==[ "+nroLista+" ]=================================================================================");
            print("  "+e.getNombre()+" @ "+e.getUbicacion()+" $$$ "+e.getPrecio()+" Fecha: "+ fecha);
            print("  "+e.getDescripcion()+"   "+e.getTipo_evento()+" ocup: "+e.getCantButacasOcupadas(fecha)+"/"+e.getCapacidad());
        }
    }

    private static void printEventoGeneral(Evento e, int nroLista){
        print("==[ "+nroLista+" ]=================================================================================");
        print("  "+e.getNombre()+" @ "+e.getUbicacion());
        print("Fechas Disponibles: ");
        e.printFechas();
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
            Autenticador.getCompradores().get(id).setEmail(email);
            print(" Ingresá tu fecha de nacimiento en formato <aaaa>-<mm>-<dd> :");
            print(" Ej: 1 de marzo de 2002 = 2002-03-01");
            LocalDate nacimiento = LocalDate.parse(s.nextLine());
            Autenticador.getCompradores().getOrDefault(id,null).setFecha_nacimiento(nacimiento);
            boolean masTiposEventos = true;
            while(masTiposEventos){
                ArrayList<String> eventosSinSeleccionar = DispatcherPreferencias.getPreferencias();
                eventosSinSeleccionar.removeAll(Autenticador.getCompradores().getOrDefault(id, null).getPreferencias()); //otra vez habria que chequear que no sea null
                if(eventosSinSeleccionar.size() != 0) {
                    print(" Seleccioná que tipo de eventos te interesan: ");
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
            print(" Bienvenidx "+Autenticador.getCompradores().getOrDefault(id, null).getNombre()+"   [M]ofidifique sus datos personales, [C]ierre sesión, o [L]iste Eventos Disponibles:");
            String opcion = s.nextLine();
            if (opcion.equals("L")){
                for(Map.Entry<Integer, Evento> entry : eventos.entrySet()){
                    Evento e = entry.getValue();
                    printEventoLogueado(e, entry.getKey());
    
                }
                print(" Ingresá el número de evento para expandirlo o [R]egrese al menú previo:");
                String numstr = s.nextLine();
                Integer num = Integer.parseInt(numstr);
                if (eventos.get(num) != null){
                    if (!eventos.get(num).getFechas().isEmpty()){
                        print("Ingrese una fecha de las disponibles: ");
                        eventos.get(num).printFechas();
                        String fecha = s.nextLine();
                        if (eventos.get(num).getFechas().contains(LocalDate.parse(fecha)) != false){
                            pantallaReservar(id, eventos.get(num), LocalDate.parse(fecha));
                        }
                    }
                } else {
                    print("No se halló el evento con el número ingresado :C");
                }
            }
            if(opcion.equals("M")){
                modificarDatosPersonales(id);
            }else if (opcion.equals("C")){
                cerrarSesion = true;
            }
        }
    }

    public static void pantallaOrganizadorInicioSesion(){
        System.out.println(" Pantalla de ejemplo organizador que ya inició sesión ");
        System.out.println(" ej actividades de organizador: editar eventos etc; no es para este sprint ");
    }

    public static void main(String[] args){
        inicializarSistema();
        boolean run = true;
        while(run){

            System.out.println(" Bienvenidx a TUSUPERENTRADA ");
            System.out.println(" Vista general, aún no iniciaste sesión ");
            System.out.println(" Preview de eventos: ");
            eventos.entrySet().stream().limit(5).forEach(entry -> printEventoGeneral(entry.getValue(), entry.getKey())); // que imprima nomas 5 de los eventos
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
                    System.out.println(" Ingrese cualquier caracter para volver atrás (cerrar sesion) ");
                    String seleccion = s.nextLine();
                }else if(status == 2){
                    pantallaOrganizadorInicioSesion();
                    System.out.println(" Ingrese cualquier caracter para volver atrás (cerrar sesion) ");
                    String seleccion = s.nextLine();
                }else if(status == 3){
                    System.out.println(" El usuario con dicho ID existe pero la contraseña es incorrecta ");
                    System.out.println(" Ingrese cualquier caracter para volver atrás y reintentar ");
                    String seleccion = s.nextLine();

                }else if(status == 4){
                    System.out.println(" ID de logitud invalida, por favor verifique los datos ");
                    System.out.println(" Ingrese cualquier caracter para volver atrás y reintentar ");
                    String seleccion = s.nextLine();

                }else if(status == 5){
                    System.out.println(" El usuario con dicho ID no existe, por favor, registrese o verifique los datos ");
                    System.out.println(" Ingrese cualquier caracter para volver atrás y reintentar ");
                    String seleccion = s.nextLine();

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
        catalogo.guardarDatos();
    }
}
