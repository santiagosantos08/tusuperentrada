public class Tarjeta extends MetodoPago{
    private int randPorc(int min, int max){
        return (int)((Math.random()*(max-min))+min);
    }
    private String numeroTarjeta;
    private int codigoSeg;
    private int mesVto;
    private int anioVto;
    private String descripcion;
    private int porcentajeDescuento;
    Tarjeta(String nro, int codigoSeg, int mesVto, int anioVto){
        this.numeroTarjeta = nro;
        this.codigoSeg = codigoSeg;
        this.mesVto = mesVto;
        this.anioVto = anioVto;
        this.porcentajeDescuento = randPorc(5,20);
        this.descripcion = "  ";
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public int getCodigoSeg() {
        return codigoSeg;
    }

    public void setCodigoSeg(int codigoSeg) {
        this.codigoSeg = codigoSeg;
    }

    public int getMesVto() {
        return mesVto;
    }

    public void setMesVto(int mesVto) {
        this.mesVto = mesVto;
    }

    public int getAnioVto() {
        return anioVto;
    }

    public void setAnioVto(int anioVto) {
        this.anioVto = anioVto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(int porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }
}
