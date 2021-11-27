/**
 * @author Jeremiah Rhoton
 */

package com.robenhood.controller;

import com.robenhood.gui.GUIMain;
import com.robenhood.model.Model;

/**
 * The main entry to the program
 */
public class Main {
    static GUIMain gui;
    static Model model;
    static Controller controller;

    /**
     * The main function of the program
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        model = new Model();
        gui = new GUIMain(model, "RoBenHood");
        controller = new Controller(model, gui);
    }
}
