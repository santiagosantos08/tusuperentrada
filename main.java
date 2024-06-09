public class main {
    public static void main(String[] args){
        //pruebas ===================================
        // autenticador para funcionanilidad de registrar
        Autenticador.levantarDatos(); //inicializacion para que cargue del csv a memoria
        int juanPerezNoExiste = Autenticador.registroExitoso("Juan", "Perez", "76238734", 1,"JuancitoSuperPassword");
        int juanPerezYaExiste = Autenticador.registroExitoso("Juan", "Perez", "76238734", 1,"JuancitoSuperPassword");
        int nombreInvalido = Autenticador.registroExitoso("J,uan", "Perez", "76238734", 1,"JuancitoSuperPassword");
        int apellidoInvalido = Autenticador.registroExitoso("Juan", "P,erez", "76238734", 1,"JuancitoSuperPassword");
        int malLongDNI = Autenticador.registroExitoso("Juan", "Perez", "78734", 1,"JuancitoSuperPassword");
        int mismoNombreDistRol = Autenticador.registroExitoso("Juan", "Perez","45567856897", 2,"JuancitoSuperPassword");
    }
}
