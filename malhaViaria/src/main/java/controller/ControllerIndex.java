package controller;

import view.ViewIndex;

/**
 * @author Leonardo & Ruan
 */
public class ControllerIndex {

    private ViewIndex view;

    public ControllerIndex() {
        this.view = new ViewIndex();
    }
    
    public void abreTela() {
        this.view.setVisible(true);
    }
    
}