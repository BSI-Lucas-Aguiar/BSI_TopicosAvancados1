package ModificacoesRGB;
import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Separa_RGB_ implements PlugIn{
	public void run(String arg) {
		//Seleciona a imagem ativa
		ImagePlus imagem = IJ.getImage();
		//IJ.showMessage("Imagem selecionada: "+imagem.getTitle());
		//IJ.run(imagem, "8-bit", "");
		
		
				
		//Cria as imagens que irão receber a divisão da imagem principal
		ImagePlus imagemVermelha = IJ.createImage("Imagem Vermelha", "8-bit", imagem.getWidth(), imagem.getHeight(), 1);
		ImagePlus imagemVerde = IJ.createImage("Imagem Verde", "8-bit", imagem.getWidth(), imagem.getHeight(), 1);
		ImagePlus imagemAzul = IJ.createImage("Imagem Azul", "8-bit", imagem.getWidth(), imagem.getHeight(), 1);
		
		//Cria os Processadores para a imagem original e para as imagens divididas
		ImageProcessor processador = imagem.getProcessor();
		ImageProcessor processadorVermelho = imagemVermelha.getProcessor();
		ImageProcessor processadorVerde = imagemVerde.getProcessor();
		ImageProcessor processadorAzul = imagemAzul.getProcessor();
		
		
		//Processamento Vermelho (Red)
		imagemVermelha.show();
		for(int x = 0; x < processadorVermelho.getWidth(); x++) {
            for(int y = 0; y < processadorVermelho.getHeight(); y++) {
            	processadorVermelho.putPixelValue(x, y, processador.getPixel(x, y, null) [0]);
            }
        }
		//LUT Vermelho
		IJ.run(imagemVermelha, "Red", "");
		imagemVermelha.updateAndDraw();
		
		//Processamento Verde (Green)
		imagemVerde.show();
		for(int x = 0; x < processadorVerde.getWidth(); x++) {
            for(int y = 0; y < processadorVerde.getHeight(); y++) {
            	processadorVerde.putPixelValue(x, y, processador.getPixel(x, y, null) [1]);
            }
        }
		//LUT Verde
		IJ.run(imagemVerde, "Green", "");
		imagemVerde.updateAndDraw();
		
		//Processamento Azul (Blue)
		imagemAzul.show();
		for(int x = 0; x < processadorAzul.getWidth(); x++) {
            for(int y = 0; y < processadorAzul.getHeight(); y++) {
            	processadorAzul.putPixelValue(x, y, processador.getPixel(x, y, null) [2]);
            }
        }
		//LUT Azul
		IJ.run(imagemAzul, "Blue", "");
		imagemAzul.updateAndDraw();
		
		imagem.hide();
		
	}
}
