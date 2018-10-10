package graffitude;

import java.util.LinkedList;

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
        Options options = new Options();
        LinkedList<PixelFilterable> filterPipeline = new LinkedList<>();

        int argNext;
        for (int arg = 0; arg < args.length; arg++) {

            System.out.println(arg + ": " + args[arg]);

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
            if (args[arg].toLowerCase().equals("genrand")) {
                System.out.println("option genrand");
                options.genrand = true;
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
                if (args[argNext] != null &&  args[argNext].startsWith("--")) {
                    options.scale_scalemode = args[argNext].substring(2);
                    System.out.println("scalemode: " +options.scale_scalemode );
                }
                scaleFilter.setOptions(options);
                filterPipeline.add(scaleFilter);
            }
            if (args[arg].toLowerCase().equals("pointed")) {
                System.out.println("option pointed");
                filterPipeline.add(new PixelPointedFilter());
            }
            if (args[arg].toLowerCase().equals("waterfill")) {
                System.out.println("option waterfill");
                filterPipeline.add(new PixelFloodFilter());
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
            System.out.println("Error: at least a inputfilename as first argument is required");
        }

        GraphicsRenderer renderer = new GraphicsRenderer();
        renderer.generate(filename, filterPipeline, options);

    }

}
