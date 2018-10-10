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

    // Generative
    public boolean genrand = false;

    // generic params
    public int paramInt1 = 0;
    public int paramInt2 = 0;

    // ?
    public LinkedList<String> filterPipeline;

    public int scalefactor = 8;
    public int stripe_scalefactor = 8;
    public int scale_scalefactor = 8;
    public String scale_scalemode = "pixel";

    public Options() {}
}
