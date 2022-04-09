import java.awt.AWTEvent;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;


public class Operacoes_Aritmeticas implements PlugIn, DialogListener{
	
	public void run(String arg) {
		
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = IJ.getProcessor();
		
		
		
		GenericDialog interfaceGrafica = new GenericDialog("Modelo");
		interfaceGrafica.addMessage("Teste");
		interfaceGrafica.addDialogListener(this);
		
		interfaceGrafica.addSlider("Exemplo", 0, 255, 128, 1);
		interfaceGrafica.showDialog();
		
		
		if (interfaceGrafica.wasCanceled()) {
			cancelar();
			//IJ.showMessage("Binarização cancelada!");
		}else{
			imagem.updateAndDraw();
			//IJ.showMessage("Binarização concluida!");
		}
		
		
	}
	
	private void cancelar() {
		
	
	}
	
	@Override
    public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
		if (interfaceGrafica.wasCanceled()) return false;
		
		return true;
	}
}
