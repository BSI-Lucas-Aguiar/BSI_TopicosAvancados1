package Sliders;
import java.awt.AWTEvent;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Limiar_de_Binarizacao implements PlugIn, DialogListener {
	
	ImagePlus imagem = IJ.getImage();
	ImageProcessor processador = IJ.getProcessor();
	ImageProcessor processadorCopia = processador.duplicate();
	
	
	
	public void run(String arg) {
		GenericDialog interfaceGrafica = new GenericDialog("Limiar de Binarização");
		interfaceGrafica.addMessage("A direita do limiar branco, a esquerda preto");
		interfaceGrafica.addDialogListener(this);
		interfaceGrafica.addSlider("Limiar", 0, 255, 128, 1);
		interfaceGrafica.showDialog();
		
		
		
		if (interfaceGrafica.wasCanceled()) {
			cancelarHistograma();
			//IJ.showMessage("Binarização cancelada!");
		}else{
			imagem.updateAndDraw();
			//IJ.showMessage("Binarização concluida!");
		}
		
	}
	
	private void cancelarHistograma() {
		imagem.setProcessor(processadorCopia);
		imagem.updateAndDraw();
	}
	
	@Override
    public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
        if (interfaceGrafica.wasCanceled()) return false;
        int limiar = (int) interfaceGrafica.getNextNumber();
        
        
        for(int x = 0; x < processador.getWidth(); x++) {
			for(int y = 0; y < processador.getHeight(); y++) {
				int pixelAtual = processadorCopia.getPixel(x, y);
				if(pixelAtual > limiar) {
    				processador.putPixel(x, y, 255);
				}else if(pixelAtual < limiar) {
					processador.putPixel(x, y, 0);
    			}
    		}
    	}
		
        imagem.updateAndDraw();
        return true;
	 }
}
