package view;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * @author Leonardo & Ruan
 */
public class ViewIndex extends javax.swing.JFrame {

    private TableModelMalhaViaria tableModel;
    
    public ViewIndex() {
        this.setTableModel(new TableModelMalhaViaria());
        initComponents();
        this.getTable().setRowMargin(0);
        this.getTable().setShowGrid(false);
        this.getTable().getTableHeader().setUI(null);
    }

    public TableModelMalhaViaria getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModelMalhaViaria tableModel) {
        this.tableModel = tableModel;
    }

    public JButton getButtonChooseFile() {
        return buttonChooseFile;
    }

    public JTextField getFieldChoosedFile() {
        return fieldChoosedFile;
    }

    public JTable getTable() {
        return table;
    }

    public JButton getButtonInterrupt() {
        return buttonInterrupt;
    }

    public JButton getButtonStart() {
        return buttonStart;
    }

    public JButton getButtonStop() {
        return buttonStop;
    }

    public JTextField getFieldTempoEspera() {
        return fieldTempoEspera;
    }

    public JTextField getFieldTotalCarros() {
        return fieldTotalCarros;
    }

    public JProgressBar getProgressBarActiveCars() {
        return progressBarActiveCars;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        fieldChoosedFile = new javax.swing.JTextField();
        buttonChooseFile = new javax.swing.JButton();
        fieldTotalCarros = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        fieldTempoEspera = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        buttonStart = new javax.swing.JButton();
        buttonStop = new javax.swing.JButton();
        buttonInterrupt = new javax.swing.JButton();
        progressBarActiveCars = new javax.swing.JProgressBar();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Malha Viária - Leonardo Alex Fusinato e Ruan Gustavo Moretti");

        table.setModel(this.getTableModel());
        table.setRowHeight(30);
        jScrollPane1.setViewportView(table);

        jLabel1.setText("Arquivo selecionado:");

        fieldChoosedFile.setEditable(false);

        buttonChooseFile.setText("Selecionar arquivo");

        jLabel2.setText("Total de Carros Simultâneos:");

        jLabel3.setText("Tempo de Espera (em milissegundos):");

        buttonStart.setText("Começar a Simulação");

        buttonStop.setText("Parar a Simulação");

        buttonInterrupt.setText("Interromper a Simulação");

        progressBarActiveCars.setStringPainted(true);

        jLabel4.setText("Quantidade de Carros Ativos:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fieldChoosedFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonChooseFile))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fieldTempoEspera, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(buttonInterrupt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonStop)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonStart))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fieldTotalCarros, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(progressBarActiveCars, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fieldChoosedFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonChooseFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(fieldTotalCarros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4))
                    .addComponent(progressBarActiveCars, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldTempoEspera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(buttonStart)
                    .addComponent(buttonStop)
                    .addComponent(buttonInterrupt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 770, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonChooseFile;
    private javax.swing.JButton buttonInterrupt;
    private javax.swing.JButton buttonStart;
    private javax.swing.JButton buttonStop;
    private javax.swing.JTextField fieldChoosedFile;
    private javax.swing.JTextField fieldTempoEspera;
    private javax.swing.JTextField fieldTotalCarros;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JProgressBar progressBarActiveCars;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
