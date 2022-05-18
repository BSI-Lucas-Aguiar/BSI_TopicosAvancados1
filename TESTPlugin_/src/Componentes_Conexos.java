import java.util.LinkedList;
import java.util.Queue;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Componentes_Conexos implements PlugIn{

	ImagePlus imagemOriginal = IJ.openImage("C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/imagem-conexa.jpg");
	ImageProcessor processadorOriginal = imagemOriginal.getProcessor();
	
	ImagePlus imagemRotulada = IJ.createImage("Imagem Rotulada", "8-bit", imagemOriginal.getWidth(), imagemOriginal.getHeight(), 1);
	ImageProcessor processadorRotulado = imagemRotulada.getProcessor();
	
	//Implementação da fila
	Queue<Integer> filaX = new LinkedList<>();
	Queue<Integer> filaY = new LinkedList<>();
	
	int escalaCinza = 40;
	int contadorConexoes = 0;
	
	public void run(String arg) {
		imagemOriginal.show();
		imagemRotulada.show();
		
		for(int x = 0; x < imagemOriginal.getWidth(); x++) {
			for(int y = 0; y < imagemOriginal.getHeight(); y++) {
				processadorRotulado.putPixel(x, y, 0);
				//230 para pegar possíveis erros da imagem criada (a imagem foi criada no paint)
				if(processadorOriginal.getPixel(x, y) >= 230) {
					processadorRotulado.putPixel(x, y, 255);
				}
			}
		}
		imagemRotulada.updateAndDraw();
		
		for(int x = 0; x < imagemOriginal.getWidth(); x++) {
			for(int y = 0; y < imagemOriginal.getHeight(); y++) {
				
				if(processadorRotulado.getPixel(x, y) == 255) {
					contadorConexoes++;
					
					verificaConexao(x, y);
					int tamanhoFila = filaX.size();
					int valorX;
					int valorY;
					escalaCinza += 30;
					for(int z = 0; z < tamanhoFila; z++) {
						valorX = filaX.remove();
						valorY = filaY.remove();
						processadorRotulado.putPixel(valorX, valorY, escalaCinza);
					}
				}
			}
		}
		IJ.showMessage("Componentes Conectados Encontrados: "+contadorConexoes);
		imagemRotulada.updateAndDraw();
	}
	
	public void verificaConexao(int x, int y) {
		filaX.add(x);
		filaY.add(y);
		//O pixel rotulado recebe o valor 254 para identificação
		processadorRotulado.putPixel(x, y, 254);
		
		if((processadorRotulado.getPixel(x+1, y) == 255) && (processadorRotulado.getPixel(x+1, y) != 254)) {
			verificaConexao(x+1, y);
		}
		
		if((processadorRotulado.getPixel(x, y+1) == 255) && (processadorRotulado.getPixel(x, y+1) != 254)){
			verificaConexao(x, y+1);
		}
		
		if((processadorRotulado.getPixel(x-1, y) == 255) && (processadorRotulado.getPixel(x-1, y) != 254)){
			verificaConexao(x-1, y);
		}
		
		if((processadorRotulado.getPixel(x, y-1) == 255) && (processadorRotulado.getPixel(x, y-1) != 254)){
			verificaConexao(x, y-1);
		}
	}
}
