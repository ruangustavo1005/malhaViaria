package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Segmento;
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
                            
                            this.getView().getTableModel().setSegmentos(segmentos);
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