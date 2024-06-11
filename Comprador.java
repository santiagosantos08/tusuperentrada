import java.util.Date;
import java.util.List;
import java.util.Scanner;
public class Comprador extends Usuario{
    //..
    private String email;
    private Date nacimiento;
    private List<String> preferencias;
    private List<Evento> eventos;
    private Envio envio;

    Comprador(String nombre, String apellido, String Id){
        super(nombre, apellido, Id);
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Date getNacimiento() {
        return nacimiento;
    }
    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }
    public List<String> getPreferencias() {
        return preferencias;
    }
    public void setPreferencias(List<String> preferencias) {
        this.preferencias = preferencias;
    }
    public boolean reservarEvento(Evento e, int nro_asiento) {
        if(e.asientoDisponible(nro_asiento)) {
            e.seleccionarAsiento(nro_asiento);
            this.eventos.add(e);
            return true;
        }
        return false;
    }

    public void elegirTipoEnvio() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione el tipo de envío: 1. Retiro en punto de venta  2. Retiro en sucursal de correo");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        switch (opcion) {
            case 1:
                envio = new EnvioSucursal(nombre, apellido, id);
                ((EnvioSucursal) envio).listarOpciones();
                break;
            case 2:
                System.out.println("Ingrese el código postal:");
                String codigoPostal = scanner.nextLine();
                envio = new EnvioCorreo(nombre, apellido, id, codigoPostal);
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }

        double gastoEnvio = envio.gastoEnvio();
        System.out.println("Gastos administrativos adicionales: " + gastoEnvio);
    }
}
