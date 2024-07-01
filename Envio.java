import java.util.Scanner;

public class Envio {
    private boolean retiraPorSucursal; // si es false ya se que es a domicilio
    private String direccion; // ya sea de envio o de retiro

    Envio(boolean retira, String direccion) {
        this.retiraPorSucursal = retira;
        this.direccion = direccion;
    }

    public boolean isRetiraPorSucursal() {
        return retiraPorSucursal;
    }

    public void setRetiraPorSucursal(boolean retiraPorSucursal) {
        this.retiraPorSucursal = retiraPorSucursal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int costoEnvio() {
        /*
        if (!retiraPorSucursal) { // Si es a domicilio
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese el número de código postal: ");
            int codigoPostal = scanner.nextInt();
            scanner.close();
            if (codigoPostal > 6000) {
                return 1500.0;
            } else {
                return 1000.0;
            }
        } else {
            // Si es retiro por sucursal, no se cobra envío
            return 0.0;
        }
        */
        if(retiraPorSucursal){return 0;}
        return 1200 + (int)Math.random()*5000;
    }
}
