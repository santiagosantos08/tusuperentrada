public class Envio {
    private boolean retiraPorSucursal; //si es false ya se que es a domicilio
    private String direccion; //ya sea de envio o de retiro
    Envio(boolean retira, String direccion){
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
}
