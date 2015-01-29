package com.witwiki.framework;

/**
 * Created by witwiki on 28/01/2015.
 */
public interface Music {

    /*
        Play, stop and pause methods, as well as ways to change volume,
        set looping (repeating an audio file), along with methods of
        checking the current state of the file. The dispose method removes
        the music file (allows it to be removed from Memory).
     */
    public void play();

    public void stop();

    public void pause();

    public void setLooping(boolean looping);

    public void setVolume(float volume);

    public boolean isPlaying();

    public boolean isStopped();

    public boolean isLooping();

    public void dispose();

    void seekBegin();
}
