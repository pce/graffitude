package graffitude;

import java.awt.image.BufferedImage;

/**
 *
 * @author pce
 */
public class PixelStripeFilter implements PixelFilterable {

    private PixelArray pixelArray;

    private BufferedImage image;

    private int scalefactor = 8;

    public int getScalefactor() {
        return scalefactor;
    }

    public void setScalefactor(int scalefactor) {
        this.scalefactor = scalefactor;
    }

    public PixelStripeFilter(PixelArray pixelArray) {
        this.pixelArray = pixelArray;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public PixelArray filter(PixelArray inPixelArray) {

        int currentClr = -1;
        boolean hasInitColor = false;

        // String style = "arrow";
        String style = "stripe";

        System.out.println("@PixelStripeFilter " + style);

        PixelArray pixelArray = new PixelArray(inPixelArray.getWidth(), inPixelArray.getHeight());

        for (int y = 0; y < inPixelArray.getHeight(); y += 8) {
            for (int x = 0; x < inPixelArray.getWidth(); x += 8) {

                int clr = image.getRGB(x, y);

                if (!hasInitColor) {
                    currentClr = clr;
                    hasInitColor = true;
                }

                if (x % scalefactor == 0 || y % scalefactor == 0) {
                    currentClr = clr;
                }

                for (int i = 0; i < 8; i++) {
                    if (style.equals("arrow")) {

                        pixelArray.setPixel(x + 1, y, currentClr);
                        pixelArray.setPixel(x, y + i, currentClr);
                        pixelArray.setPixel(x + i, y + i + 1, currentClr);
                        pixelArray.setPixel(x + i + 1, y + i, currentClr);

                    } else {

                        pixelArray.setPixel(x + i, y + i, currentClr);
                        pixelArray.setPixel(x + i, y + i + 1, currentClr);
                        pixelArray.setPixel(x + i + 1, y + i, currentClr);

//                        pixelArray.setPixel(x+i, y+i, currentClr);
//                        pixelArray.setPixel(x+i+1, y+i+1, currentClr);
//                        pixelArray.setPixel(x+i+2, y+i+2, currentClr);
//                        pixelArray.setPixel(x+i+3, y+i+3, currentClr);
// ---
                        // pixelArray.setPixel(x+i+1, y+i, currentClr);
                        // pixelArray.setPixel(x+2, y+i, currentClr);
                        // pixelArray.setPixel(x+i, y+2, currentClr);
                        // pixelArray.setPixel(x+3, y+i, currentClr);
                        // pixelArray.setPixel(x+i, y+3, currentClr);
                    }
                }

            }
        }

        return pixelArray;
    }
}
