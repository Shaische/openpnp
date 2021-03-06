/*
 * Copyright (C) 2011 Jason von Nieda <jason@vonnieda.org>
 * 
 * This file is part of OpenPnP.
 * 
 * OpenPnP is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * OpenPnP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with OpenPnP. If not, see
 * <http://www.gnu.org/licenses/>.
 * 
 * For more information about OpenPnP visit http://openpnp.org
 */

package org.openpnp.spi;

import java.awt.image.BufferedImage;
import java.io.Closeable;

import org.openpnp.CameraListener;
import org.openpnp.model.Location;

/**
 * Represents a Camera attached to the system and allows a caller to retrieve images from it.
 */
public interface Camera extends HeadMountable, WizardConfigurable,
        PropertySheetHolder, Closeable {
    public enum Looking {
        Down, Up
    }

    /**
     * Get the location of the camera inlcuding the calibrated offset for the given tool.   
     * If the bottom camera focal plane is different from the PCB surface plane, the various
     * tools might introduce slight offsets in X, Y as their Z axes are not perfectly parallel.
     * This offset is compensated if the getLocation(tool) method is used instead of the plain
     * getLocation() method. 
     * If tool == null it returns the same as plain getLocation().
     *  
     * @param tool
     * @return
     */
    public Location getLocation(HeadMountable tool);

    /**
     * Get the direction the Camera is looking.
     * 
     * @return
     */
    public Looking getLooking();

    public void setLooking(Looking looking);

    /**
     * The number of X and Y units per pixel this camera shows when in perfect focus. Location isn't
     * a great datatype for this, but it gets the job done.
     * 
     * @return
     */
    public Location getUnitsPerPixel();

    public void setUnitsPerPixel(Location unitsPerPixel);
    
    /**
     * Immediately captures an image from the camera and returns it in it's native format. Fires
     * the Camera.BeforeCapture and Camera.AfterCapture scripting events before and after.
     * @return
     */
    public BufferedImage capture();
    
    public BufferedImage captureForPreview();
    
    public BufferedImage captureRaw();
    
    /**
     * Same as capture(), but waits the settle time before capturing.
     * 
     * @return
     */
    public BufferedImage settleAndCapture();

    /**
     * Registers a listener to receive continuous images from the camera.
     * 
     * @param listener
     */
    public void startContinuousCapture(CameraListener listener);

    /**
     * Requests that the continuous capture be stopped for the previously registered listener. If
     * the Camera has other listeners they should still receive updates.
     * 
     * @param listener
     */
    public void stopContinuousCapture(CameraListener listener);

    public void setVisionProvider(VisionProvider visionProvider);

    /**
     * Get the VisionProvider that is attached to this Camera, if any.
     * 
     * @return
     */
    public VisionProvider getVisionProvider();

    /**
     * Get the width of images in pixels that will be returned from this Camera.
     * 
     * @return
     */
    public int getWidth();

    /**
     * Get the height of images in pixels that will be returned from this Camera.
     * 
     * @return
     */
    public int getHeight();
}
