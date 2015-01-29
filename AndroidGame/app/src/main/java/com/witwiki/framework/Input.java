package com.witwiki.framework;

import java.util.List;

/**
 * Created by witwiki on 28/01/2015.
 */
public interface Input {

    /*
        The Input interface keeps track of touch events,
        with variables x, y, type (touch down, touch up, etc),
        and pointer (each point of contact on screen - Android
        supports multi-touch)
     */
    public static class TouchEvent{
        public static final int TOUCH_DOWN = 0;
        public static final int TOUCH_UP = 1;
        public static final int TOUCH_DRAGGED = 2;
        public static final int TOUCH_HOLD = 3;

        public int type;
        public int x, y;
        public int pointer;

    }

    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<TouchEvent> getTouchEvents();
}
