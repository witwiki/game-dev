package com.witwiki.framework;

import com.witwiki.framework.Graphics.ImageFormat;

/**
 * Created by witwiki on 28/01/2015.
 */
public interface Image {

    public int getWidth();

    public int getHeight();

    public ImageFormat getFormat();

    public void dispose();
}
