package view;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import model.Segmento;

/**
 * @author Leonardo & Ruan
 */
public class TableModelMalhaViaria extends AbstractTableModel {

    private Segmento[][] segmentos;

    public Segmento[][] getSegmentos() {
        return segmentos;
    }

    public void setSegmentos(Segmento[][] segmentos) {
        this.segmentos = new Segmento[segmentos.length][segmentos[0].length];
        
        for (int i = 0; i < this.segmentos.length; i++) {
            for (int j = 0; j < this.segmentos[i].length; j++) {
                this.setValueAt(segmentos[i][j].clona(), i, j);
            }
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
        return this.getSegmentos()[rowIndex][columnIndex].getInstanceImageIcon();
    }

    public TableModelMalhaViaria setValueAt(Segmento segmento) {
        return this.setValueAt(segmento, segmento.getPosY(), segmento.getPosX());
    }
    
    public TableModelMalhaViaria setValueAt(Segmento segmento, int i, int j) {
        this.segmentos[i][j] = segmento;
        this.fireTableCellUpdated(i, j);
        return this;
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