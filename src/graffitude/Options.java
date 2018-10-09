package graffitude;

import java.util.*;

/**
 *
 * @author pce
 */
public class Options {


    // 1=drawRect 1.1
    public int g2d_style = 1;

    public int width = 320;
    public int height = 240;

    // Filter Registry (enabled)
    public boolean genrand = false;
    public boolean scale = false;
    public boolean pointed = false;
    public boolean split = false;
    public boolean stripe = false;

    public LinkedList<String> filterPipeline;

    public int scalefactor = 8;
    public int stripe_scalefactor = 8;
    public int scale_scalefactor = 8;
    public String scale_scalemode = "pixel";

    public Options() {
        this.scale = false;
        this.pointed = false;
        this.split = false;
        this.stripe = false;
    }
}
