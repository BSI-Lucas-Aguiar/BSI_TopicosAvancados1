import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.gui.GenericDialog;

import ij.IJ;
import ij.ImagePlus;

public class Conversao_RGB_Grayscale implements PlugIn {
	public void run(String arg) {
		apresentarInterfaceGrafica();
	}
	
	public void apresentarInterfaceGrafica() {
		GenericDialog interfaceGrafica = new GenericDialog("Convers�o de Imagem RGB");
		//interfaceGrafica.addDialogListener(this);
		
		String[] estrategia = {"M�dia RGB", "Luminosidade RGB","Desatura��o RGB"};
		interfaceGrafica.addRadioButtonGroup("Escolha uma das Tr�s estrat�gias abaixo:", estrategia, 1, 3,  "M�dia RGB");
		interfaceGrafica.addCheckbox("Criar nova imagem?", false);
		interfaceGrafica.showDialog();
		
		ImagePlus imagemRGB = IJ.getImage();
		ImageProcessor processadorRGB = imagemRGB.getProcessor();
		
		if (interfaceGrafica.wasCanceled()) {
			IJ.showMessage("Convers�o cancelada!");
		}else{
			if (interfaceGrafica.wasOKed()) {
				String opcaoSelecionada = interfaceGrafica.getNextRadioButton();
				if(opcaoSelecionada == "M�dia RGB") {
					ConverteMedia(processadorRGB, imagemRGB, interfaceGrafica.getNextBoolean());
				}else {
					if(opcaoSelecionada == "Luminosidade RGB") {
						ConvertePeso(processadorRGB, imagemRGB, interfaceGrafica.getNextBoolean());
					}else {
						if(opcaoSelecionada == "Desatura��o RGB") {
							ConverteDessaturacao(processadorRGB, imagemRGB, interfaceGrafica.getNextBoolean());
						}
					}
				}
				
			}
		}
	}
	
	//Fun��o para converter pela M�dia
	public void ConverteMedia(ImageProcessor processadorRGB, ImagePlus imagemRGB, Boolean CriaImagem) {
		int mediaRGB = 0;
		int vetorRGB[] = new int[3];
		
		if(CriaImagem == true) {
			ImagePlus imagemNova = IJ.createImage("Imagem Nova M�diaRGB", "8-bit", imagemRGB.getWidth(), imagemRGB.getHeight(), 1);
			ImageProcessor processadorNova = imagemNova.getProcessor();
			imagemNova.show();
			for(int x = 0; x < processadorRGB.getWidth(); x++) {
				for(int y = 0; y < processadorRGB.getHeight(); y++) {
					vetorRGB[0] = processadorRGB.getPixel(x, y, null) [0];
					vetorRGB[1] = processadorRGB.getPixel(x, y, null) [1];
					vetorRGB[2] = processadorRGB.getPixel(x, y, null) [2];
						
					mediaRGB = (vetorRGB[0]+vetorRGB[1]+vetorRGB[2])/3;
								
					processadorNova.putPixel(x, y, mediaRGB);
				}
			}
			imagemNova.updateAndDraw();
			
		}else{
			for(int x = 0; x < processadorRGB.getWidth(); x++) {
				for(int y = 0; y < processadorRGB.getHeight(); y++) {
					vetorRGB[0] = processadorRGB.getPixel(x, y, null) [0];
					vetorRGB[1] = processadorRGB.getPixel(x, y, null) [1];
					vetorRGB[2] = processadorRGB.getPixel(x, y, null) [2];
						
					mediaRGB = (vetorRGB[0]+vetorRGB[1]+vetorRGB[2])/3;
					
					vetorRGB[0] = mediaRGB;
					vetorRGB[1] = mediaRGB;
					vetorRGB[2] = mediaRGB;
								
					processadorRGB.putPixel(x, y, vetorRGB);
				}
			}
			imagemRGB.updateAndDraw();
		}
	}
	
