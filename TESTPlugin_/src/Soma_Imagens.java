import java.awt.AWTEvent;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Soma_Imagens implements PlugIn, DialogListener{
	
	ImagePlus imp3 = IJ.openImage("C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/imagem3.jpg");
	ImageProcessor processador3 = imp3.getProcessor();
	ImagePlus imp4 = IJ.openImage("C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/imagem4.jpg");
	ImageProcessor processador4 = imp4.getProcessor();
	
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
		ImagePlus imagemSomada = IJ.createImage("Imagem Somada", "8-bit", imp3.getWidth(), imp3.getHeight(), 1);
		ImageProcessor processadorSoma = imagemSomada.getProcessor();
		if(opcaoSelecionada == "Truncamento") {
			
			for(int x = 0; x < processador3.getWidth(); x++) {
				for(int y = 0; y < processador3.getHeight(); y++) {
					processadorSoma.putPixel(x, y, processador3.getPixel(x, y) + processador4.getPixel(x, y));
					if(processadorSoma.getPixel(x, y) > 255) {
						processadorSoma.putPixel(x, y, 255);
					}else if(processadorSoma.getPixel(x, y) < 0) {
						processadorSoma.putPixel(x, y, 0);
	    			}
					
	    		}
	    	}
			//imagemSomada.updateAndDraw();
			imagemSomada.show();
			
		}else if(opcaoSelecionada == "Normalização") {
			int vetorF[][] = new int[processador3.getWidth()][processador3.getHeight()];
			int fMin = 255;
			int fMax = 0;
			int soma = 0;
			for(int x = 0; x < processador3.getWidth(); x++) {
				for(int y = 0; y < processador3.getHeight(); y++) {
					soma = processador3.getPixel(x, y) + processador4.getPixel(x, y);
					vetorF[x][y] = soma;
					//Verificação do fMin e fMax dentre as duas imagens
					if(processador3.getPixel(x, y) > fMax) {
						fMax = processador3.getPixel(x, y);
						if(processador4.getPixel(x, y) > fMax) {
							fMax = processador4.getPixel(x, y);
						}
					}else if(processador3.getPixel(x, y) < fMin) {
						fMin = processador3.getPixel(x, y);
						if(processador4.getPixel(x, y) < fMin) {
							fMin = processador4.getPixel(x, y);
						}
					}
				}
			}
			
			int resultadoG = 0;
						
			for(int x = 0; x < processador3.getWidth(); x++) {
				for(int y = 0; y < processador3.getHeight(); y++) {
					resultadoG = (255 / (fMax - fMin)) * (vetorF[x][y] - fMin);
					processadorSoma.putPixel(x, y, resultadoG);
				}
			}
			imagemSomada.show();
			
		}else if(opcaoSelecionada == "Wrapping") {
			
			int soma;
			for(int x = 0; x < processador3.getWidth(); x++) {
				for(int y = 0; y < processador3.getHeight(); y++) {
					soma = processador3.getPixel(x, y) + processador4.getPixel(x, y);
					if(soma > 255) {
						soma = soma - 255;
					}
					processadorSoma.putPixel(x, y, soma);
				}
			}
			imagemSomada.show();
			
		}
	}
	
	
	@Override
    public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
		if (interfaceGrafica.wasCanceled()) return false;
		
		return true;
	}
}
