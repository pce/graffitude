package graffitude;

/**
 *
 * @author pce
 */
public class Options {



    // 1=drawRect 1.1
    public int g2d_style = 1;

    // Filter Registry (enabled)
    public boolean scale = false;
    public boolean pointed = false;
    public boolean split = false;
    public boolean stripe = false;

    public int scalefactor = 8;
    public int stripe_scalefactor = 8;
    public int scale_scalefactor = 8;

    public Options() {
        this.scale = false;
        this.pointed = false;
        this.split = false;
        this.stripe = false;
    }
}
