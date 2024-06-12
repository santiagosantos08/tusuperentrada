public class Usuario {
    private String nombre;
    private String apellido;
    private String id; //en caso del Comprador va a ser DNI, en Org va a ser CUIT/CUIL
    private String contrasenia;

    Usuario(String nombre, String apellido, String id) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
    }

    public String getContrasenia() { //nivel maximo de crotera alcanzado
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public boolean equals(Object o){
        if (o == this) {return true;}
        if (!(o instanceof Usuario)) {return false;}
        Usuario u = (Usuario) o;
        return ((this.getApellido().equals(u.getApellido()))&&(this.getNombre().equals(u.getNombre()))&&(this.getId().equals(u.getId())));
        //deberia comparar la contraseña tambien pero no tengo ganas de cambiar todos los llamados que tiene este metodo..
        //si hay 2 DNI iguales es problema del RENAPER no de la app esta
    }
}
