import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Gerar_HSV implements PlugIn {
	
	public void run(String arg) {
		ImagePlus imagemOriginal = IJ.getImage();
		ImageProcessor processadorOriginal = imagemOriginal.getProcessor();
		
		ImagePlus imagemMatiz = IJ.createImage("Matiz", "8-bit", processadorOriginal.getWidth(), processadorOriginal.getHeight(), 1);
		ImageProcessor processadorMatiz = imagemMatiz.getProcessor();
		
		ImagePlus imagemSaturacao = IJ.createImage("Saturação", "8-bit", processadorOriginal.getWidth(), processadorOriginal.getHeight(), 1);
		ImageProcessor processadorSaturacao = imagemSaturacao.getProcessor();
		
		ImagePlus imagemBrilho = IJ.createImage("Brilho", "8-bit", processadorOriginal.getWidth(), processadorOriginal.getHeight(), 1);
		ImageProcessor processadorBrilho = imagemBrilho.getProcessor();
		
		//Chamada a cada pixel percorrido
		for(int x = 0 ; x < processadorOriginal.getWidth() ; x++){
			for(int y = 0 ; y < processadorOriginal.getHeight() ; y++){
				processadorMatiz.putPixel(x, y, matiz(processadorOriginal.getPixel(x, y, null)));
				processadorSaturacao.putPixel(x, y, saturacao(processadorOriginal.getPixel(x, y, null)));
				processadorBrilho.putPixel(x, y, brilho(processadorOriginal.getPixel(x, y, null)));
			}
		}
		
		imagemMatiz.show();
		imagemSaturacao.show();
		imagemBrilho.show();
		
	}
	
	//Verificação de Maior, utilizado por todas as 3 partes do HSV
	public double verificaMaior(double[] vetorRecebido) {
		double maior = vetorRecebido[0];
		
		for(int x = 0 ; x < 3 ; x++) {
			if(maior < vetorRecebido[x]) {
				maior = vetorRecebido[x];
			}
		}
		
		return maior;
	}
	//Verificação de Menor, utilizado por todas as 3 partes do HSV
	public double verificaMenor(double[] vetorRecebido) {
		double menor = vetorRecebido[0];
		
		for(int x = 0 ; x < 3 ; x++) {
			if(menor > vetorRecebido[x]) {
				menor = vetorRecebido[x];
			}
		}
		
		return menor;
	}
	
	//Verificação da Matiz - 'H'SV
	public int matiz(int[] pixelAtual) {
		
		//Resultado H - Matiz após a primeira normalizacao
		double hue = 0;
		double pixelNormalizado[] = new double[3];
		
		pixelNormalizado[0] = normalizacao(pixelAtual[0], 0, 255, 0, 1);
		pixelNormalizado[1] = normalizacao(pixelAtual[1], 0, 255, 0, 1);
		pixelNormalizado[2] = normalizacao(pixelAtual[2], 0, 255, 0, 1);
		
		double maior = verificaMaior(pixelNormalizado);
		double menor = verificaMenor(pixelNormalizado);
		
		if( maior == pixelNormalizado[0] && pixelNormalizado[1] >= pixelNormalizado[2]) {
			hue = 60 * ((pixelNormalizado[1] - pixelNormalizado[2])/(maior - menor)) + 0;
	
		} else if (maior == pixelNormalizado[0] && pixelNormalizado[1] < pixelNormalizado[2]) {
			hue = 60 * ((pixelNormalizado[1] - pixelNormalizado[2])/(maior - menor)) + 360;
		
		} else if (maior == pixelNormalizado[1]) {
			hue = 60 * ((pixelNormalizado[2] - pixelNormalizado[0])/(maior - menor)) + 120;
			
		} else if (maior == pixelNormalizado[2]) {
			hue = 60 * ((pixelNormalizado[0] - pixelNormalizado[1])/(maior - menor)) + 240;

		}
		
		//Resultado novamente normalizado de 360 para 255
		int resultado = (int) normalizacao(hue, 0, 360, 0, 255);
		return resultado;
	}
	
	//Verificação da Saturação - H'S'V
	public int saturacao(int[] pixelAtual) {
		double pixelNormalizado[] = new double[3];
		
		pixelNormalizado[0] = normalizacao(pixelAtual[0], 0, 255, 0, 1);
		pixelNormalizado[1] = normalizacao(pixelAtual[1], 0, 255, 0, 1);
		pixelNormalizado[2] = normalizacao(pixelAtual[2], 0, 255, 0, 1);
		
		double maior = verificaMaior(pixelNormalizado);
		double menor = verificaMenor(pixelNormalizado);
		
		//Resultado normalizado
		double resultado = (double) ((maior - menor)/maior); 
		resultado = normalizacao(resultado, 0, 1, 0, 255);
		return (int) resultado;
	}
	
	//Verificação do Brilho - HS'V'
	public int brilho(int[] pixelAtual) {
		//Convertido para double para chamada da função 'verificaMaior'
		double pixelDouble[] = new double[3];
		
		pixelDouble[0] = pixelAtual[0];
		pixelDouble[1] = pixelAtual[1];
		pixelDouble[2] = pixelAtual[2];
		
		//Como retorna sempre o maior foi chamada somente a função do Maior
		double maior = verificaMaior(pixelDouble);
		
		return (int) maior;
	}
	
	public double normalizacao(double result, double valorMin, double valorMax, double novoMinimo, double novoMaximo) {
		double valorNormalizado = (result - valorMin) * ( (novoMaximo - novoMinimo)/(valorMax - valorMin) ) + novoMinimo;
		return valorNormalizado;	
	}
}

