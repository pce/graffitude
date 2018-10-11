package graffitude;

/**
 *
 * @author pce
 */
public class PixelArray {

    private int pixels[];

    private int width, height;

    private int bgColor = 0x44000000;

    public PixelArray(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];
        this.clear();
    }

    public PixelArray(int[] pixels, int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    public int[] getPixels() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public int getPixel(int x, int y) {
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (x >= width) {
            x = width - 1;
        }
        if (y >= height) {
            y = height - 1;
        }

        return pixels[y * width + x];
    }

    public void setPixel(int x, int y, int color) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return;
        }
        this.pixels[y * width + x] = color;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setBackground(int color) {
        for (int i = 0; i < height * width; i++) {
            if (pixels[i] == bgColor) {
                pixels[i] = color;
            }
        }
    }

    public void clear() {
        for (int i = 0; i < height * width; i++) {
            pixels[i] = bgColor;
        }
    }

    /**
     * the filter operates on a new PixelArray
     *
     * @param filter
     */
    public void applyFilter(PixelFilterable filter) {
        PixelArray pixelArray = filter.filter(new PixelArray(this.pixels, width, height));
        this.height = pixelArray.getHeight();
        this.width = pixelArray.getWidth();
        this.pixels = pixelArray.getPixels();
    }


    public void setPixel(int offset, int color) {
        pixels[offset] = color;
    }


    public void drawCircle(int x, int y, int r, int color) {
        for (int  i = 0; i < 360; i += 5) {
            setPixel((int) Math.round(x + Math.sin(i) * r),
                    (int) Math.round(y + Math.cos(i) * r), color);
        }
    }


    public void drawSquare(int x, int y, int r, int color) {
        int i;
        for (i = x - (r / 2); i < x + (r / 2); i++) {
            setPixel(i, y - (r / 2), color);
            setPixel(i, y + (r / 2), color);
        }
        for (i = y - (r / 2); i < y + (r / 2); i++) {
            setPixel(x - (r / 2), i, color);
            setPixel(x + (r / 2), i, color);
        }
    }


    public void drawRect(int x, int y, int w, int h, int color) {
        int i;
        for (i = x; i < x + w; i++) {
            setPixel(i, y, color);
            setPixel(i, y + h, color);
        }
        for (i = y; i < y + h; i++) {
            setPixel(x, i, color);
            setPixel(x + w, i, color);
        }
    }

    public void drawRectFill(int x, int y, int w, int h, int color) {
        int i;
        for (i = x; i < x + w; i++) {
            setPixel(i, y, color);
            setPixel(i, y + h, color);
        }
        for (i = y; i < y + h; i++) {
            setPixel(x, i, color);
            setPixel(x + w, i, color);
        }
    }



}
