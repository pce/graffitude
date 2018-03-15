package graffitude;

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

        // what about filter specific options and for each usage ...
        // --filter "scale" 123 "pointed"
        for (int arg = 0; arg < args.length; arg++) {

            System.out.println(arg + ": " + args[arg]);

            if (args[arg].toLowerCase().equals("stripe")) {
                System.out.println("option stripe");
                options.stripe = true;
                // first optional parameter is: scale_factor (stripe)
                int argNext = arg + 1;
                if (args[argNext] != null && isNumeric(args[argNext])) {
                    options.stripe_scalefactor = Integer.parseInt(args[argNext]);
                }
            }
            if (args[arg].toLowerCase().equals("scale")) {
                System.out.println("option scale");
                options.scale = true;
                // first parameter of scale is: scale_factor (scale)
                int argNext = arg + 1;
                if (args[argNext] != null && isNumeric(args[argNext])) {
                    options.scale_scalefactor = Integer.parseInt(args[argNext]);
                }

            }
            if (args[arg].toLowerCase().equals("pointed")) {
                System.out.println("option pointed");
                options.pointed = true;
            }
            if (args[arg].toLowerCase().equals("split")) {
                System.out.println("option split");
                options.split = true;
            }

        }

        try {
            // 0 = filename
            filename = args[0].toString();
        } catch (Exception e) {
            System.out.println("Error: at least a inputfilename as first argument is required");
        }

        GraphicsRenderer renderer = new GraphicsRenderer();
        renderer.generate(filename, options);

    }

}
