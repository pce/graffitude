package graffitude;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author pce
 */
public class PixelFloodFilter implements PixelFilterable {

    private PixelArray pixelArray;


    private int scalefactor = 8;

    @Override
    public void setOptions(Options options) {
        this.scalefactor = options.stripe_scalefactor;
    }

    public int getScalefactor() {
        return scalefactor;
    }

    public void setScalefactor(int scalefactor) {
        this.scalefactor = scalefactor;
    }

    public PixelFloodFilter() {}

    public PixelFloodFilter(PixelArray pixelArray) {
        this.pixelArray = pixelArray;
    }

    private List<Integer> detectColorPallette() {
        List<Integer> colorlist = new ArrayList<>();
        // detect n (here 2-8) significant colors and add them
        // ...
        // {0xFFFFEEAA, 0xFFFF3355, 0xFF3355FF};
        return colorlist;
    }

    @Override
    public PixelArray filter(PixelArray inPixelArray) {

        int currentClr = -1;
        boolean hasInitColor = false;
        // TODO treshold-by-palette, interpolate or less noise ...
        String style = "simple-contrast";
        style = "3-thresholds";

        System.out.println("@" + this.getClass().getSimpleName() + " style:" + style);

        // PixelArray
        pixelArray = new PixelArray(inPixelArray.getWidth(), inPixelArray.getHeight());

        int threshold = 0xff555555;

        int thresholdMed = 0xff333333;
        int thresholdHi = 0xff888888;

        for (int y = 0; y < inPixelArray.getHeight(); y += 1) {
            for (int x = 0; x < inPixelArray.getWidth(); x += 1) {

                int clr = inPixelArray.getPixel(x, y);

                if (style.equals("3-thresholds")) {
                    if (clr < thresholdMed) {
                        pixelArray.setPixel(x, y, thresholdMed);
                    } else if (clr < thresholdHi) {
                        pixelArray.setPixel(x, y, thresholdHi);
                    } else {
                        pixelArray.setPixel(x, y, threshold);
                    }
                } else {
                    if (clr > threshold) {
                        pixelArray.setPixel(x, y, 0x00000000);
                    } else {
                        pixelArray.setPixel(x, y, 0xffffffff);
                    }
                }

            }
        }
        return pixelArray;
    }

}
