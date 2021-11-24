package model;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

/**
 * @author Leonardo & Ruan
 */
public class Malha extends AbstractTableModel {

    private Segmento[][] malha;

    public Malha() {
        
    }
    
    public Malha(int qtdLinhas, int qtdColunas) {
        this(new Segmento[qtdLinhas][qtdColunas]);
    }

    public Malha(Segmento[][] malha) {
        this.malha = malha;
    }

    public Segmento[][] getMalha() {
        return malha;
    }

    public Malha setMalha(Segmento[][] malha) {
        this.malha = malha;
        return this;
    }

    public Segmento getSegmento(int i, int j) {
        return (i >= 0 && i < this.malha.length && j >= 0 && j < this.malha[i].length) ? this.malha[i][j] : null;
    }

    public void resetSegmentos() {
        for (Segmento[] segmentos : this.malha) {
            for (Segmento segmento : segmentos) {
                segmento.setCarro(null);
                this.fireTableCellUpdated(segmento);
            }
        }
    }

    public void setValueAt(Segmento segmento) {
        super.setValueAt(segmento, segmento.getPosY(), segmento.getPosX());
        this.fireTableCellUpdated(segmento);
    }
    
    @Override
    public int getRowCount() {
        return this.malha != null ? this.malha.length : 0;
    }

    @Override
    public int getColumnCount() {
        return this.malha != null ? this.malha[0].length : 0;
    }

    @Override
    public ImageIcon getValueAt(int rowIndex, int columnIndex) {
        return this.getSegmento(rowIndex, columnIndex).getInstanceImageIcon();
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return ImageIcon.class;
    }

    public void fireTableCellUpdated(Segmento segmento) {
        if (segmento != null) {
            this.fireTableCellUpdated(segmento.getPosY(), segmento.getPosX());
        }
    }
    
}