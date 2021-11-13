package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;
import model.Segmento;
import view.TableModelMalhaViaria;
import view.ViewIndex;

/**
 * @author Leonardo & Ruan
 */
public class ControllerIndex {

    private ViewIndex view;

    public ControllerIndex() {
        this.view = new ViewIndex();
        this.addListeners();
    }
    
    public void abreTela() {
        this.getView().setVisible(true);
    }

    public ViewIndex getView() {
        return view;
    }

    private void addListeners() {
        this.getView().getButtonChooseFile().addActionListener((actionListener) -> {
            try {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("Texto", "txt"));
                
                int choice = chooser.showOpenDialog(this.getView());
                
                if (choice == JFileChooser.APPROVE_OPTION) {
                    String absolutePath = chooser.getSelectedFile().getAbsolutePath();
                    this.getView().getFieldChoosedFile().setText(absolutePath);
                    File file = new File(absolutePath);
                    
                    try (Scanner scanner = new Scanner(file)) {
                        if (scanner.hasNextLine()) {
                            int rowCount = Integer.valueOf(scanner.nextLine());
                            int columnCount = Integer.valueOf(scanner.nextLine());
                            
                            Segmento[][] segmentos = new Segmento[rowCount][columnCount];
                            
                            int i = 0;
                            while (scanner.hasNextLine()) {
                                String[] line = scanner.nextLine().split("\t");
                                for (int j = 0; j < columnCount; j++) {
                                    segmentos[i][j] = new Segmento(Integer.valueOf(line[j]));
                                }
                                i++;
                            }
                            
                            JTable table = this.getView().getTable();
                            TableModelMalhaViaria tableModel = this.getView().getTableModel();
                            
                            for (i = 0; i < tableModel.getColumnCount(); i++) {
                                table.removeColumn(table.getColumn(String.valueOf(i)));
                            }
                            
                            tableModel.setSegmentos(segmentos);
                            
                            for (i = 0; i < tableModel.getColumnCount(); i++) {
                                TableColumn tableColumn = new TableColumn(i);
                                tableColumn.setMinWidth(30);
                                tableColumn.setMaxWidth(30);
                                table.addColumn(tableColumn);
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(this.getView(), "O arquivo está vazio", "Informação", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
            catch (FileNotFoundException fileNotFoundException) {
                JOptionPane.showMessageDialog(this.getView(), "Não foi possivel ler o arquivo", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
    
}