package com.robenhood.controller;

import com.robenhood.gui.GUIMain;
import com.robenhood.model.Model;

public class Controller {
    private Model aModel;
    private GUIMain aGUI;
    public Controller(Model aModel, GUIMain aGUI) {
        this.aModel = aModel;
        this.aGUI = aGUI;
    }

    public void update() {
        // No need to update gui because it has its own buttons for that
        aModel.update();
    }
}
