package com.antoman.flasher.ui;

import javax.swing.*;

public class ErrorFlash extends JFrame {

    private String message;

    public ErrorFlash(String message) {
        this.message = message;
    }

    public void showFlash() {
        JOptionPane.showMessageDialog(null,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
