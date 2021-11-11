package view;

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
    public Segmento getValueAt(int rowIndex, int columnIndex) {
        return this.getSegmentos()[rowIndex][columnIndex];
    }

}