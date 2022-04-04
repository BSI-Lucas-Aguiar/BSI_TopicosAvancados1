import java.awt.AWTEvent;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Ajustar_Histograma implements PlugIn, DialogListener {
	
	
	int valorMin = 0;
	int valorMax = 255;
	int valorLow = 255;
	int valorHigh = 0;
	
	int vetExpansao[] = new int[256];
	
	public void run(String arg) {
		
		GenericDialog interfaceGrafica = new GenericDialog("Alteração de Histograma");
		
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = imagem.getProcessor();
		
		ImagePlus imagemNova = IJ.createImage("Imagem Nova Cinza", "8-bit", imagem.getWidth(), imagem.getHeight(), 1);
		ImageProcessor processadorNova = imagemNova.getProcessor();
		
		//Cópia da imagem RGB para escala de cinza na imagem 8-bit
		int mediaRGB = 0;
		int vetorRGB[] = new int[3];
		for(int x = 0; x < processador.getWidth(); x++) {
			for(int y = 0; y < processador.getHeight(); y++) {
				vetorRGB[0] = processador.getPixel(x, y, null) [0];
				vetorRGB[1] = processador.getPixel(x, y, null) [1];
				vetorRGB[2] = processador.getPixel(x, y, null) [2];
					
				mediaRGB = (vetorRGB[0]+vetorRGB[1]+vetorRGB[2])/3;
							
				processadorNova.putPixel(x, y, mediaRGB);
			}
		}
		imagemNova.show();
		
		
		
		IJ.log(valorLow+" "+valorHigh);
		
		String[] estrategia = {"Expansão", "Equalização"};
		interfaceGrafica.addRadioButtonGroup("Escolha uma das Três estratégias abaixo:", estrategia, 1, 2,  "Expansão");
		interfaceGrafica.showDialog();
		
		if (interfaceGrafica.wasCanceled()) {
			//IJ.showMessage("PlugIn cancelado!");
		}
		else {
			if (interfaceGrafica.wasOKed()) {
				String opcaoSelecionada = interfaceGrafica.getNextRadioButton();
				if(opcaoSelecionada == "Expansão") {
					
					//Verifica tipo de imagem RGB, caso seja RGB cria uma nova imagem 8-bit
					if(imagem.getType() == ImagePlus.COLOR_RGB) {
						//Chamada da Expansão
						expansao(processadorNova);
						imagemNova.updateAndDraw();
					}else {
						//Chamada da Expansão
						expansao(processador);
						imagem.updateAndDraw();
					}
					
				}else {
					if(opcaoSelecionada == "Equalização"){

					}
				}
				
			}
		}
		
		
		
	}
	
	//Valor Low e High
	public void verificaMaiorMenor(ImageProcessor processador) {
		for(int x = 0; x < processador.getWidth(); x++) {
			for(int y = 0; y < processador.getHeight(); y++){
				if(processador.getPixel(x, y) <= valorLow) {
					valorLow = processador.getPixel(x, y);
				}
				if(processador.getPixel(x, y) >= valorHigh) {
					valorHigh = processador.getPixel(x, y);
				}
			}
		}
	}
	//Irá receber sempre uma imagem cinza, caso a imagem selecionada seja RGB será criada uma nova
	public void expansao(ImageProcessor processador) {
		verificaMaiorMenor(processador);
		int resultado = 0;
		for (int x = 0; x < processador.getWidth(); x++) {
			for (int y = 0; y < processador.getHeight(); y++) {
				resultado = (valorMin + (processador.getPixel(x, y) - valorLow) * ((valorMax - valorMin)/ (valorHigh - valorLow)));
				processador.putPixel(x, y, resultado);
			}
		}

	}
	
	public void equalizacao() {
		
	}

	@Override
    public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
        if (interfaceGrafica.wasCanceled()) return false;
        
        return true;
	}
}
