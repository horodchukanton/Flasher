package com.antoman.flasher.drives;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.usb.*;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DrivesList implements Iterable {

    private static Logger logger = LoggerFactory.getLogger(DrivesList.class);


    private List<UsbDevice> drives;
    public static final boolean REMOVABLE = true;


    public DrivesList() throws IllegalStateException {

        UsbHub root = null;
        try {
            root = getRootHub();
        } catch (UsbException e) {
            logger.error("Failed to get drives list", e);
            throw new IllegalStateException(e);
        }

        List<UsbDevice> flatList = getAllChildDevices(root);
        List<UsbDevice> flashDrives = new ArrayList<UsbDevice>();

        // Now deffer the drives amongst other devices
        for (UsbDevice dev : flatList){
            if (dev.isConfigured() && isOpenable(dev)){
                try {
                    System.out.println(dev.getProductString());
                    flashDrives.add(dev);
                } catch (UnsupportedEncodingException e) {
                    logger.error("UnsupportedEncodingException", e);
                } catch (UsbException e) {
                    e.printStackTrace();
                }
            }
        }

        if (flashDrives.size() < 1){
            throw  new IllegalStateException("No drives found!");
        }

        this.drives = flashDrives;
    }

    private boolean isOpenable(UsbDevice dev) {
        try {
            dev.getSerialNumberString();
        } catch (UsbException e) {
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private List<UsbDevice> getAllChildDevices(UsbHub root) {
        List<UsbDevice> devices = root.getAttachedUsbDevices();
        List<UsbDevice> result = new ArrayList<UsbDevice>();

        for (UsbDevice dev : devices) {
            if (dev.isUsbHub()) {
                logger.info("Found a Hub on: " + dev.toString());
                result.addAll(getAllChildDevices((UsbHub) dev));
            } else {
                logger.info("Usual device on: " + dev.toString());
                result.add(dev);
            }
        }

        return result;
    }

    public Iterator iterator() {
        return drives.iterator();
    }

    private UsbHub getRootHub() throws UsbException {
        UsbServices services = UsbHostManager.getUsbServices();
        return services.getRootUsbHub();
    }

}
