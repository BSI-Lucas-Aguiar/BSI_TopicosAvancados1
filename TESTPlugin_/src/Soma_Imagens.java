import java.awt.AWTEvent;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Soma_Imagens implements PlugIn, DialogListener{
	
	ImagePlus imp3 = IJ.openImage("C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/imagem3.jpg");
	ImageProcessor processador3 = IJ.getProcessor();
	ImagePlus imp4 = IJ.openImage("C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/imagem4.jpg");
	ImageProcessor processador4 = IJ.getProcessor();
	
	public void run(String arg) {
		
		imp3.show();
		imp4.show();
		
		GenericDialog interfaceGrafica = new GenericDialog("Soma de Imagens");
		interfaceGrafica.addDialogListener(this);
		String[] metodoOverflow = {"Truncamento","Normalização","Wrapping"};
		interfaceGrafica.addRadioButtonGroup("Qual o metodo que será utilizado?", metodoOverflow, 1, 3,  "Truncamento");
		interfaceGrafica.showDialog();
		
		
		if (interfaceGrafica.wasCanceled()) {
			IJ.showMessage("Soma cancelada!");
		}else{
			if(interfaceGrafica.wasOKed()) {
				String opcaoSelecionada = interfaceGrafica.getNextRadioButton();
				somaImagens(opcaoSelecionada);
				
				IJ.showMessage("Soma Realizada!");
			}
		}
		
		
	}
	
	private void somaImagens(String opcaoSelecionada) {
		ImagePlus imagemSomada = IJ.createImage("Imagem Somada", "8-bit", imp3.getHeight(), imp3.getWidth(), 1);
		ImageProcessor processadorSoma = IJ.getProcessor();
		if(opcaoSelecionada == "Truncamento") {
			for(int x = 0; x < processador3.getWidth(); x++) {
				for(int y = 0; y < processador3.getHeight(); y++) {
					processadorSoma.putPixel(x, y, processador3.getPixel(x, y)+processador4.getPixel(x, y));
					if(processadorSoma.getPixel(x, y) > 255) {
						processadorSoma.putPixel(x, y, 255);
					}else if(processadorSoma.getPixel(x, y) < 255) {
						processadorSoma.putPixel(x, y, 0);
	    			}
					
	    		}
	    	}
			imagemSomada.updateAndDraw();
			imagemSomada.show();
			
		}else if(opcaoSelecionada == "Normalização") {
			
		}else if(opcaoSelecionada == "Wrapping") {
			
		}
	}
	
	
	@Override
    public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
		if (interfaceGrafica.wasCanceled()) return false;
		
		return true;
	}
}
