package com.witwiki.framework;

/**
 * Created by witwiki on 28/01/2015.
 */
public interface Audio {

    /*
        It uses the Music and Sound interfaces to create audio
        objects for use.
    */
    public Music createMusic(String file);

    public Sound createSound(String file);
}
