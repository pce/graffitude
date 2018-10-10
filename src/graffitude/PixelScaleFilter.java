package graffitude;


/**
 *
 * @author pce
 */
public class PixelScaleFilter implements PixelFilterable {

    private PixelArray pixelArray;

    private int scalefactor = 8;

    private String mode = "pixel";

    public PixelScaleFilter() {
    }

    public PixelScaleFilter(PixelArray pixelArray) {
        this.pixelArray = pixelArray;
    }

    @Override
    public void setOptions(Options options) {
        this.scalefactor = options.scale_scalefactor;
        this.mode = options.scale_scalemode;
    }

    public void setScalefactor(int scalefactor) {
        this.scalefactor = scalefactor;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public PixelArray filter(PixelArray inPixelArray) {

        System.out.println("@" + this.getClass().getSimpleName() + " mode:" + mode);

        switch (mode) {
            case "pixel":
                return simplePixelScale(inPixelArray);
            case "image":
                return simpleImageScale(inPixelArray);
            case "weird":
            default:
                return simpleImageScaleWeirdXY(inPixelArray);

        }

    }

    /**
     * scales the pixels and the image size
     *
     * @param inPixelArray
     * @return
     */
    private PixelArray simpleImageScale(PixelArray inPixelArray) {
        int currentClr;

        int outImgWidth = inPixelArray.getWidth() * scalefactor;
        int outImgHeight = inPixelArray.getHeight() * scalefactor;

        // PixelArray
        pixelArray = new PixelArray(outImgWidth, outImgHeight);

        int clr;
        for (int y = 0; y < inPixelArray.getHeight(); y++) {
            for (int x = 0; x < inPixelArray.getWidth(); x++) {

                clr = inPixelArray.getPixel(x, y);

                currentClr = clr;

                for (int i = 0; i <= scalefactor; i++) {
                    pixelArray.setPixel((x + i) * scalefactor, y * scalefactor, currentClr);
                    pixelArray.setPixel(x * scalefactor, (y + i) * scalefactor, currentClr);
                }

            }
        }

        return pixelArray;

    }

    /**
     * scales the pixels by skipping pixels, while keeping image size
     *
     * @param inPixelArray
     * @return
     */
    private PixelArray simplePixelScale(PixelArray inPixelArray) {
        int currentClr = -1;
        boolean hasInitColor = false;

        // PixelArray
        pixelArray = new PixelArray(inPixelArray.getWidth(), inPixelArray.getHeight());

        int clr;
        for (int y = 0; y < inPixelArray.getHeight(); y++) {
            for (int x = 0; x < inPixelArray.getWidth(); x++) {

                clr = inPixelArray.getPixel(x, y);

                if (!hasInitColor) {
                    currentClr = clr;
                    hasInitColor = true;
                }

                if (x % scalefactor == 0 || y % scalefactor == 0) {
                    currentClr = clr;
                }
                pixelArray.setPixel(x, y, currentClr);
            }
        }

        return pixelArray;

    }

    /**
     * scales the pixels and the image size x-y fun
     *
     * @param inPixelArray
     * @return
     */
    private PixelArray simpleImageScaleWeirdXY(PixelArray inPixelArray) {
        int currentClr;

        int outImgWidth = inPixelArray.getWidth() * scalefactor;
        int outImgHeight = inPixelArray.getHeight() * scalefactor;

        // PixelArray
        pixelArray = new PixelArray(outImgWidth, outImgHeight);

        int clr;
        for (int y = 0; y < inPixelArray.getHeight(); y++) {
            for (int x = 0; x < inPixelArray.getWidth(); x++) {

                clr = inPixelArray.getPixel(x, y);

                currentClr = clr;

                for (int i = 0; i <= scalefactor; i++) {
                    pixelArray.setPixel(x + i * scalefactor, y * scalefactor, currentClr);
                    pixelArray.setPixel(x * scalefactor, y + i * scalefactor, currentClr);
                }

            }
        }

        return pixelArray;

    }

}
