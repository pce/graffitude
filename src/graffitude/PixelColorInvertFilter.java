package graffitude;


/**
 *
 * @author pce
 */
public class PixelColorInvertFilter implements PixelFilterable {

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

    public PixelColorInvertFilter() {}

    public PixelColorInvertFilter(PixelArray pixelArray) {
        this.pixelArray = pixelArray;
    }

    @Override
    public PixelArray filter(PixelArray inPixelArray) {

        String style = "simple"; // or "artistic";

        System.out.println("@" + this.getClass().getSimpleName() + " style:" + style);

        // PixelArray
        pixelArray = new PixelArray(inPixelArray.getWidth(), inPixelArray.getHeight());

        for (int y = 0; y < inPixelArray.getHeight(); y += 1) {
            for (int x = 0; x < inPixelArray.getWidth(); x += 1) {

                int clr = inPixelArray.getPixel(x, y);

                if (style.equals("artistic")) {
                    pixelArray.setPixel(x, y, 0xFFFFFFFF - clr);
                } else {
                    // A(FF)RGB  - clr
                    pixelArray.setPixel(x, y, ~clr|0xff000000);
                }
            }
        }
        return pixelArray;
    }



}

