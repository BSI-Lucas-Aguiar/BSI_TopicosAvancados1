import java.awt.AWTEvent;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
 
public class Conversao_Ponto_a_Ponto  implements PlugIn, DialogListener {
	public void run(String arg) {
		GenericDialog interfaceGrafica = new GenericDialog("Conversão Ponto a Ponto");
		interfaceGrafica.addDialogListener(this);
		interfaceGrafica.addSlider("Brilho", -255, 255, 0, 1);
		interfaceGrafica.addSlider("Contraste", 0, 255, 128, 1);
		interfaceGrafica.addSlider("Solarização", 0, 255, 128, 1);
		interfaceGrafica.addSlider("Dessaturação", 0, 255, 128, 1);
		interfaceGrafica.showDialog();
		
		ImagePlus imagemRGB = IJ.getImage();
		ImageProcessor processadorRGB = imagemRGB.getProcessor();
		
		int vetorRGB[] = new int[3];
		
		int valorBrilho = (int) interfaceGrafica.getNextNumber();
		
		if (interfaceGrafica.wasCanceled()) {
			IJ.showMessage("Conversão cancelada!");
		}else{
			if (interfaceGrafica.wasOKed()) {
				for(int x = 0; x < processadorRGB.getWidth(); x++) {
					for(int y = 0; y < processadorRGB.getHeight(); y++) {
						vetorRGB[0] = processadorRGB.getPixel(x, y, null) [0];
						vetorRGB[1] = processadorRGB.getPixel(x, y, null) [1];
						vetorRGB[2] = processadorRGB.getPixel(x, y, null) [2];
							
						vetorRGB[0] = valorBrilho + vetorRGB[0];
						vetorRGB[1] = valorBrilho + vetorRGB[1];
						vetorRGB[2] = valorBrilho + vetorRGB[2];
									
						processadorRGB.putPixel(x, y, vetorRGB);
					}
				}
			}
		}
		imagemRGB.updateAndDraw();
		
		interfaceGrafica.getNextNumber();
		
	}
	
	@Override
	public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
		if (interfaceGrafica.wasCanceled()) return false;
		IJ.log("Valores selecionados:");
		IJ.log("Brilho:" + interfaceGrafica.getNextNumber());
		IJ.log("Contraste:" + interfaceGrafica.getNextNumber());
		IJ.log("Solarização:" + interfaceGrafica.getNextNumber());
		IJ.log("Dessaturação:" + interfaceGrafica.getNextNumber());
        IJ.log("\n");
        return true;
    }
}