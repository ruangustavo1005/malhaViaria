package model;

import enumerators.EnumDirecaoCarro;
import enumerators.EnumSegmento;
import javax.swing.ImageIcon;
import utils.ImageUtils;
import utils.StringUtils;

/**
 * @author Leonardo & Ruan
 */
public class Carro extends Thread {

    /**
     * Tempo de espera entre um movimento e outro, em milissegundos
     */
    private int velocidade;
    /**
     * Valor entre 0 e 11
     */
    private int tipoCarro;
    private EnumDirecaoCarro direcao;
    private Segmento segmento;
    
    private boolean emExecucao;

    public Carro() {
        
    }

    @Override
    public void run() {
        this.emExecucao = true;

        while (this.isEmExecucao()) {
            
        }
    }

    public int getVelocidade() {
        return velocidade;
    }

    public Carro setVelocidade(int velocidade) {
        this.velocidade = velocidade;
        return this;
    }

    public int getTipoCarro() {
        return tipoCarro;
    }

    public Carro setTipoCarro(int tipoCarro) {
        this.tipoCarro = tipoCarro < 0 ? 0 : (tipoCarro > 11 ? 11 : tipoCarro);
        return this;
    }

    public EnumDirecaoCarro getDirecao() {
        return direcao;
    }

    public Carro setDirecao(EnumDirecaoCarro direcao) {
        this.direcao = direcao;
        return this;
    }

    public Segmento getSegmento() {
        return segmento;
    }

    public Carro setSegmento(Segmento segmento) {
        this.setDirecaoBySegmento(segmento);
        this.segmento = segmento;
        return this;
    }

    public Carro setDirecaoBySegmento(Segmento segmento) {
        EnumSegmento tipoSegmento = segmento.getTipo();
        
        if (tipoSegmento.compareTo(EnumSegmento.ESTRADA_CIMA) == 0) {
            this.setDirecao(EnumDirecaoCarro.CIMA);
        }
        else if (tipoSegmento.compareTo(EnumSegmento.ESTRADA_DIRETA) == 0) {
            this.setDirecao(EnumDirecaoCarro.DIREITA);
        }
        else if (tipoSegmento.compareTo(EnumSegmento.ESTRADA_BAIXO) == 0) {
            this.setDirecao(EnumDirecaoCarro.BAIXO);
        }
        else if (tipoSegmento.compareTo(EnumSegmento.ESTRADA_ESQUERDA) == 0) {
            this.setDirecao(EnumDirecaoCarro.ESQUERDA);
        }
        
        return this;
    }

    public boolean isEmExecucao() {
        return emExecucao;
    }
    
    public ImageIcon getInstanceImageIcon() {
        return ImageUtils.getIconPng("carro_" + StringUtils.lpad(this.getTipoCarro(), 2) + "_direcao_" + this.getDirecao());
    }
    
}