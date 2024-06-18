public class Envio {
    private boolean retiraPorSucursal; //si es false ya se que es a domicilio
    private String direccion; //ya sea de envio o de retiro
    private String nombre;
    private String apellido;
    private String dni;


    Envio(boolean retira, String direccion, String nombre,String apellido, String dni){
       this.retiraPorSucursal = retira;
       this.direccion = direccion;
       this.nombre=nombre;
       this.apellido=apellido;
       this.dni=dni;
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
    public double gastoEnvio(){
        return 0.0;
    }
}
