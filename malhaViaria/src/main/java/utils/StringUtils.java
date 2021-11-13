package utils;

/**
 * @author Leonardo & Ruan
 */
public class StringUtils {
    
    static public int PAD_LEFT  = 1;
    static public int PAD_RIGHT = 2;
    
    public static String ucfirst(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    
    public static String removeChars(String texto, char... chars) {
        for (char caracter : chars) {
            int index = texto.indexOf(caracter);
            while (index >= 0) {
                texto = texto.substring(0, index).concat(texto.substring(index + 1));
                index = texto.indexOf(caracter);
            }
        }
        return texto;
    }
    
    public static String lpad(String texto, int tamanho) {
        return lpad(texto, tamanho, '0');
    }
    
    public static String lpad(String texto, int tamanho, char caracter) {
        return pad(texto, tamanho, caracter, PAD_LEFT);
    }
    
    public static String rpad(String texto, int tamanho) {
        return rpad(texto, tamanho, '0');
    }
    
    public static String rpad(String texto, int tamanho, char caracter) {
        return pad(texto, tamanho, caracter, PAD_RIGHT);
    }
    
    public static String pad(String texto, int tamanho, char caracter, int lado) {
        StringBuilder stringBuilder = new StringBuilder();
        if (lado == PAD_RIGHT) {
            stringBuilder.append(texto);
        }
        while (lado == PAD_LEFT  && stringBuilder.length() < tamanho - texto.length()
            || lado == PAD_RIGHT && stringBuilder.length() < tamanho) {
            stringBuilder.append(caracter);
        }
        if (lado == PAD_LEFT) {
            stringBuilder.append(texto);
        }
        return stringBuilder.toString();
    }
    
}