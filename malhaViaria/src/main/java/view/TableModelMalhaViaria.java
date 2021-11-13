package view;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import model.Segmento;
import utils.StringUtils;

/**
 * @author Leonardo & Ruan
 */
public class TableModelMalhaViaria extends AbstractTableModel {

    private Segmento[][] segmentos;

    public Segmento[][] getSegmentos() {
        return segmentos;
    }

    public void setSegmentos(Segmento[][] segmentos) {
        if (this.segmentos != null && this.segmentos.length > 0) {
            this.fireTableRowsDeleted(0, this.segmentos.length - 1);
        }
        
        this.segmentos = segmentos;
        
        if (this.segmentos != null && this.segmentos.length > 0) {
            this.fireTableRowsInserted(0, this.segmentos.length - 1);
        }
    }
    
    @Override
    public int getRowCount() {
        return this.getSegmentos() != null ? this.getSegmentos().length : 0;
    }

    @Override
    public int getColumnCount() {
        return this.getSegmentos() != null ? this.getSegmentos()[0].length : 0;
    }

    @Override
    public ImageIcon getValueAt(int rowIndex, int columnIndex) {
        int segmento = this.getSegmentos()[rowIndex][columnIndex].getValue();
        String pathImage = "/images/segmento_" + StringUtils.lpad(String.valueOf(segmento), 2, '0') + ".png";
        return new ImageIcon(getClass().getResource(pathImage));
    }

    @Override
    public String getColumnName(int column) {
        return String.valueOf(column);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return ImageIcon.class;
    }

}