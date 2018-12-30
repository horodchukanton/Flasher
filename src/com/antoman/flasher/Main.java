package com.antoman.flasher;

import com.antoman.flasher.drives.*;
import com.antoman.flasher.ui.*;

import org.slf4j.*;

import javax.usb.UsbException;
import java.util.Date;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Started " + (new Date()).toString());

        try {
            DrivesList flashes = new DrivesList();


        } catch (IllegalStateException e) {
            (new ErrorFlash("Failed to initialize USB Services. See logs for details.")).showFlash();

            logger.error("At initialize", e);
        }

        logger.info("Exit " + (new Date()).toString());

    }


}
