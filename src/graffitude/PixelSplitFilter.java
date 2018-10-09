package graffitude;


/**
 * The Splitfilter split images,
 * just a Toy ...
 *
 * "in.jpg"  --filter split 4  1
 *
 * TODO Options from Command-Line: splitsNum tileNum?
 * or give the Filter the ability to create additional Graphics
 * for Frames to create a gif or movie
 * or for additional Images on Disk (Storage)
 *
 * @author pce
 */
public class PixelSplitFilter implements PixelFilterable {

    private PixelArray pixelArray;

    private int scalefactor = 2;

    public PixelSplitFilter() {}

    public PixelSplitFilter(PixelArray pixelArray) {
        this.pixelArray = pixelArray;
    }

    @Override
    public void setOptions(Options options) {
        this.scalefactor = options.scalefactor;
    }

    @Override
    public PixelArray filter(PixelArray inPixelArray) {

        PixelArray pixelArray = new PixelArray(inPixelArray.getWidth(), inPixelArray.getHeight());

        // XXX 1,2,3,4 hardcoded
        int currentTile = 1;

        int currentClr = -1;
        int r = (scalefactor > 3) ? scalefactor / 2 : scalefactor;
        int w = inPixelArray.getWidth() / scalefactor;
        int h = inPixelArray.getHeight() / scalefactor;

        int offsetx = 0;
        int offsety = 0;

        if (currentTile == 1) {
            offsetx = 0;
            offsety = 0;
        }
        if (currentTile == 2) {
            offsetx = w; // w * currentTile;
            offsety = 0;
        }
        if (currentTile == 3) {
            offsetx = 0; // w * currentTile;
            offsety = h;
        }
        if (currentTile == 4) {
            offsetx = w; // w * currentTile;
            offsety = h;
        }


        for (int x = offsetx; x < w + offsetx; x++) {
            for (int y = offsety; y < h + offsety; y++) {

                int _x = (x - offsetx) * scalefactor;
                int _y = (y - offsety) * scalefactor;

                int clr = inPixelArray.getPixel(x, y);

                for (int i = 0; i < scalefactor; i++) {
                    pixelArray.drawSquare(_x, _y, r, clr);
                    for (int j = 1; j < scalefactor / 2; j++) {
                        pixelArray.drawSquare(_x + j, _y + j, r, clr);
                    }
                }

            }
        }

        return pixelArray;
    }
}
