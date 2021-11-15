package enumerators;

/**
 * @author Leonardo & Ruan
 */
public enum EnumDirecaoCarro {
    CIMA(0),
    DIREITA(1),
    BAIXO(2),
    ESQUERDA(3);
    
    private final int identificador;
    
    private EnumDirecaoCarro(int identificador) {
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
