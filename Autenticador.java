public class Autenticador {
    public static boolean guardarDatos(){return false;};
    public static boolean levantarDatos(){return false;};
    public static boolean loginValido(){return true;};
    public static int registroExitoso(String nombre, String Apellido, int Id){
        return 1; // si es exitoso
        return 2; // si falla porque ya está registrado
        return 3; // si falla porque algun dato es invalido
    };
}
