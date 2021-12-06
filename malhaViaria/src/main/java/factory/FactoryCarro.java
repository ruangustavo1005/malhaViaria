package factory;


import java.util.Random;
import model.Carro;
import model.Malha;
import model.Segmento;

/**
 * @author Leonardo & Ruan
 */
public class FactoryCarro {

    static public Carro createCarro(Segmento segmentoInicial, Malha malha) {
        Carro carro = new Carro();
        
        Random random = new Random();
        
        carro.setVelocidade((30 + random.nextInt(120)) * 10)
             .setTipoCarro(random.nextInt(12))
             .setMalha(malha)
             .setSegmento(segmentoInicial);
        
        segmentoInicial.setCarro(carro);
        
        return carro;
    }
    
}