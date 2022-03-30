import java.awt.AWTEvent;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Conversao_Ponto_a_Ponto implements PlugIn, DialogListener {

    ImagePlus imagemRGB = IJ.getImage();
    ImageProcessor processadorRGB = imagemRGB.getProcessor();

    ImagePlus imagemOriginal = IJ.createImage("ImagemOriginal", "RGB", imagemRGB.getWidth(), imagemRGB.getHeight(), 1);
    ImageProcessor processadorOriginal = imagemOriginal.getProcessor();
    
    int vetorRGB[] = new int[3];
    

    public void run(String arg) {
    	for(int x = 0; x < processadorRGB.getWidth(); x++) {
    		for(int y = 0; y < processadorRGB.getHeight(); y++) {
    			vetorRGB[0] = processadorRGB.getPixel(x, y, null) [0];
    			vetorRGB[1] = processadorRGB.getPixel(x, y, null) [1];
    			vetorRGB[2] = processadorRGB.getPixel(x, y, null) [2];
    			processadorOriginal.putPixel(x, y, vetorRGB);
    		}
    	}
    	
        GenericDialog interfaceGrafica = new GenericDialog("Convers�o Ponto a Ponto");
        interfaceGrafica.addDialogListener(this);
        interfaceGrafica.addSlider("Brilho", -255, 255, 0, 1);
        interfaceGrafica.addSlider("Contraste", -255, 255, 0, 1);
        interfaceGrafica.addSlider("Solariza��o", 0, 255, 128, 1);
        interfaceGrafica.addSlider("Dessatura��o", 0, 255, 128, 1);
        interfaceGrafica.showDialog();
        

        if (interfaceGrafica.wasCanceled()) {
            IJ.showMessage("Convers�o cancelada!");
        } else {
            if (interfaceGrafica.wasOKed()) {

            }
        }
        imagemRGB.updateAndDraw();

        interfaceGrafica.getNextNumber();

    }

    @Override
    public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
        if (interfaceGrafica.wasCanceled()) return false;
        //IJ.log("Valores selecionados:");
        //IJ.log("Brilho:" + interfaceGrafica.getNextNumber());
        //IJ.log("Contraste:" + interfaceGrafica.getNextNumber());
        //IJ.log("Solariza��o:" + interfaceGrafica.getNextNumber());
        //IJ.log("Dessatura��o:" + interfaceGrafica.getNextNumber());
        //IJ.log("\n");

        int brilho = (int) interfaceGrafica.getNextNumber();
        int contraste = (int) interfaceGrafica.getNextNumber();
        int solarizacao = (int) interfaceGrafica.getNextNumber();
        int dessaturacao = (int) interfaceGrafica.getNextNumber();

        atualizaBrilho(brilho);
        atualizaContraste(contraste);

        return true;
    }
    
    public void atualizaBrilho(int brilho) {
    	
        for(int x = 0; x < processadorRGB.getWidth(); x++) {
    		for(int y = 0; y < processadorRGB.getHeight(); y++) {
    			//Brilho R
    			vetorRGB[0] = brilho + processadorOriginal.getPixel(x, y, null) [0];
    			if((brilho + processadorOriginal.getPixel(x, y, null) [0]) > 255) {
    				vetorRGB[0] = 255;
    			}else {
    				if((brilho + processadorOriginal.getPixel(x, y, null) [0]) < 0) {
    					vetorRGB[0] = 0;
    				}
    			}
    			
    			//Brilho G
    			vetorRGB[1] = brilho + processadorOriginal.getPixel(x, y, null) [1];
    			if((brilho + processadorOriginal.getPixel(x, y, null) [1]) > 255) {
    				vetorRGB[1] = 255;
    			}else {
    				if((brilho + processadorOriginal.getPixel(x, y, null) [1]) < 0) {
    					vetorRGB[1] = 0;
    				}
    			}
    			
    			//Brilho B
    			vetorRGB[2] = brilho + processadorOriginal.getPixel(x, y, null) [2];
    			if((brilho + processadorOriginal.getPixel(x, y, null) [2]) > 255) {
    				vetorRGB[2] = 255;
    			}else {
    				if((brilho + processadorOriginal.getPixel(x, y, null) [2]) < 0) {
    					vetorRGB[2] = 0;
    				}
    			}
    			
    			processadorRGB.putPixel(x, y, vetorRGB);
    		}
    	}

        imagemRGB.updateAndDraw();
    }
    
    public void atualizaContraste(int contraste) {
    	int nivelContraste = (259*(contraste + 255)) / (255*(259 - contraste));
    	
    	
    	for(int x = 0; x < processadorRGB.getWidth(); x++) {
    		for(int y = 0; y < processadorRGB.getHeight(); y++) {
    			vetorRGB[0] = nivelContraste*(processadorOriginal.getPixel(x, y, null) [0] - 128) + 128;
    			vetorRGB[1] = nivelContraste*(processadorOriginal.getPixel(x, y, null) [1] - 128) + 128;
    			vetorRGB[2] = nivelContraste*(processadorOriginal.getPixel(x, y, null) [2] - 128) + 128;
    			
    			processadorRGB.putPixel(x, y, vetorRGB);
    		}
    	}

        imagemRGB.updateAndDraw();
    }
}