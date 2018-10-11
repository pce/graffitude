package graffitude;

/**
 *
 * @author pce
 */
interface PixelFilterable {
    public PixelArray filter(PixelArray pixelArray);
    public void setOptions(Options options);
}
