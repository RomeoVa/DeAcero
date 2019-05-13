package mx.itesm.csf.deacero;

import java.io.Serializable;

public class Trituradora implements Serializable {

    private int Toneladas;
    private String Fecha;
    private String Trituradora;

    public int getToneladas() {

        return Toneladas;
    }
    public void setToneladas (int Toneladas) {
        this.Toneladas = Toneladas;
    }

    public String getFecha() {

        return Fecha;
    }
    public void setFecha(String Fecha) {

        this.Fecha = Fecha;
    }

    public String getTrituradora() {

        return Trituradora;
    }
    public void setTrituradora(String Trituradora) {

        this.Trituradora = Trituradora;
    }
}