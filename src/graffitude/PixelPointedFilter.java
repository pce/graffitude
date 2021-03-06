package graffitude;


/**
 *
 * @author pce
 */
public class PixelPointedFilter implements PixelFilterable {

    private PixelArray pixelArray;

    private int scalefactor = 4;

    public PixelPointedFilter() {}

    public PixelPointedFilter(PixelArray pixelArray) {
        this.pixelArray = pixelArray;
    }

    @Override
    public void setOptions(Options options) {
        this.scalefactor = options.scalefactor;
    }

    @Override
    public PixelArray filter(PixelArray inPixelArray) {

        System.out.println("@" + this.getClass().getSimpleName());

        int currentClr = -1;
        boolean hasInitColor = false;

        // PixelArray
        pixelArray = new PixelArray(inPixelArray.getWidth(), inPixelArray.getHeight());

        int radius = 10;

        boolean onXAndY = true;

        for (int y = 0; y < inPixelArray.getHeight(); y++) {
            for (int x = 0; x < inPixelArray.getWidth(); x++) {

                int clr = inPixelArray.getPixel(x, y);

                if (!hasInitColor) {
                    currentClr = clr;
                    hasInitColor = true;
                }

                if (onXAndY) {
                    if (x % scalefactor == 0 && y % scalefactor == 0) {
                        currentClr = clr;
                        // pixelArray.setPixel(x, y, currentClr);
                        pixelArray.drawCircle(x, y, radius, currentClr);
                    }
                } else {
                    if (x % scalefactor == 0 || y % scalefactor == 0) {
                        currentClr = clr;
                        // pixelArray.setPixel(x, y, currentClr);
                        pixelArray.drawCircle(x, y, radius, currentClr);

                    }
                }
            }
        }

        return pixelArray;
    }
}
