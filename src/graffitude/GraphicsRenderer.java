package graffitude;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.LinkedList;

import javax.imageio.ImageIO;

/**
 *
 * @author pce
 */
public class GraphicsRenderer {

    private int width = 700;
    private int height = 887;

    private void generateGraphics(PixelArray pixelArray, int g2dStyle) throws Exception {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // pixelArray.setBackground(-16726016);
        // pixelArray.setBackground(255255255);
        // pixelArray.setBackground(-255255255);
        pixelArray.setBackground(0);

        for (int x = 0; x < pixelArray.getWidth(); x++) {
            for (int y = 0; y < pixelArray.getHeight(); y++) {
                int clr = pixelArray.getPixel(x, y);
                g2d.setColor(new Color(clr));

                switch (g2dStyle) {
                    case 1:
                        g2d.fillRect(x, y, 1, 1);
                        break;
                    case 2:
                        g2d.fillOval(x, y, 8, 8);
                        break;
                    default:
                        if (x % 2 == 0) {
                            g2d.fillOval(x, y, 8, 8);
                        } else {
                            g2d.fillRect(x, y, 1, 1);
                        }
                        break;
                }

            }
        }

        g2d.dispose();
        RenderedImage rendImage = bufferedImage;

        int frames = 1;
        for (int i = 0; i < frames; i++) {
            File file = new File(i + ".png");
            ImageIO.write(rendImage, "png", file);
            System.out.println("File: " + file.getAbsolutePath());
        }

    }

    public void generate(String filename, LinkedList<PixelFilterable> filterPipeline, Options options) {
        try {
            // TODO  do not require file if (options.isGeneratingPic)
            final File file = new File(filename);

            final BufferedImage image;
            if (file.canRead()) {
                image = ImageIO.read(file);
                width = image.getWidth();
                height = image.getHeight();
            } else {
                // create image with width, height
                width = options.width;
                height = options.height;
                BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                RenderedImage rendImage = bufferedImage;
                ImageIO.write(rendImage, "png", file);
                image = bufferedImage;
            }

            PixelArray pixelArray = new PixelArray(width, height);

            // init pixelArray with image data
            int clr;
            for (int y = 0; y < pixelArray.getHeight(); y++) {
                for (int x = 0; x < pixelArray.getWidth(); x++) {

                    clr = image.getRGB(x, y);
                    pixelArray.setPixel(x, y, clr);
                }
            }

            if (options.isGeneratingPic) {
                System.out.println("apply isGeneratingPic");
                Graphics2D g2d = image.createGraphics();
                g2d.setColor(new Color(155180255));
                /*
                for (int x = 0; x < pixelArray.getWidth(); x++) {
                    for (int y = 0; y < pixelArray.getHeight(); y++) {
                    }
                }
                */
                PytaghorasTreePainter treePainer = new PytaghorasTreePainter();
                treePainer.setOptions(options);
                // pixelArray = treePainer.paintTree(pixelArray, width - width/4, height, width, height);
                pixelArray = treePainer.paint(pixelArray);
            }

            for (PixelFilterable filter : filterPipeline) {
                System.out.println("iterate linked list of filters to apply: " + filter.getClass().getSimpleName());
                pixelArray.applyFilter(filter);
            }

            generateGraphics(pixelArray, options.g2d_style);

        } catch (Exception ex) {
            System.out.println(filename + " generate graphics failed... \n" + ex.getMessage());
            System.out.println(ex);
        }
    }

}
