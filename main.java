import java.util.Scanner;
// demo de app
public class main {
    //...
    public static void pantallaCompradorInicioSesion(){ // aca en una verison mas completa iria el nombre etc para que lo muestre
        System.out.println(" Pantalla de ejemplo comprador que ya inició sesión \n");
        System.out.println(" ej ev1: detalles avanzados \n");
        System.out.println(" ej ev2: detalles avanzados \n");
        System.out.println(" ej ev3: detalles avanzados \n");
        System.out.println(" ej ev4: detalles avanzados \n");
        System.out.println(" ej ev5: detalles avanzados \n");
    }
    public static void pantallaOrganizadorInicioSesion(){
        System.out.println(" Pantalla de ejemplo organizador que ya inició sesión \n");
        System.out.println(" ej actividades de organizador: editar eventos etc; no es para este sprint \n");
    }
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        boolean run = true;
        while(run){

            System.out.println(" Pantalla de Inicio de ejemplo sin iniciar sesión, acá se mostrarian solos los detalles principales de los eventos \n");
            System.out.println(" ej ev1: nombre dd/mm/aaaa lugar imagen \n");
            System.out.println(" ej ev2: nombre dd/mm/aaaa lugar imagen \n");
            System.out.println(" ej ev3: nombre dd/mm/aaaa lugar imagen \n");
            System.out.println(" ej ev4: nombre dd/mm/aaaa lugar imagen \n");
            System.out.println(" ej ev5: nombre dd/mm/aaaa lugar imagen \n");
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
        }
        Autenticador.guardarDatos();
    }
}
