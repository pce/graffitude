package graffitude;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 *
 * @author pce
 */
public class GraphicsRenderer {

    private int width = 700;
    private int height = 887;

    private void generateGraphics(PixelArray pixelarray, int g2dStyle) throws Exception {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // pixelarray.setBackground(-16726016);
        // pixelarray.setBackground(255255255);
        // pixelarray.setBackground(-255255255);
        pixelarray.setBackground(0);

        for (int x = 0; x < pixelarray.getWidth(); x++) {
            for (int y = 0; y < pixelarray.getHeight(); y++) {
                int clr = pixelarray.getPixel(x, y);
                g2d.setColor(new Color(clr));

                switch (g2dStyle) {
                    case 1:
                        g2d.fillRect(x, y, 1, 1);
                    break;
                    case 2:
                        g2d.fillOval(x, y, 8, 8);
                    break;
                    default:
                        if (x%2==0) {
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
        }

    }


    public void generate(String filename, Options options) {
        try {
            final File file = new File(filename);
            final BufferedImage image = ImageIO.read(file);

            width = image.getWidth();
            height = image.getHeight();

            PixelArray pixelArray = new PixelArray(width, height);

            if (options.scale) {
                System.out.println("apply scale");
                PixelScaleFilter scaleFilter = new PixelScaleFilter(pixelArray);
                scaleFilter.setImage(image);
                // scaleFilter.set
                pixelArray.applyFilter(scaleFilter);
            }

            if (options.pointed) {
                System.out.println("apply pointed");
                PixelPointedFilter pointedFilter = new PixelPointedFilter(pixelArray);
                pointedFilter.setImage(image);
                pixelArray.applyFilter(pointedFilter);
            }

            if (options.split) {
                System.out.println("apply split");
                PixelSplitFilter splitFilter = new PixelSplitFilter(pixelArray);
                splitFilter.setImage(image);
                pixelArray.applyFilter(splitFilter);
            }

            if (options.stripe) {
                System.out.println("apply stripe");
                PixelStripeFilter stripeFilter = new PixelStripeFilter(pixelArray);
                stripeFilter.setImage(image);
                stripeFilter.setScalefactor(options.stripe_scalefactor);
                pixelArray.applyFilter(stripeFilter);
            }

            generateGraphics(pixelArray, options.g2d_style);

        } catch (Exception ex) {
            System.out.println(filename + "generate graphics failed... \n" + ex.getMessage());
        }
    }

}
