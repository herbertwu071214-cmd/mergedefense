package com.herb.macondo.mergedefense.input;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class InputHandler {
    private double lastMouseX;
    private double lastMouseY;
    private boolean mousePressed;
    private MouseButton pressedButton;

    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        pressedButton = e.getButton();
        lastMouseX = e.getX();
        lastMouseY = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    public void mouseMoved(MouseEvent e) {
        lastMouseX = e.getX();
        lastMouseY = e.getY();
    }

    public double getLastMouseX() { return lastMouseX; }
    public double getLastMouseY() { return lastMouseY; }
    public boolean isMousePressed() { return mousePressed; }
    public MouseButton getPressedButton() { return pressedButton; }
}
