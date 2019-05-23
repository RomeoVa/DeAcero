package mx.itesm.csf.deacero;
import java.io.Serializable;

public class Usuario implements Serializable {

    private String id;
    private String indice;
    private String Nombre;
    private String Appaterno;
    private String Apmaterno;

    private String usuario;
    private String password;
    private String Rol;

    public String getId(){ return id; }

    public void setId(String id){ this.id = id; }

    public String getIndice(){ return indice; }

    public void setIndice(String indice){ this.indice = indice; }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getAppaterno() {
        return Appaterno;
    }

    public void setAppaterno(String Appaterno) {
        this.Appaterno = Appaterno;
    }

    public String getApmaterno() {
        return Apmaterno;
    }

    public void setApmaterno(String Apmaterno) {
        this.Apmaterno = Apmaterno;
    }


    public String getusuario() {
        return usuario;
    }

    public void setusuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String Rol) {
        this.Rol = Rol;
    }


    private static final Usuario info = new Usuario();

    public static Usuario getInstance() {
        return info;
    }

}
