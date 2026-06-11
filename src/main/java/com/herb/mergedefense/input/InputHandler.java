package com.herb.mergedefense.input;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class InputHandler {
    private double lastMouseX;
    private double lastMouseY;
    private boolean mousePressed;
    private MouseButton pressedButton;
    private boolean dragging;
    private int dragStartRow = -1;
    private int dragStartCol = -1;

    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        pressedButton = e.getButton();
        lastMouseX = e.getX();
        lastMouseY = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
        dragging = false;
    }

    public void mouseMoved(MouseEvent e) {
        lastMouseX = e.getX();
        lastMouseY = e.getY();
    }

    public double getLastMouseX() { return lastMouseX; }
    public double getLastMouseY() { return lastMouseY; }
    public boolean isMousePressed() { return mousePressed; }
    public MouseButton getPressedButton() { return pressedButton; }

    public void setDragging(boolean dragging) { this.dragging = dragging; }
    public boolean isDragging() { return dragging; }
    public void setDragStart(int row, int col) { dragStartRow = row; dragStartCol = col; }
    public int getDragStartRow() { return dragStartRow; }
    public int getDragStartCol() { return dragStartCol; }
    public void clearDragStart() { dragStartRow = -1; dragStartCol = -1; dragging = false; }
}
