package graffitude;


/**
 *
 * @author pce
 */
public class PixelFloodFilter implements PixelFilterable {

    private PixelArray pixelArray;


    // detectColorPallette() {}

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

    @Override
    public PixelArray filter(PixelArray inPixelArray) {

        int currentClr = -1;
        boolean hasInitColor = false;
        // TODO 3-thresholds, treshold-by-palette, interpolate or less noise ...
        String style = "simple-contrast";

        System.out.println("@" + this.getClass().getSimpleName() + " style:" + style);

        // PixelArray
        pixelArray = new PixelArray(inPixelArray.getWidth(), inPixelArray.getHeight());

        int threshold = 0xff555555;

        for (int y = 0; y < inPixelArray.getHeight(); y += 1) {
            for (int x = 0; x < inPixelArray.getWidth(); x += 1) {

                int clr = inPixelArray.getPixel(x, y);

                if (clr > threshold) {
                    pixelArray.setPixel(x + 1, y, 0x00000000);
                } else {
                    pixelArray.setPixel(x + 1, y, 0xffffffff);
                }
            }
        }
        return pixelArray;
    }

}
