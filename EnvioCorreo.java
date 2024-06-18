public class EnvioCorreo extends Envio {
    private String codigoPostal;
    public EnvioCorreo(String nombre,String direccion, String apellido, String codigoPostal,String dni) {
        super(false,direccion,nombre,apellido,dni);
        this.codigoPostal = codigoPostal;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    @Override
    public double gastoEnvio() {
        // Simulación de interacción con el sistema de correo para calcular los gastos
        return calcularGastosAdicionales(codigoPostal);
    }

    private double calcularGastosAdicionales(String codigoPostal) {
        // Aquí simplemente simularemos un costo de envío basado en el código postal
        return 50.0;
    }
}
