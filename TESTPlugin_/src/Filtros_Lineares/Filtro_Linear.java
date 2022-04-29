package Filtros_Lineares;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Filtro_Linear implements PlugIn{

	ImagePlus imagemOriginal = IJ.getImage();
	ImageProcessor processadorOriginal = imagemOriginal.getProcessor();
	ImagePlus imagemGerada = IJ.createImage("Imagem com filtro", "8-bit",imagemOriginal.getWidth(), imagemOriginal.getHeight(), 1);
	ImageProcessor processadorGerado = imagemGerada.getProcessor();
	
	public void run(String arg) {
		GenericDialog interfaceGrafica = new GenericDialog("Aplicação de Filtros Lineares");
		String[] selecaoImagens = {"Passa Baixas", "Passa Altas","Borda"};
		interfaceGrafica.addRadioButtonGroup("Selecione o tipo de Filtro:", selecaoImagens, 1, 3,  "Passa Baixas");
		interfaceGrafica.showDialog();
		
		
		
		if (interfaceGrafica.wasCanceled()) {
			IJ.showMessage("Aplicação de filtro cancelada!");
		}else{
			if(interfaceGrafica.wasOKed()) {
				String opcaoSelecionada = interfaceGrafica.getNextRadioButton();
				if(opcaoSelecionada == "Passa Baixas") {
					aplicaBaixa();
				}else if(opcaoSelecionada == "Passa Altas") {
					aplicaAlta();
				}else if(opcaoSelecionada == "Borda") {
					aplicaBorda();
				}
			}
		}
	}
	
	public void aplicaBaixa() {
		int novoValor;
		
		for (int x = 0; x< processadorOriginal.getWidth() - 1; x++) {
            for (int y = 0; y < processadorOriginal.getHeight() - 1; y++) {
            	novoValor = 0;
            	for(int k = x-1; k <= x+1; k++) {
					for(int l = y-1; l <= y+1; l++) {
						novoValor += processadorOriginal.getPixel(k, l);
					}
				}
            	
            	processadorGerado.putPixel(x, y, (novoValor / 9));
              
            }
        }
		imagemGerada.show();
	}
	
	public void aplicaAlta() {
		int novoValor;
		//Cada posição irá conter um dos pixels buscados para realização da operação
		int[] vetor = new int[9];
		
		
		for (int x = 0; x < processadorOriginal.getWidth(); x++){
            for (int y = 0; y < processadorOriginal.getHeight(); y++){
            	novoValor = 0;
            	
            	vetor[0] = processadorOriginal.getPixel(x-1,y+1);
	         	vetor[1] = processadorOriginal.getPixel(x+1,y+1);
	         	vetor[2] = processadorOriginal.getPixel(x, y+1);
	         	vetor[3] = processadorOriginal.getPixel(x-1, y-1);
	         	vetor[4] = processadorOriginal.getPixel(x+1,y-1);
	         	vetor[5] = processadorOriginal.getPixel(x-1, y);
	         	vetor[6] = processadorOriginal.getPixel(x+1, y);
	         	vetor[7] = processadorOriginal.getPixel(x, y-1);
	         	vetor[8] = processadorOriginal.getPixel(x, y);
	         	
	         	for (int i = 0; i < 9; i++){
	         		if (i == 0){
	         			novoValor = novoValor + (vetor[i] * -1);
	         		}else if(i > 0 && i < 8){
	         			novoValor = novoValor + (vetor[i] * -1);
	         		}else{
	         			novoValor = novoValor + (vetor[i] * 9);
	         		}
	         	}
	         	processadorGerado.putPixel(x, y, novoValor);
            }
        }
        imagemGerada.show();
	}
	
	public void aplicaBorda() {
		int novoValor;
		int[] vetor = new int[9];
      
        for (int x = 0; x< processadorOriginal.getWidth(); x++) {
            for (int y = 0; y < processadorOriginal.getHeight(); y++) {
            	novoValor = 0;
	         	vetor[0] = processadorOriginal.getPixel(x-1,y+1);
	         	vetor[1] = processadorOriginal.getPixel(x+1,y+1);
	         	vetor[2] = processadorOriginal.getPixel(x, y+1);
	         	vetor[3] = processadorOriginal.getPixel(x-1, y-1);
	         	vetor[4] = processadorOriginal.getPixel(x+1,y-1);
	         	vetor[5] = processadorOriginal.getPixel(x-1, y);
	         	vetor[6] = processadorOriginal.getPixel(x+1, y);
	         	vetor[7] = processadorOriginal.getPixel(x, y-1);
	         	vetor[8] = processadorOriginal.getPixel(x, y);
         	   
	         	for (int i = 0; i < 9; i++) {
	         		if (i < 3) {
	         			novoValor = novoValor + (vetor[i] * -1);
	         		} else if (i >= 3 && i < 8) {
	         			novoValor = novoValor + (vetor[i] * 1);
	         		} else {
	         			novoValor = novoValor + (vetor[i] * -2);
	         		}
	         	}
	         	
	         	processadorGerado.putPixel(x, y, novoValor);
            }
        }
        
        imagemGerada.show();
	}
	
}
