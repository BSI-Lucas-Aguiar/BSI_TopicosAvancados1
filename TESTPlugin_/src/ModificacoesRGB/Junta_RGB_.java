package ModificacoesRGB;
import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Junta_RGB_  implements PlugIn {
    public void run(String arg) {
    	ImagePlus imagemVermelha = WindowManager.getImage("Imagem Vermelha");
    	ImagePlus imagemVerde = WindowManager.getImage("Imagem Verde");
    	ImagePlus imagemAzul = WindowManager.getImage("Imagem Azul");
    	
    	ImagePlus imagemRGB = IJ.createImage("Imagem RGB", "RGB", imagemVermelha.getWidth(), imagemVermelha.getHeight(), 1);
    	
    	ImageProcessor processadorRGB = imagemRGB.getProcessor();
    	ImageProcessor processadorVermelho = imagemVermelha.getProcessor();
		ImageProcessor processadorVerde = imagemVerde.getProcessor();
		ImageProcessor processadorAzul = imagemAzul.getProcessor();
		
		
		
		//Processamento RGB
		imagemRGB.show();
		int vetorRGB[] = new int[3];
		for(int x = 0; x < processadorRGB.getWidth(); x++) {
			for(int y = 0; y < processadorRGB.getHeight(); y++) {
				vetorRGB[0] = processadorVermelho.getPixel(x, y);
				vetorRGB[1] = processadorVerde.getPixel(x, y);
				vetorRGB[2] = processadorAzul.getPixel(x, y);
				processadorRGB.putPixel(x, y, vetorRGB);
			}
		}
		
		imagemVermelha.hide();		
		imagemVerde.hide();
		imagemAzul.hide();
		
		imagemRGB.updateAndDraw();
		
    }
}