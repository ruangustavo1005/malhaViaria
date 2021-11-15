package utils;

import javax.swing.ImageIcon;

/**
 * @author Leonardo & Ruan
 */
public class ImageUtils {

    public static ImageIcon getIconPng(String fileName) {
        String pathImage = "/images/" + fileName + ".png";
        return new ImageIcon(Object.class.getResource(pathImage));
    }
    
}