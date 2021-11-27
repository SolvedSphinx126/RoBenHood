/**
 * @author Jeremiah Rhoton
 */

package com.robenhood.controller;

import com.robenhood.gui.GUIMain;
import com.robenhood.model.Model;

/**
 * A class to control a model and a viewer
 */
public class Controller {
    private Model aModel;
    private GUIMain aGUI;

    /**
     * Constructs a new controller given a model and a viewer
     * @param aModel The model to be controlled
     * @param aGUI The viewer to display the data
     */
    public Controller(Model aModel, GUIMain aGUI) {
        this.aModel = aModel;
        this.aGUI = aGUI;
    }

    /**
     * Updates the model and the viewer
     */
    public void update() {
        // No need to update gui because it has its own buttons for that
        aModel.update();
    }
}
