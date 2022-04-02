import java.awt.AWTEvent;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Equalizacao_de_Histograma implements PlugIn, DialogListener {
	public void run(String arg) {
		
		GenericDialog interfaceGrafica = new GenericDialog("Equalização de Histograma");
		
		ImagePlus imagemRGB = IJ.getImage();
		ImageProcessor processadorRGB = IJ.getProcessor();
		
		
		
		if (interfaceGrafica.wasCanceled()) {
			//IJ.showMessage("PlugIn cancelado!");
		}
		else {
			if (interfaceGrafica.wasOKed()) {
				
			}
		}
		
		
		
	}

	@Override
    public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
        if (interfaceGrafica.wasCanceled()) return false;
        
        return true;
	}
}
