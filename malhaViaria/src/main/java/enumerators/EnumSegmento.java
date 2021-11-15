package enumerators;

/**
 * @author Leonardo & Ruan
 */
public enum EnumSegmento {
    NADA(0),
    ESTRADA_CIMA(1),
    ESTRADA_DIRETA(2),
    ESTRADA_BAIXO(3),
    ESTRADA_ESQUERDA(4),
    CRUZAMENTO_CIMA(5),
    CRUZAMENTO_DIRETA(6),
    CRUZAMENTO_BAIXO(7),
    CRUZAMENTO_ESQUERDA(8),
    CRUZAMENTO_CIMA_DIREITA(9),
    CRUZAMENTO_CIMA_ESQUERDA(10),
    CRUZAMENTO_BAIXO_DIREITA(11),
    CRUZAMENTO_BAIXO_ESQUERDA(12);
    
    private final int identificador;
    
    private EnumSegmento(int identificador) {
        this.identificador = identificador;
    }
    
    public int getIdentificador() {
        return this.identificador;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getIdentificador());
    }
    
}
