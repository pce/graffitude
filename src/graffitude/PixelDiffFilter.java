package graffitude;

/**
 * The simple Diff-Filter takes an input-image as argument
 * and creates a Pixelarray of Differences
 *
 * @todo test: same images should result in a single colored pixelarray
 *
 * @author pce
 */
public class PixelDiffFilter implements PixelFilterable {
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

    public PixelDiffFilter() {}

    public PixelDiffFilter(PixelArray pixelArray) {
        this.pixelArray = pixelArray;
    }


    @Override
    public PixelArray filter(PixelArray inPixelArray) {

        String style = "simple";

        System.out.println("@" + this.getClass().getSimpleName() + " style:" + style);

        // PixelArray
        PixelArray pixelArrayOut = new PixelArray(inPixelArray.getWidth(), inPixelArray.getHeight());
        int clr;
        int clrDiff;

        for (int y = 0; y < inPixelArray.getHeight(); y += 1) {
            for (int x = 0; x < inPixelArray.getWidth(); x += 1) {
                clr = inPixelArray.getPixel(x, y);
                clrDiff = pixelArray.getPixel(x, y);
                if (clr != clrDiff) {
                    pixelArrayOut.setPixel(x, y, clrDiff);
                } else {
                    pixelArrayOut.setPixel(x, y, 0xff000000);
                }

            }
        }
        return pixelArrayOut;
    }

}
