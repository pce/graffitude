package graffitude;


/**
 *
 * @author pce
 */
public class PixelColorInvert implements PixelFilterable {

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

    public PixelColorInvert() {}

    public PixelColorInvert(PixelArray pixelArray) {
        this.pixelArray = pixelArray;
    }

    @Override
    public PixelArray filter(PixelArray inPixelArray) {

        int currentClr = -1;
        boolean hasInitColor = false;

        // String style = "artistic";
        String style = "simple";

        System.out.println("@" + this.getClass().getSimpleName() + " style:" + style);

        // PixelArray
        pixelArray = new PixelArray(inPixelArray.getWidth(), inPixelArray.getHeight());

        for (int y = 0; y < inPixelArray.getHeight(); y += 1) {
            for (int x = 0; x < inPixelArray.getWidth(); x += 1) {

                int clr = inPixelArray.getPixel(x, y);

                if (style.equals("artistic")) {
                    // no invertion, but nice when interpolated
                    pixelArray.setPixel(x, y, (clr / 2) + clr);
                    pixelArray.setPixel(x, y+1, (clr / 2) + clr);
                    pixelArray.setPixel(x + 1, y, (clr / 2) + clr);
                } else {
                    // FF - clr
                    pixelArray.setPixel(x, y, 0xFFFFFFFF - clr);
                }
            }
        }
        return pixelArray;
    }



}

