package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;
import model.Malha;
import model.Segmento;
import view.ViewIndex;

/**
 * @author Leonardo & Ruan
 */
public class ControllerIndex {

    private static ControllerIndex instance;
    
    private ViewIndex view;

    private ControllerIndex() {
        this.view = new ViewIndex();
        this.addListeners();
    }

    public synchronized static ControllerIndex getInstance() {
        if (ControllerIndex.instance == null) {
            ControllerIndex.instance = new ControllerIndex();
        }
        return instance;
    }
    
    public void abreTela() {
        this.getView().getButtonStart().setEnabled(false);
        this.getView().getButtonStop().setEnabled(false);
        this.getView().getButtonInterrupt().setEnabled(false);
        this.getView().setVisible(true);
    }

    public ViewIndex getView() {
        return view;
    }

    private void addListeners() {
        this.addListenerButtonChooseFile();
        this.addListenerButtonStart();
        this.addListenerButtonStop();
        this.addListenerButtonInterrupt();
    }
    
    private void addListenerButtonChooseFile() {
        this.getView().getButtonChooseFile().addActionListener((actionListener) -> {
            try {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("Texto", "txt"));
                
                int choice = chooser.showOpenDialog(this.getView());
                
                if (choice == JFileChooser.APPROVE_OPTION) {
                    this.processaArquivoEscolhido(chooser);
                }
            }
            catch (FileNotFoundException | NumberFormatException exception) {
                this.getView().getButtonStart().setEnabled(false);
                this.getView().getButtonStop().setEnabled(false);
                this.getView().getButtonInterrupt().setEnabled(false);
                this.getView().getFieldChoosedFile().setText("");
                JOptionPane.showMessageDialog(this.getView(), "N??o foi possivel ler o arquivo", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
    
    private void processaArquivoEscolhido(JFileChooser chooser) throws FileNotFoundException {
        String absolutePath = chooser.getSelectedFile().getAbsolutePath();
        File file = new File(absolutePath);

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                int rowCount = Integer.valueOf(scanner.nextLine());
                int columnCount = Integer.valueOf(scanner.nextLine());

                ControllerMalhaViaria.getInstance().setRowCount(rowCount)
                                                   .setColumnCount(columnCount);
                
                Segmento[][] malha = new Segmento[rowCount][columnCount];

                int i = 0;
                while (scanner.hasNextLine()) {
                    String[] line = scanner.nextLine().split("\t");
                    for (int j = 0; j < columnCount; j++) {
                        malha[i][j] = (new Segmento()).setTipo(Integer.valueOf(line[j]))
                                                      .setPosX(j)
                                                      .setPosY(i);
                    }
                    i++;
                }

                this.loadSegmentosTabela(malha);
                
                this.getView().getButtonStart().setEnabled(true);
                this.getView().getButtonStop().setEnabled(true);
                this.getView().getButtonInterrupt().setEnabled(true);
                this.getView().getFieldChoosedFile().setText(absolutePath);
            }
            else {
                this.getView().getButtonStart().setEnabled(false);
                this.getView().getButtonStop().setEnabled(false);
                this.getView().getButtonInterrupt().setEnabled(false);
                this.getView().getFieldChoosedFile().setText("");
                JOptionPane.showMessageDialog(this.getView(), "O arquivo est?? vazio", "Informa????o", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void loadSegmentosTabela(Segmento[][] malha) {
        JTable table = this.getView().getTable();

        for (int i = 0; i < this.getView().getTableModel().getColumnCount(); i++) {
            table.removeColumn(table.getColumn(String.valueOf(i)));
        }

        this.getView().getTableModel().setMalha(malha);

        for (int i = 0; i < this.getView().getTableModel().getColumnCount(); i++) {
            TableColumn tableColumn = new TableColumn(i);
            tableColumn.setMinWidth(30);
            tableColumn.setMaxWidth(30);
            table.addColumn(tableColumn);
        }
    }
    
    public ControllerIndex atualizaSegmento(Segmento segmento) {
        this.getView().getTableModel().setValueAt(segmento);
        return this;
    }
    
    public ControllerIndex atualizaProgressBar(int incremento) {
        this.getView().getProgressBarActiveCars().setValue(this.getView().getProgressBarActiveCars().getValue() + incremento);
        return this;
    }
    
    public void addListenerButtonStart() {
        this.getView().getButtonStart().addActionListener((actionListener) -> {
            String tempoEspera = this.getView().getFieldTempoEspera().getText();
            
            if (tempoEspera.isEmpty()) {
                JOptionPane.showMessageDialog(this.getView(), "Informe um Tempo de Espera", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                String totalCarros = this.getView().getFieldTotalCarros().getText();
                
                if (totalCarros.isEmpty()) {
                    JOptionPane.showMessageDialog(this.getView(), "Informe um Total de Carros", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    this.getView().getButtonChooseFile().setEnabled(false);
                    this.getView().getFieldTempoEspera().setEnabled(false);
                    this.getView().getFieldTotalCarros().setEnabled(false);
                    
                    this.getView().getProgressBarActiveCars().setMaximum(Integer.parseInt(totalCarros));
                    
                    ControllerMalhaViaria.getInstance().setCooldownEntradaCarros(Integer.parseInt(tempoEspera))
                                                       .setMaxCarrosSimultaneos(Integer.parseInt(totalCarros))
                                                       .startMalhaViaria();
                }
            }
            
        });
    }
    
    public void addListenerButtonStop() {
        this.getView().getButtonStop().addActionListener((actionListener) -> {
            ControllerMalhaViaria.getInstance().stopMalhaViaria();
            this.getView().getFieldTempoEspera().setEnabled(true);
            this.getView().getFieldTotalCarros().setEnabled(true);
        });
    }
    
    public void addListenerButtonInterrupt() {
        this.getView().getButtonInterrupt().addActionListener((actionListener) -> {
            this.getView().getTableModel().resetSegmentos();
            this.getView().getProgressBarActiveCars().setValue(0);
            this.getView().getButtonChooseFile().setEnabled(true);
            this.getView().getFieldTempoEspera().setEnabled(true);
            this.getView().getFieldTotalCarros().setEnabled(true);
            ControllerMalhaViaria.getInstance().interruptMalhaViaria();
        });
    }

    public Malha getMalha() {
        return this.getView().getTableModel();
    }
    
}