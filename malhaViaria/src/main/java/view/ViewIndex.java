package view;

import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * @author Leonardo & Ruan
 */
public class ViewIndex extends javax.swing.JFrame {

    private TableModelMalhaViaria tableModel;
    
    public ViewIndex() {
        this.setTableModel(new TableModelMalhaViaria());
        initComponents();
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        fieldChoosedFile = new javax.swing.JTextField();
        buttonChooseFile = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Malha Viária - Leonardo Alex Fusinato e Ruan Gustavo Moretti");

        jTable1.setModel(this.getTableModel());
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Arquivo selecionado:");

        fieldChoosedFile.setEditable(false);

        buttonChooseFile.setText("Selecionar arquivo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 808, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldChoosedFile)
                .addGap(18, 18, 18)
                .addComponent(buttonChooseFile)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fieldChoosedFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonChooseFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 808, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonChooseFile;
    private javax.swing.JTextField fieldChoosedFile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
