public class main {
    public static void main(String[] args){
        //pruebas ===================================
        // autenticador para funcionanilidad de registrar

        Autenticador sistemLogin = new Autenticador();
        sistemLogin.levantarDatos(); //inicializacion para que cargue del csv a memoria
        int juanPerezNoExiste = sistemLogin.registroExitoso("Juan", "Perez", "76238734", 1,"JuancitoSuperPassword");
        int juanPerezYaExiste = sistemLogin.registroExitoso("Juan", "Perez", "76238734", 1,"JuancitoSuperPassword");
        int nombreInvalido = sistemLogin.registroExitoso("J,uan", "Perez", "76238734", 1,"JuancitoSuperPassword");
        int apellidoInvalido = sistemLogin.registroExitoso("Juan", "P,erez", "76238734", 1,"JuancitoSuperPassword");
        int malLongDNI = sistemLogin.registroExitoso("Juan", "Perez", "78734", 1,"JuancitoSuperPassword");
        int mismoNombreDistRol = sistemLogin.registroExitoso("Juan", "Perez","45567856897", 2,"JuancitoSuperPassword");
        int malLongCUIT = sistemLogin.registroExitoso("Juan", "Perez","46897", 2,"JuancitoSuperPassword");
        int argInvalido = sistemLogin.registroExitoso("Juan", "Perez","46897", 77,"JuancitoSuperPassword");
        System.out.println(juanPerezNoExiste);
        System.out.println(juanPerezYaExiste);
        System.out.println(nombreInvalido);
        System.out.println(apellidoInvalido);
        System.out.println(malLongDNI);
        System.out.println(mismoNombreDistRol);
        System.out.println(malLongCUIT);
        System.out.println(argInvalido);
        sistemLogin.guardarDatos();

    }
}
