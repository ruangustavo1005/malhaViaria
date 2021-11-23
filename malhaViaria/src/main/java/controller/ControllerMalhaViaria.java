package controller;

import enumerators.EnumSegmento;
import factory.FactoryCarro;
import java.util.ArrayList;
import java.util.Random;
import model.Carro;
import model.Segmento;

/**
 * @author Leonardo & Ruan
 */
public class ControllerMalhaViaria extends Thread {

    private static ControllerMalhaViaria instance;
    
    private ArrayList<Segmento> entradas;
    
    private Random random;
    
    private int rowCount;
    private int columnCount;
    private int cooldownEntradaCarros;
    private int maxCarrosSimultaneos;
    private boolean emExecucao;
    private long ultimaAtualizacao;
    private int qtdCarrosAndando;
    
    private ControllerMalhaViaria() {
        this.qtdCarrosAndando = 0;
        this.random = new Random();
    }

    public static ControllerMalhaViaria getInstance() {
        if (ControllerMalhaViaria.instance == null) {
            ControllerMalhaViaria.instance = new ControllerMalhaViaria();
        }
        return instance;
    }

    @Override
    public void run() {
        this.emExecucao = true;

        this.loadEntradasFromMalha();
        
        while (this.isEmExecucao()) {
            if (this.getQtdCarrosAndando() < this.getMaxCarrosSimultaneos()) {
                long tempoAtual = System.currentTimeMillis();
                
                if (tempoAtual >= (this.ultimaAtualizacao + this.cooldownEntradaCarros)) {
                    Segmento entradaAleatoria;
                    
                    do {
                        entradaAleatoria = this.getEntradas().get(this.getRandom().nextInt(this.getEntradas().size()));
                    }
                    while (entradaAleatoria.hasCarro());
                    
                    Carro carro = FactoryCarro.createCarro(entradaAleatoria);
                    
                    ControllerIndex.getInstance().atualizaSegmento(entradaAleatoria)
                                                 .atualizaProgressBar(1);
                    
                    carro.start();
                    
                    this.qtdCarrosAndando++;
                    this.ultimaAtualizacao = tempoAtual;
                }
            }
        }
    }
    
    public void startMalhaViaria() {
        if (this.isAlive()) {
            this.resume();
        }
        else {
            this.start();
        }
    }
    
    public void stopMalhaViaria() {
        this.suspend();
    }
    
    public void interruptMalhaViaria() {
        this.emExecucao = false;
        ControllerMalhaViaria.instance = null;
    }

    private ControllerMalhaViaria loadEntradasFromMalha() {
        this.entradas = new ArrayList<>();
        Segmento[][] malha = this.getMalha();
        
        if (malha != null && malha.length > 0 && malha[0].length > 0) {
            for (Segmento[] segmentos : malha) {
                if (segmentos[0].getTipo().compareTo(EnumSegmento.ESTRADA_DIRETA) == 0) {
                    this.getEntradas().add(segmentos[0]);
                }
                if (segmentos[segmentos.length - 1].getTipo().compareTo(EnumSegmento.ESTRADA_ESQUERDA) == 0) {
                    this.getEntradas().add(segmentos[segmentos.length - 1]);
                }
            }
            
            for (int i = 1; i < malha[0].length - 1; i++) {
                if (malha[0][i].getTipo().compareTo(EnumSegmento.ESTRADA_BAIXO) == 0) {
                    this.getEntradas().add(malha[0][i]);
                }
                if (malha[malha.length - 1][i].getTipo().compareTo(EnumSegmento.ESTRADA_CIMA) == 0) {
                    this.getEntradas().add(malha[malha.length - 1][i]);
                }
            }
        }
        
        return this;
    }
    
    public Segmento[][] getMalha() {
        return ControllerIndex.getInstance().getView().getTableModel().getSegmentos();
    }
    
    public int getRowCount() {
        return rowCount;
    }

    public ControllerMalhaViaria setRowCount(int rowCount) {
        this.rowCount = rowCount;
        return this;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public ControllerMalhaViaria setColumnCount(int columnCount) {
        this.columnCount = columnCount;
        return this;
    }

    public int getCooldownEntradaCarros() {
        return cooldownEntradaCarros;
    }

    public ControllerMalhaViaria setCooldownEntradaCarros(int cooldownEntradaCarros) {
        this.cooldownEntradaCarros = cooldownEntradaCarros;
        return this;
    }

    public int getMaxCarrosSimultaneos() {
        return maxCarrosSimultaneos;
    }

    public ControllerMalhaViaria setMaxCarrosSimultaneos(int maxCarrosSimultaneos) {
        this.maxCarrosSimultaneos = maxCarrosSimultaneos;
        return this;
    }

    public ArrayList<Segmento> getEntradas() {
        return entradas;
    }

    public Random getRandom() {
        return random;
    }

    public boolean isEmExecucao() {
        return emExecucao;
    }

    public long getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public int getQtdCarrosAndando() {
        return qtdCarrosAndando;
    }
    
}