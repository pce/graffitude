package graffitude;


/**
 * The Splitfilter split images,
 * just a Toy ...
 *
 * TODO all Options from Cli: splitsNum tileNum?, "in.jpg"  --filter split 4  1
 * or give the Filter the ability to create additional Graphics
 * for Frames to create a gif or movie
 * or for additional Images on Disk (Storage)
 * ( In(A) ->(split)-> Out(A1, A2, A3, A4)  -> nextFilter?
 *
 * @author pce
 */
public class PixelSplitFilter implements PixelFilterable {

    private PixelArray pixelArray;

    private int scalefactor = 2;

    private int currentTile = 1;

    public PixelSplitFilter() {}

    public PixelSplitFilter(PixelArray pixelArray) {
        this.pixelArray = pixelArray;
    }

    @Override
    public void setOptions(Options options) {
        // hardcoded (0 is forbidden, ...)
        this.scalefactor = 2;
        this.currentTile = options.paramInt1;
    }

    @Override
    public PixelArray filter(PixelArray inPixelArray) {

        System.out.println("@" + this.getClass().getSimpleName() + " tile:" + this.currentTile + " scale:" + this.scalefactor);


        // PixelArray pixelArray = new PixelArray(inPixelArray.getWidth(), inPixelArray.getHeight());
        pixelArray = new PixelArray(inPixelArray.getWidth(), inPixelArray.getHeight());

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
