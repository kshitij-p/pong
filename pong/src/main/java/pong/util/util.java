package pong.util;

import pong.GlobalConstants;

public class util {
    public static double getScreenBottom() {
        return GlobalConstants.WINDOW_HEIGHT - GlobalConstants.INSETS.bottom;
    }

    public static double getScreenTop() {
        // Linux title bar is bugged so +10 needs to be added
        return GlobalConstants.INSETS.top + 10;
    }
}
