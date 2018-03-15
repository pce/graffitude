package graffitude;

import java.awt.image.BufferedImage;

/**
 *
 * @author pce
 */
public class PixelPointedFilter implements PixelFilterable {

    private PixelArray pixelArray;

    private BufferedImage image;

    private int scalefactor = 4;

    public PixelPointedFilter(PixelArray pixelArray) {
        this.pixelArray = pixelArray;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public PixelArray filter(PixelArray inPixelArray) {
        int currentClr = -1;
        boolean hasInitColor = false;

        PixelArray pixelArray = new PixelArray(inPixelArray.getWidth(), inPixelArray.getHeight());

        int radius = 10;

        boolean onXAndY = true;

        for (int y = 0; y < inPixelArray.getHeight(); y++) {
            for (int x = 0; x < inPixelArray.getWidth(); x++) {

                int clr = image.getRGB(x, y);

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
