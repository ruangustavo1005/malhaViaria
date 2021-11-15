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
    
    private Segmento[][] malha;
    
    private ArrayList<Integer> entradasPosX;
    private ArrayList<Integer> entradasPosY;
    
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

        while (this.isEmExecucao()) {
            if (this.getQtdCarrosAndando() <= this.getMaxCarrosSimultaneos()) {
                long tempoAtual = System.currentTimeMillis();
                
                if (tempoAtual >= (this.ultimaAtualizacao + this.cooldownEntradaCarros)) {
                    Segmento entradaAleatoria;
                    int posXEntrada, posYEntrada;
                    
                    do {
                        long criterioEscolhaEntrada = this.getRandom().nextInt(this.getEntradasPosX().size());
                        
                        posYEntrada = this.getEntradasPosY().get((int) criterioEscolhaEntrada);
                        posXEntrada = this.getEntradasPosX().get((int) criterioEscolhaEntrada);
                        
                        entradaAleatoria = this.malha[posYEntrada][posXEntrada];
                    }
                    while (entradaAleatoria.hasCarro());
                    
                    Carro carro = FactoryCarro.createCarro(entradaAleatoria);
                    
                    ControllerIndex.getInstance().atualizaSegmento(entradaAleatoria, posYEntrada, posXEntrada)
                                                 .atualizaProgressBar(1);
                    
                    carro.start();
                    
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

    private ControllerMalhaViaria loadEntradasFromMalha(Segmento[][] malha) {
        this.entradasPosX = new ArrayList<>();
        this.entradasPosY = new ArrayList<>();
        
        if (malha != null && malha.length > 0 && malha[0].length > 0) {
            for (int i = 0; i < malha.length; i++) {
                Segmento[] segmentos = malha[i];
                
                if (segmentos[0].getTipo().compareTo(EnumSegmento.ESTRADA_DIRETA) == 0) {
                    this.getEntradasPosX().add(0);
                    this.getEntradasPosY().add(i);
                }
                if (segmentos[segmentos.length - 1].getTipo().compareTo(EnumSegmento.ESTRADA_ESQUERDA) == 0) {
                    this.getEntradasPosX().add(segmentos.length - 1);
                    this.getEntradasPosY().add(i);
                }
            }
            
            for (int i = 1; i < malha[0].length - 1; i++) {
                if (malha[0][i].getTipo().compareTo(EnumSegmento.ESTRADA_BAIXO) == 0) {
                    this.getEntradasPosX().add(i);
                    this.getEntradasPosY().add(0);
                }
                if (malha[malha.length - 1][i].getTipo().compareTo(EnumSegmento.ESTRADA_CIMA) == 0) {
                    this.getEntradasPosX().add(i);
                    this.getEntradasPosY().add(malha.length - 1);
                }
            }
        }
        
        return this;
    }
    
    public Segmento[][] getMalha() {
        return malha;
    }

    public ControllerMalhaViaria setMalha(Segmento[][] malha) {
        this.malha = malha;
        return this.loadEntradasFromMalha(malha);
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

    public ArrayList<Integer> getEntradasPosX() {
        return entradasPosX;
    }

    public ArrayList<Integer> getEntradasPosY() {
        return entradasPosY;
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