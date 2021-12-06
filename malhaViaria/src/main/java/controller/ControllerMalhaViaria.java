package controller;

import enumerators.EnumSegmento;
import factory.FactoryCarro;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Carro;
import model.Malha;
import model.Segmento;

/**
 * @author Leonardo & Ruan
 */
public class ControllerMalhaViaria extends Thread {

    private static ControllerMalhaViaria instance;
    
    private ArrayList<Segmento> entradas;
    private ArrayList<Carro> carrosAndando;
    
    private final Random random;
    
    private int rowCount;
    private int columnCount;
    private int cooldownEntradaCarros;
    private int maxCarrosSimultaneos;
    private boolean emExecucao;
    private int qtdCarrosAndando;
    
    private ControllerMalhaViaria() {
        this.qtdCarrosAndando = 0;
        this.random = new Random();
    }

    public synchronized static ControllerMalhaViaria getInstance() {
        if (ControllerMalhaViaria.instance == null) {
            ControllerMalhaViaria.instance = new ControllerMalhaViaria();
        }
        return instance;
    }

    @Override
    public void run() {
        this.emExecucao = true;

        this.loadEntradasFromMalha();
        
        this.carrosAndando = new ArrayList<>();
        
        while (this.isEmExecucao()) {
            if (this.getQtdCarrosAndando() < this.getMaxCarrosSimultaneos()) {
                try {
                    Segmento entradaAleatoria;

                    do {
                        entradaAleatoria = this.getEntradas().get(this.getRandom().nextInt(this.getEntradas().size()));
                    }
                    while (entradaAleatoria.hasCarro());

                    Carro carro = FactoryCarro.createCarro(entradaAleatoria, this.getMalha());
                    this.carrosAndando.add(carro);
                    carro.start();

                    this.incrementaQtdCarrosAndando();
                    
                    ControllerMalhaViaria.sleep(this.getCooldownEntradaCarros());
                }
                catch (InterruptedException ex) {
                    Logger.getLogger(ControllerMalhaViaria.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void startMalhaViaria() {
        if (this.isAlive()) {
            this.resume();
            this.carrosAndando.forEach(Carro::resume);
        }
        else {
            this.start();
        }
    }
    
    public void stopMalhaViaria() {
        this.suspend();
        this.carrosAndando.forEach(Carro::suspend);
    }
    
    public void interruptMalhaViaria() {
        this.emExecucao = false;
        this.carrosAndando.forEach((Carro carro) -> {
            carro.stopCarro();
            if (carro.getSegmento() != null) {
                carro.getSegmento().limpaCarro();
            }
        });
        ControllerMalhaViaria.instance = null;
    }

    private ControllerMalhaViaria loadEntradasFromMalha() {
        this.entradas = new ArrayList<>();
        Segmento[][] malha = this.getMalha().getMalha();
        
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
    
    public Malha getMalha() {
        return ControllerIndex.getInstance().getMalha();
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

    public synchronized int getQtdCarrosAndando() {
        return qtdCarrosAndando;
    }

    public ControllerMalhaViaria setQtdCarrosAndando(int qtdCarrosAndando) {
        this.qtdCarrosAndando = qtdCarrosAndando;
        return this;
    }
    
    public synchronized ControllerMalhaViaria decrementaQtdCarrosAndando() {
        this.qtdCarrosAndando--;
        ControllerIndex.getInstance().atualizaProgressBar(-1);
        return this;
    }
    
    
    public synchronized ControllerMalhaViaria incrementaQtdCarrosAndando() {
        this.qtdCarrosAndando++;
        ControllerIndex.getInstance().atualizaProgressBar(1);
        return this;
    }
    
}