package model;

import enumerators.EnumSegmento;
import javax.swing.ImageIcon;
import utils.ImageUtils;
import utils.StringUtils;

/**
 * @author Leonardo & Ruan
 */
public class Segmento {

    private EnumSegmento tipo;
    private Carro carro;
    private int posX;
    private int posY;

    public Segmento() {
        
    }

    public EnumSegmento getTipo() {
        return tipo;
    }

    public Segmento setTipo(EnumSegmento tipo) {
        this.tipo = tipo;
        return this;
    }

    public Segmento setTipo(int tipo) {
        for (EnumSegmento enumSegmento : EnumSegmento.values()) {
            if (enumSegmento.getIdentificador() == tipo) {
                this.tipo = enumSegmento;
                break;
            }
        }
        return this;
    }

    public Carro getCarro() {
        return carro;
    }

    public Segmento setCarro(Carro carro) {
        this.carro = carro;
        return this;
    }

    public Segmento limpaCarro() {
        this.carro = null;
        return this;
    }
    
    public boolean hasCarro() {
        return this.carro != null;
    }

    public int getPosX() {
        return posX;
    }

    public Segmento setPosX(int posX) {
        this.posX = posX;
        return this;
    }

    public int getPosY() {
        return posY;
    }

    public Segmento setPosY(int posY) {
        this.posY = posY;
        return this;
    }
    
    public ImageIcon getInstanceImageIcon() {
        if (this.hasCarro()) {
            return this.getCarro().getInstanceImageIcon();
        }
        return ImageUtils.getIconPng("segmento_" + StringUtils.lpad(this.getTipo().getIdentificador(), 2));
    }
    
}