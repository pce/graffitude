package graffitude;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pce
 */
public class PixelPeakDetectFilter implements PixelFilterable {

    private PixelArray pixelArray;


    private ArrayList nodes;

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

    public PixelPeakDetectFilter() {}

    public PixelPeakDetectFilter(PixelArray pixelArray) {
        this.pixelArray = pixelArray;
    }

    private List<Integer> findThresholds(PixelArray inPixelArray) {
        List<Integer> colorlist = new ArrayList<>();


        int brightestColor = 0xFFFFFFFF;
        int darkestColor = 0xFF000000;

        int currentColor = -1;
        int matches = 0;

        for (int y = 0; y < inPixelArray.getHeight(); y += 1) {
            for (int x = 0; x < inPixelArray.getWidth(); x += 1) {

                int clr = inPixelArray.getPixel(x, y);

                if (currentColor == -1) {
                    brightestColor = clr;
                    darkestColor = clr;
                }

                matches = 0;

                // todo
                // at least two neighbours of this color to count
                if (inPixelArray.getPixel(x + 1, y) == clr) {
                      matches++;
                }

                if (inPixelArray.getPixel(x, y + 1) == clr) {
                      matches++;
                }

                if (inPixelArray.getPixel(x, y - 1) == clr) {
                      matches++;
                }

                if (inPixelArray.getPixel(x +1, y + 1) == clr) {
                      matches++;
                }

                if (inPixelArray.getPixel(x -1, y - 1) == clr) {
                      matches++;
                }

                if (inPixelArray.getPixel(x -1, y ) == clr) {
                      matches++;
                }

                if (matches < 4) {
                    continue;
                }

                if (clr > brightestColor) {
                    brightestColor = clr;
                }

                if (clr < darkestColor) {
                    darkestColor = clr;
                }
                // ?
                currentColor = clr;
            }
        }

        colorlist.add(darkestColor);
        colorlist.add(brightestColor);

        return colorlist;
    }



    @Override
    public PixelArray filter(PixelArray inPixelArray) {

        String style = "simple";
        System.out.println("@" + this.getClass().getSimpleName() + " style:" + style);

        pixelArray = new PixelArray(inPixelArray.getWidth(), inPixelArray.getHeight());

        int colorNoMatch = 0xFF00FF00;
        int colorBg = 0xFF000000;
        int colorEdge = 0xFFFFFFFF;

        List<Integer> colorlist = findThresholds(inPixelArray);

        for (int y = 0; y < inPixelArray.getHeight(); y += 1) {
            for (int x = 0; x < inPixelArray.getWidth(); x += 1) {

                int clr = inPixelArray.getPixel(x, y);

                if (clr <= colorlist.get(0)) {
                    pixelArray.setPixel(x, y, colorBg);
                } else if (clr >= colorlist.get(1)) {
                    pixelArray.setPixel(x, y, colorEdge);
                } else {
                   // pixelArray.setPixel(x, y, clr);
                   pixelArray.setPixel(x, y, colorNoMatch);
                }
            }
        }

        return pixelArray;
    }

}

