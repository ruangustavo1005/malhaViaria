package factory;


import java.util.Random;
import model.Carro;
import model.Segmento;

/**
 * @author Leonardo & Ruan
 */
public class FactoryCarro {

    static public Carro createCarro(Segmento segmentoInicial) {
        Carro carro = new Carro();
        
        Random random = new Random();
        
        carro.setVelocidade(500 + random.nextInt(1001))
             .setTipoCarro(random.nextInt(12))
             .setSegmento(segmentoInicial);
        
        segmentoInicial.setCarro(carro);
        
        return carro;
    }
    
}