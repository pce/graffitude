/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graffitude;

/**
 *
 * @author pce
 */
interface PixelFilterable {
    public PixelArray filter(PixelArray pixelArray);
    public void setOptions(Options options);
}
