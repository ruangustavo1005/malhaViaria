package model;

import controller.ControllerMalhaViaria;
import enumerators.EnumDirecaoCarro;
import enumerators.EnumSegmento;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Malha malha;
    
    private boolean emExecucao;

    public Carro() {
        
    }

    @Override
    public void run() {
        this.emExecucao = true;

        while (this.isEmExecucao()) {
            try {
                Carro.sleep(this.getVelocidade() / 2);
                
                if (!this.emExecucao) {
                    break;
                }
                
                boolean podeGirar = this.podeGirar();
                boolean deveGirar = this.deveGirar();
                
                if (podeGirar && (deveGirar || (new Random()).nextBoolean())) {
                    this.gira();
                }
                
                Carro.sleep(this.getVelocidade() / 2);
                
                if (!this.emExecucao) {
                    break;
                }
                
                Segmento segmentoAFrente = this.getSegmentoAFrente(this.getDirecao());
                
                if (segmentoAFrente != null) {
                    while (!this.podeAndar(segmentoAFrente) && !this.podeUltrapassar(segmentoAFrente)) {
                        Carro.sleep(10);
                        
                        if (!this.emExecucao) {
                            break;
                        }
                        
                        if (podeGirar && !deveGirar) {
                            this.gira();
                            segmentoAFrente = this.getSegmentoAFrente(this.getDirecao());
                        }
                    }
                }
                
                this.getSegmento().setCarro(null);
                
                Segmento segmentoDisponivel = null;
                
                if (segmentoAFrente != null) {
                    if (this.podeAndar(segmentoAFrente)) {
                        segmentoDisponivel = segmentoAFrente.setCarro(this);
                    }
                    else if (this.podeUltrapassar(segmentoAFrente)) {
                        segmentoDisponivel = this.getSegmentoDiagonal(segmentoAFrente);
                    }
                }
                
                this.setSegmento(segmentoDisponivel);
                
                if (this.getSegmento() == null) {
                    break;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Carro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        ControllerMalhaViaria.getInstance().decrementaQtdCarrosAndando();
    }
    
    public Carro stopCarro() {
        this.emExecucao = false;
        return this;
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
        if (segmento != null) {
            this.setDirecaoBySegmento(segmento);
        }
        this.getMalha().fireTableCellUpdated(this.segmento);
        this.segmento = segmento;
        this.getMalha().fireTableCellUpdated(segmento);
        return this;
    }

    public Malha getMalha() {
        return malha;
    }

    public Carro setMalha(Malha malha) {
        this.malha = malha;
        return this;
    }

    public Carro setDirecaoBySegmento(Segmento segmento) {
        EnumSegmento tipoSegmento = segmento.getTipo();
        
        if (tipoSegmento.compareTo(EnumSegmento.ESTRADA_CIMA) == 0
         || tipoSegmento.compareTo(EnumSegmento.CRUZAMENTO_CIMA) == 0) {
            this.setDirecao(EnumDirecaoCarro.CIMA);
        }
        else if (tipoSegmento.compareTo(EnumSegmento.ESTRADA_DIRETA) == 0
              || tipoSegmento.compareTo(EnumSegmento.CRUZAMENTO_DIRETA) == 0) {
            this.setDirecao(EnumDirecaoCarro.DIREITA);
        }
        else if (tipoSegmento.compareTo(EnumSegmento.ESTRADA_BAIXO) == 0
              || tipoSegmento.compareTo(EnumSegmento.CRUZAMENTO_BAIXO) == 0) {
            this.setDirecao(EnumDirecaoCarro.BAIXO);
        }
        else if (tipoSegmento.compareTo(EnumSegmento.ESTRADA_ESQUERDA) == 0
              || tipoSegmento.compareTo(EnumSegmento.CRUZAMENTO_ESQUERDA) == 0) {
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
    
    private Segmento getSegmentoAFrente(EnumDirecaoCarro direcaoCarro) {
        int i = this.getSegmento().getPosY();
        int j = this.getSegmento().getPosX();
        
        if (this.isDirecaoCima(direcaoCarro)) {
            i--;
        }
        else if (this.isDirecaoDireita(direcaoCarro)) {
            j++;
        }
        else if (this.isDirecaoBaixo(direcaoCarro)) {
            i++;
        }
        else if (this.isDirecaoEsquerda(direcaoCarro)) {
            j--;
        }
        
        return this.getMalha().getSegmento(i, j);
    }
    
    private boolean isDirecaoCima() {
        return this.isDirecaoCima(this.getDirecao());
    }
    
    private boolean isDirecaoCima(EnumDirecaoCarro direcaoCarro) {
        return direcaoCarro.compareTo(EnumDirecaoCarro.CIMA) == 0;
    }
    
    private boolean isDirecaoDireita() {
        return this.isDirecaoDireita(this.getDirecao());
    }
    
    private boolean isDirecaoDireita(EnumDirecaoCarro direcaoCarro) {
        return direcaoCarro.compareTo(EnumDirecaoCarro.DIREITA) == 0;
    }
    
    private boolean isDirecaoBaixo() {
        return this.isDirecaoBaixo(this.getDirecao());
    }
    
    private boolean isDirecaoBaixo(EnumDirecaoCarro direcaoCarro) {
        return direcaoCarro.compareTo(EnumDirecaoCarro.BAIXO) == 0;
    }
    
    private boolean isDirecaoEsquerda() {
        return this.isDirecaoEsquerda(this.getDirecao());
    }
    
    private boolean isDirecaoEsquerda(EnumDirecaoCarro direcaoCarro) {
        return direcaoCarro.compareTo(EnumDirecaoCarro.ESQUERDA) == 0;
    }
    
    public Segmento getSegmentoADiagonalEsquerda(Segmento segmentoAFrente) {
        int i = segmentoAFrente.getPosY();
        int j = segmentoAFrente.getPosX();
        
        if (this.isDirecaoCima()) {
            j--;
        }
        else if (this.isDirecaoDireita()) {
            i--;
        }
        else if (this.isDirecaoBaixo()) {
            j++;
        }
        else if (this.isDirecaoEsquerda()) {
            i++;
        }
        
        return this.getMalha().getSegmento(i, j);
    }
    
    public Segmento getSegmentoADiagonalDireita(Segmento segmentoAFrente) {
        int i = segmentoAFrente.getPosY();
        int j = segmentoAFrente.getPosX();
        
        if (this.isDirecaoCima()) {
            j++;
        }
        else if (this.isDirecaoDireita()) {
            i++;
        }
        else if (this.isDirecaoBaixo()) {
            j--;
        }
        else if (this.isDirecaoEsquerda()) {
            i--;
        }
        
        return this.getMalha().getSegmento(i, j);
    }
    
    private boolean deveGirar() {
        Segmento segmentoACima     = this.getSegmentoAFrente(EnumDirecaoCarro.CIMA);
        Segmento segmentoADireita  = this.getSegmentoAFrente(EnumDirecaoCarro.DIREITA);
        Segmento segmentoABaixo    = this.getSegmentoAFrente(EnumDirecaoCarro.BAIXO);
        Segmento segmentoAEsquerda = this.getSegmentoAFrente(EnumDirecaoCarro.ESQUERDA);
        
        boolean isSegmentoACimaValido = segmentoACima != null
                                     && (segmentoACima.getTipo().compareTo(EnumSegmento.ESTRADA_CIMA) == 0
                                      || segmentoACima.getTipo().compareTo(EnumSegmento.CRUZAMENTO_CIMA) == 0
                                      || segmentoACima.getTipo().compareTo(EnumSegmento.CRUZAMENTO_CIMA_DIREITA) == 0
                                      || segmentoACima.getTipo().compareTo(EnumSegmento.CRUZAMENTO_CIMA_ESQUERDA) == 0);
        
        boolean isSegmentoADireitaValido = segmentoADireita != null
                                        && (segmentoADireita.getTipo().compareTo(EnumSegmento.ESTRADA_DIRETA) == 0
                                         || segmentoADireita.getTipo().compareTo(EnumSegmento.CRUZAMENTO_DIRETA) == 0
                                         || segmentoADireita.getTipo().compareTo(EnumSegmento.CRUZAMENTO_CIMA_DIREITA) == 0
                                         || segmentoADireita.getTipo().compareTo(EnumSegmento.CRUZAMENTO_BAIXO_DIREITA) == 0);
        
        boolean isSegmentoABaixoValido = segmentoABaixo != null
                                      && (segmentoABaixo.getTipo().compareTo(EnumSegmento.ESTRADA_BAIXO) == 0
                                       || segmentoABaixo.getTipo().compareTo(EnumSegmento.CRUZAMENTO_BAIXO) == 0
                                       || segmentoABaixo.getTipo().compareTo(EnumSegmento.CRUZAMENTO_BAIXO_DIREITA) == 0
                                       || segmentoABaixo.getTipo().compareTo(EnumSegmento.CRUZAMENTO_BAIXO_ESQUERDA) == 0);
        
        boolean isSegmentoAEsquerdaValido = segmentoAEsquerda != null
                                         && (segmentoAEsquerda.getTipo().compareTo(EnumSegmento.ESTRADA_ESQUERDA) == 0
                                          || segmentoAEsquerda.getTipo().compareTo(EnumSegmento.CRUZAMENTO_ESQUERDA) == 0
                                          || segmentoAEsquerda.getTipo().compareTo(EnumSegmento.CRUZAMENTO_CIMA_ESQUERDA) == 0
                                          || segmentoAEsquerda.getTipo().compareTo(EnumSegmento.CRUZAMENTO_BAIXO_ESQUERDA) == 0);
        
        return (this.getSegmento().getTipo().compareTo(EnumSegmento.CRUZAMENTO_CIMA_DIREITA) == 0
             && (isSegmentoACimaValido ^ isSegmentoADireitaValido))
            || (this.getSegmento().getTipo().compareTo(EnumSegmento.CRUZAMENTO_CIMA_ESQUERDA) == 0
             && (isSegmentoACimaValido ^ isSegmentoAEsquerdaValido))
            || (this.getSegmento().getTipo().compareTo(EnumSegmento.CRUZAMENTO_BAIXO_DIREITA) == 0
             && (isSegmentoABaixoValido ^ isSegmentoADireitaValido))
            || (this.getSegmento().getTipo().compareTo(EnumSegmento.CRUZAMENTO_BAIXO_ESQUERDA) == 0
             && (isSegmentoABaixoValido ^ isSegmentoAEsquerdaValido));
    }
    
    private boolean podeGirar() {
        return this.getSegmento().getTipo().compareTo(EnumSegmento.CRUZAMENTO_CIMA_DIREITA) == 0
            || this.getSegmento().getTipo().compareTo(EnumSegmento.CRUZAMENTO_CIMA_ESQUERDA) == 0
            || this.getSegmento().getTipo().compareTo(EnumSegmento.CRUZAMENTO_BAIXO_DIREITA) == 0
            || this.getSegmento().getTipo().compareTo(EnumSegmento.CRUZAMENTO_BAIXO_ESQUERDA) == 0;
    }
    
    private void gira() {
        if (this.getSegmento().getTipo().compareTo(EnumSegmento.CRUZAMENTO_CIMA_DIREITA) == 0) {
            if (this.getDirecao().compareTo(EnumDirecaoCarro.CIMA) == 0) {
                this.setDirecao(EnumDirecaoCarro.DIREITA);
            }
            else {
                this.setDirecao(EnumDirecaoCarro.CIMA);
            }
        }
        else if (this.getSegmento().getTipo().compareTo(EnumSegmento.CRUZAMENTO_CIMA_ESQUERDA) == 0) {
            if (this.getDirecao().compareTo(EnumDirecaoCarro.CIMA) == 0) {
                this.setDirecao(EnumDirecaoCarro.ESQUERDA);
            }
            else {
                this.setDirecao(EnumDirecaoCarro.CIMA);
            }
        }
        else if (this.getSegmento().getTipo().compareTo(EnumSegmento.CRUZAMENTO_BAIXO_DIREITA) == 0) {
            if (this.getDirecao().compareTo(EnumDirecaoCarro.BAIXO) == 0) {
                this.setDirecao(EnumDirecaoCarro.DIREITA);
            }
            else {
                this.setDirecao(EnumDirecaoCarro.BAIXO);
            }
        }
        else if (this.getSegmento().getTipo().compareTo(EnumSegmento.CRUZAMENTO_BAIXO_ESQUERDA) == 0) {
            if (this.getDirecao().compareTo(EnumDirecaoCarro.BAIXO) == 0) {
                this.setDirecao(EnumDirecaoCarro.ESQUERDA);
            }
            else {
                this.setDirecao(EnumDirecaoCarro.BAIXO);
            }
        }
        
        this.getMalha().fireTableCellUpdated(this.getSegmento());
    }
    
    private synchronized boolean podeAndar(Segmento segmentoAFrente) {
        return !segmentoAFrente.hasCarro();
    }
    
    private boolean podeUltrapassar(Segmento segmentoAFrente) {
        if (this.getSegmento().getTipo().compareTo(EnumSegmento.ESTRADA_CIMA) != 0
         && this.getSegmento().getTipo().compareTo(EnumSegmento.ESTRADA_DIRETA) != 0
         && this.getSegmento().getTipo().compareTo(EnumSegmento.ESTRADA_BAIXO) != 0
         && this.getSegmento().getTipo().compareTo(EnumSegmento.ESTRADA_ESQUERDA) != 0) {
            return false;
        }
        
        Segmento segmentoADiagonalDireita  = this.getSegmentoADiagonalDireita(segmentoAFrente);
        Segmento segmentoADiagonalEsquerda = this.getSegmentoADiagonalEsquerda(segmentoAFrente);
        
        return (segmentoADiagonalDireita != null
             && segmentoADiagonalDireita.getTipo().equals(segmentoAFrente.getTipo())
             && this.podeAndar(segmentoADiagonalDireita))
            || (segmentoADiagonalEsquerda != null
             && segmentoADiagonalEsquerda.getTipo().equals(segmentoAFrente.getTipo())
             && this.podeAndar(segmentoADiagonalEsquerda));
    }
    
    private Segmento getSegmentoDiagonal(Segmento segmentoAFrente) {
        Segmento segmentoDiagonal = null;
        
        Segmento segmentoADiagonalDireita  = this.getSegmentoADiagonalDireita(segmentoAFrente);
        Segmento segmentoADiagonalEsquerda = this.getSegmentoADiagonalEsquerda(segmentoAFrente);
        
        if (segmentoADiagonalDireita != null && segmentoADiagonalEsquerda != null) {
            segmentoDiagonal = (new Random().nextBoolean()) ? segmentoADiagonalDireita : segmentoADiagonalEsquerda;
        }
        else if (segmentoADiagonalDireita != null) {
            segmentoDiagonal = segmentoADiagonalDireita;
        }
        else if (segmentoADiagonalEsquerda != null) {
            segmentoDiagonal = segmentoADiagonalEsquerda;
        }
        
        return segmentoDiagonal;
    }
    
}