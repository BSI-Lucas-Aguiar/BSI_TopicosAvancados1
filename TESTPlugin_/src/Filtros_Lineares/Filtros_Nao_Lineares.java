package Filtros_Lineares;

import java.util.Arrays;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Filtros_Nao_Lineares implements PlugIn{

	ImagePlus imagemOriginal = IJ.getImage();
	ImageProcessor processadorOriginal = imagemOriginal.getProcessor();
	ImagePlus imagemGerada = IJ.createImage("Imagem com filtro", "8-bit",imagemOriginal.getWidth(), imagemOriginal.getHeight(), 1);
	ImageProcessor processadorGerado = imagemGerada.getProcessor();
	
	public void run(String arg) {
		GenericDialog interfaceGrafica = new GenericDialog("Aplicação de Filtros Não Lineares");
		String[] selecaoImagens = {"Média", "Moda","Mediana"};
		interfaceGrafica.addRadioButtonGroup("Selecione o tipo de Filtro:", selecaoImagens, 1, 3,  "Média");
		interfaceGrafica.showDialog();
		
		
		
		if (interfaceGrafica.wasCanceled()) {
			IJ.showMessage("Aplicação de filtro cancelada!");
		}else{
			if(interfaceGrafica.wasOKed()) {
				String opcaoSelecionada = interfaceGrafica.getNextRadioButton();
				if(opcaoSelecionada == "Média") {
					aplicaMedia();
				}else if(opcaoSelecionada == "Moda") {
					aplicaModa();
				}else if(opcaoSelecionada == "Mediana") {
					aplicaMediana();
				}
			}
		}
	}
	
	private void aplicaMediana() {
		int[] vetor = new int[9];
		double median;
		
		for (int x = 1; x< processadorOriginal.getWidth()-1; x++) {
            for (int y = 1; y < processadorOriginal.getHeight()-1; y++) {
            	vetor[0] = processadorOriginal.getPixel(x-1,y+1);
	         	vetor[1] = processadorOriginal.getPixel(x+1,y+1);
	         	vetor[2] = processadorOriginal.getPixel(x, y+1);
	         	vetor[3] = processadorOriginal.getPixel(x-1, y-1);
	         	vetor[4] = processadorOriginal.getPixel(x+1,y-1);
	         	vetor[5] = processadorOriginal.getPixel(x-1, y);
	         	vetor[6] = processadorOriginal.getPixel(x+1, y);
	         	vetor[7] = processadorOriginal.getPixel(x, y-1);
	         	vetor[8] = processadorOriginal.getPixel(x, y);
	         	//Ordena o vetor divide por 2 e pega ultimo elemento, para obter o valor do meio
	         	Arrays.sort(vetor);
	         	if (vetor.length % 2 == 0) {
	         	    median = ((double)vetor[vetor.length/2] + (double)vetor[vetor.length/2 - 1])/2;
	         	} else {
	         	    median = (double) vetor[vetor.length/2];
	         	}
	         	
	         	processadorGerado.putPixel(x, y, (int) median);
              
            }
        }
		imagemGerada.show();
	}
	
	private void aplicaModa() {
		int[] vetor = new int[9];
		
		for (int x = 1; x< processadorOriginal.getWidth()-1; x++) {
            for (int y = 1; y < processadorOriginal.getHeight()-1; y++) {
            	vetor[0] = processadorOriginal.getPixel(x-1,y+1);
	         	vetor[1] = processadorOriginal.getPixel(x+1,y+1);
	         	vetor[2] = processadorOriginal.getPixel(x, y+1);
	         	vetor[3] = processadorOriginal.getPixel(x-1, y-1);
	         	vetor[4] = processadorOriginal.getPixel(x+1,y-1);
	         	vetor[5] = processadorOriginal.getPixel(x-1, y);
	         	vetor[6] = processadorOriginal.getPixel(x+1, y);
	         	vetor[7] = processadorOriginal.getPixel(x, y-1);
	         	vetor[8] = processadorOriginal.getPixel(x, y);
	         	Arrays.sort(vetor);
	         	//Utilizado para pegar o valor mais presente no vetor
	         	processadorGerado.putPixel(x, y, buscaModa(vetor));
              
            }
        }
		imagemGerada.show();
	}
	
	private int buscaModa(int[] vetor) {
		int contador = 1, tempCount;
		int popular = vetor[0];
		int temp = 0;
		
		//Percorre o vetor e para cada posição percorre novamente contando a incidencia desse valor
		for (int i = 0; i < (vetor.length - 1); i++) {
			temp = vetor[i];
			tempCount = 0;
			for (int j = 1; j < vetor.length; j++) {
				if (temp == vetor[j]) tempCount++;
			}
			if (tempCount > contador) {
				popular = temp;
				contador = tempCount;
			}
		}
		return popular;
	}

	private void aplicaMedia() {
		int[] vetor = new int[9];
		int totalParcial;
		
		for (int x = 1; x < processadorOriginal.getWidth()-1; x++) {
            for (int y = 1; y < processadorOriginal.getHeight()-1; y++) {
            	totalParcial = 0;
            	vetor[0] = processadorOriginal.getPixel(x-1,y+1);
            	totalParcial += vetor[0];
	         	vetor[1] = processadorOriginal.getPixel(x+1,y+1);
	         	totalParcial += vetor[1];
	         	vetor[2] = processadorOriginal.getPixel(x, y+1);
	         	totalParcial += vetor[2];
	         	vetor[3] = processadorOriginal.getPixel(x-1, y-1);
	         	totalParcial += vetor[3];
	         	vetor[4] = processadorOriginal.getPixel(x+1,y-1);
	         	totalParcial += vetor[4];
	         	vetor[5] = processadorOriginal.getPixel(x-1, y);
	         	totalParcial += vetor[5];
	         	vetor[6] = processadorOriginal.getPixel(x+1, y);
	         	totalParcial += vetor[6];
	         	vetor[7] = processadorOriginal.getPixel(x, y-1);
	         	totalParcial += vetor[7];
	         	vetor[8] = processadorOriginal.getPixel(x, y);
	         	totalParcial += vetor[8];
	         	totalParcial = totalParcial / 9;
	         	
	         	processadorGerado.putPixel(x, y, totalParcial);
              
            }
        }
		imagemGerada.show();
	}

}
