package graffitude;


/**
 * Malen nach Lehrbuch (⌐■_■)
 *
 * @see Algorithm "Algorithmen und Datenstrukturen - Gunter Saake und Kai-Uwe Sattler"
 */
public class PythagorasTreePainter implements PixelPaintable {

    private PixelArray pixelArray;
    private Options options;
    private double tanphi = 1.0;
    private int fgColor = 255180155;

    public PythagorasTreePainter() {}

    public PythagorasTreePainter(PixelArray pixelArray) {
        this.pixelArray = pixelArray;
    }

    @Override
    public void setOptions(Options options) {
        this.options = options;
    }

    @Override
    public PixelArray paint(PixelArray inPixelArray) {
        System.out.println("@" + this.getClass().getSimpleName());
        return paintTree(inPixelArray, options.width - options.width / 4, options.height, options.width, options.height);
    }

    /**
     * recursive paint, use member pixelArray
     */
    public PixelArray paintTree(PixelArray pixelArray, double x1, double y1, double x2, double y2) {
        // System.out.println("paintTree");
        // 1: corners
        double dx = x2 - x1;
        double dy = y1 - y2;
        double x3 = x1 - dy;
        double y3 = y1 - dx;
        double x4 = x2 - dy;
        double y4 = y2 - dx;

        // 2: paint quad
        pixelArray.drawLine((int)x1, (int)y1, (int)x2, (int)y2, fgColor);
        pixelArray.drawLine((int)x2, (int)y2, (int)x4, (int)y4, fgColor);
        pixelArray.drawLine((int)x4, (int)y4, (int)x3, (int)y3, fgColor);
        pixelArray.drawLine((int)x1, (int)y1, (int)x3, (int)y3, fgColor);

        // 3: new coords
        double v = (x3 + x4) / 2 - (dy / 2 * tanphi);
        double w = (y3 + y4) / 2 - (dx / 2 * tanphi);

        if (dx * dx + dy * dy > 2) {
            // 4: small parts
            pixelArray = paintTree(pixelArray, x3, y3, v, w);
            pixelArray = paintTree(pixelArray, v, w, x4, y4);
        }
        return pixelArray;
    }

}
