package com.witwiki.framework;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.SharedPreferences;

/**
 * Created by witwiki on 28/01/2015.
 */
public interface FileIO {

    //  We handle IOException so that we do not need a try and catch statement.
    public InputStream readFile(String file) throws IOException;

    public OutputStream writeFile(String file) throws IOException;

    public InputStream readAsset(String file) throws IOException;

    //  SharedPreferences is an Android interface that lets you access and modify preference data
    public SharedPreferences getSharedPref();
}
