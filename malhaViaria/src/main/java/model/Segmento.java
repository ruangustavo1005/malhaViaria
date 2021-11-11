package model;

/**
 * @author Leonardo & Ruan
 */
public class Segmento {

    private int value;

    public Segmento(int value) {
        this.value = value;
    }

    public Segmento() {
        
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getValue());
    }
    
}