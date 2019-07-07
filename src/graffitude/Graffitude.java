package graffitude;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 *
 * @author pce
 */
public class Graffitude {

    public static final int FILTERS = 0;

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String filename = "";
        String diffFilename = "";
        Options options = new Options();
        LinkedList<PixelFilterable> filterPipeline = new LinkedList<>();
        boolean isVerbose = false;

        int argNext;
        for (int arg = 0; arg < args.length; arg++) {

            if (isVerbose) {
                System.out.println(arg + ": " + args[arg]);
            }
            if (args[arg].toLowerCase().equals("-v")) {
                isVerbose = true;
            }
            // global options like width and height
            if (args[arg].toLowerCase().equals("-w")) {
                argNext = arg + 1;
                if (args[argNext] != null && isNumeric(args[argNext])) {
                    options.width = Integer.parseInt(args[argNext]);
                }
            }

            if (args[arg].toLowerCase().equals("-h")) {
                argNext = arg + 1;
                if (args[argNext] != null && isNumeric(args[argNext])) {
                    options.height = Integer.parseInt(args[argNext]);
                }
            }

            // generative
            if (args[arg].toLowerCase().equals("isGeneratingPic")) {
                System.out.println("option isGeneratingPic");
                options.isGeneratingPic = true;
            }

            // mod
            if (args[arg].toLowerCase().equals("stripe")) {
                System.out.println("option stripe");

                PixelFilterable stripeFilter = new PixelStripeFilter();
                // first optional parameter is: scale_factor (stripe)
                argNext = arg + 1;
                if (args[argNext] != null && isNumeric(args[argNext])) {
                    options.stripe_scalefactor = Integer.parseInt(args[argNext]);
                    stripeFilter.setOptions(options);
                }
                filterPipeline.add(stripeFilter);
            }

            if (args[arg].toLowerCase().equals("scale")) {
                System.out.println("option scale");

                PixelFilterable scaleFilter = new PixelScaleFilter();

                // first parameter of scale is: scale_factor (scale)
                argNext = arg + 1;
                if (args[argNext] != null && isNumeric(args[argNext])) {
                    options.scale_scalefactor = Integer.parseInt(args[argNext]);
                }
                argNext++;
                if (args[argNext] != null && args[argNext].startsWith("--")) {
                    options.scale_scalemode = args[argNext].substring(2);
                    System.out.println("scalemode: " + options.scale_scalemode);
                }
                scaleFilter.setOptions(options);
                filterPipeline.add(scaleFilter);
            }
            if (args[arg].toLowerCase().equals("pointed")) {
                System.out.println("option pointed");
                filterPipeline.add(new PixelPointedFilter());
            }
            if (args[arg].toLowerCase().equals("peak")) {
                System.out.println("option peak");
                filterPipeline.add(new PixelPeakDetectFilter());
            }
            if (args[arg].toLowerCase().equals("waterfill")) {
                System.out.println("option waterfill");
                filterPipeline.add(new PixelFloodFilter());
            }
            if (args[arg].toLowerCase().equals("diff")) {
                // DiffMaskFilter?
                System.out.println("option diff");
                // first parameter of scale is: scale_factor (scale)
                argNext = arg + 1;
                // && (args[argNext]).length()
                if (args[argNext] != null) {
                    diffFilename = args[argNext];
                }
                BufferedImage image;
                File file = new File(diffFilename);
                if (file.canRead()) {
                    try {
                        image = ImageIO.read(file);

                        PixelArray pixelArray = new PixelArray(image.getWidth(), image.getHeight());
                        int clr;
                        for (int y = 0; y < image.getHeight(); y++) {
                            for (int x = 0; x < image.getWidth(); x++) {
                                clr = image.getRGB(x, y);
                                pixelArray.setPixel(x, y, clr);
                            }
                        }
                        filterPipeline.add(new PixelDiffFilter(pixelArray));

                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println(e.getMessage());
                    }

                } else {
                    System.out.println("File not found: " + diffFilename);
                }
            }
            if (args[arg].toLowerCase().equals("invert")) {
                System.out.println("option invert");
                filterPipeline.add(new PixelColorInvertFilter());
            }

            if (args[arg].toLowerCase().equals("split")) {
                System.out.println("option split");
                PixelFilterable splitFilter = new PixelSplitFilter();
                // first parameter of split is: tile, should be tiles tile
                argNext = arg + 1;
                if (args[argNext] != null && isNumeric(args[argNext])) {
                    options.paramInt1 = Integer.parseInt(args[argNext]);
                }

                splitFilter.setOptions(options);
                filterPipeline.add(splitFilter);

            }

        }

        try {
            // 0 = filename
            filename = args[0];//.toString();
        } catch (Exception e) {
            System.out.println("Note: at least a inputfilename as first argument is required to use filter");
            options.isGeneratingPic = true;
            // TODO no filename needed, file is unused
            filename = "../in.jpg";
        }

        GraphicsRenderer renderer = new GraphicsRenderer();
        renderer.generate(filename, filterPipeline, options);

    }

}
