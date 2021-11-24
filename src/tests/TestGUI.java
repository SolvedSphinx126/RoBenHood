package tests;

import com.robenhood.gui.GUIMain;
import com.robenhood.model.Model;

public class TestGUI {
    static GUIMain gui;
    static Model model;
    public static void main(String[] args) {
        model = new Model();
        gui = new GUIMain(model, "RoBenHood");
    }
}
