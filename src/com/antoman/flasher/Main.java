package com.antoman.flasher;

import com.antoman.flasher.drives.DrivesList;
import com.antoman.flasher.ui.ErrorFlash;
import com.antoman.flasher.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Started " + (new Date()).toString());

        try {
            startUI();
        } catch (Exception e) {
            (new ErrorFlash("Program has encountered an unexpected error. Please restart.")).showFlash();
            logger.error("At initialize", e);
        }

    }

    private static void startUI() {
        DrivesList flashes = new DrivesList();

        Runnable uiRunner = () -> {
            UI ui = new UI(flashes);
            ui.setVisible(true);
        };

        uiRunner.run();
    }


}