	//Fun��o para converter Luminosidade pelo Peso
	public void ConvertePeso(ImageProcessor processadorRGB, ImagePlus imagemRGB, Boolean CriaImagem) {
		int pesoRGB = 0;
		int vetorRGB[] = new int[3];
		
		double pesoR = 0.2125;
		double pesoG = 0.7154;
		double pesoB = 0.072;
		
		if(CriaImagem == true) {
			ImagePlus imagemNova = IJ.createImage("Imagem Nova LuminosidadeRGB", "8-bit", imagemRGB.getWidth(), imagemRGB.getHeight(), 1);
			ImageProcessor processadorNova = imagemNova.getProcessor();
			imagemNova.show();
			for(int x = 0; x < processadorRGB.getWidth(); x++) {
				for(int y = 0; y < processadorRGB.getHeight(); y++) {
					vetorRGB[0] = processadorRGB.getPixel(x, y, null) [0];
					vetorRGB[1] = processadorRGB.getPixel(x, y, null) [1];
					vetorRGB[2] = processadorRGB.getPixel(x, y, null) [2];
							
					pesoRGB = (int) (pesoR*vetorRGB[0] + pesoG*vetorRGB[1] + pesoB*vetorRGB[2]);
								
					processadorNova.putPixel(x, y, pesoRGB);
				}
			}
			imagemNova.updateAndDraw();
			
		}else{
			for(int x = 0; x < processadorRGB.getWidth(); x++) {
				for(int y = 0; y < processadorRGB.getHeight(); y++) {
					vetorRGB[0] = processadorRGB.getPixel(x, y, null) [0];
					vetorRGB[1] = processadorRGB.getPixel(x, y, null) [1];
					vetorRGB[2] = processadorRGB.getPixel(x, y, null) [2];
						
					pesoRGB = (int) (pesoR*vetorRGB[0] + pesoG*vetorRGB[1] + pesoB*vetorRGB[2]);
					
					vetorRGB[0] = pesoRGB;
					vetorRGB[1] = pesoRGB;
					vetorRGB[2] = pesoRGB;

					processadorRGB.putPixel(x, y, vetorRGB);
				}
			}
			imagemRGB.updateAndDraw();
		}
	}
	
	//Fun��o para converter Dessatura��o pelo Peso 
		public void ConverteDessaturacao(ImageProcessor processadorRGB, ImagePlus imagemRGB, Boolean CriaImagem) {
			int pesoRGB = 0;
			int vetorRGB[] = new int[3];
			
			double pesoR = 0.299;
			double pesoG = 0.587;
			double pesoB = 0.114;
			
			if(CriaImagem == true) {
				ImagePlus imagemNova = IJ.createImage("Imagem Nova Dessatura��oRGB", "8-bit", imagemRGB.getWidth(), imagemRGB.getHeight(), 1);
				ImageProcessor processadorNova = imagemNova.getProcessor();
				imagemNova.show();
				for(int x = 0; x < processadorRGB.getWidth(); x++) {
					for(int y = 0; y < processadorRGB.getHeight(); y++) {
						vetorRGB[0] = processadorRGB.getPixel(x, y, null) [0];
						vetorRGB[1] = processadorRGB.getPixel(x, y, null) [1];
						vetorRGB[2] = processadorRGB.getPixel(x, y, null) [2];
								
						pesoRGB = (int) (pesoR*vetorRGB[0] + pesoG*vetorRGB[1] + pesoB*vetorRGB[2]);
									
						processadorNova.putPixel(x, y, pesoRGB);
					}
				}
				imagemNova.updateAndDraw();
				
			}else{
				for(int x = 0; x < processadorRGB.getWidth(); x++) {
					for(int y = 0; y < processadorRGB.getHeight(); y++) {
						vetorRGB[0] = processadorRGB.getPixel(x, y, null) [0];
						vetorRGB[1] = processadorRGB.getPixel(x, y, null) [1];
						vetorRGB[2] = processadorRGB.getPixel(x, y, null) [2];
							
						pesoRGB = (int) (pesoR*vetorRGB[0] + pesoG*vetorRGB[1] + pesoB*vetorRGB[2]);
						
						vetorRGB[0] = pesoRGB;
						vetorRGB[1] = pesoRGB;
						vetorRGB[2] = pesoRGB;
									
						processadorRGB.putPixel(x, y, vetorRGB);
					}
				}
				imagemRGB.updateAndDraw();
			}
		}
}