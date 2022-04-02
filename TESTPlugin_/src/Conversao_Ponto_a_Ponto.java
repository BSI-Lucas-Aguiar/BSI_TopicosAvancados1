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

    ImageProcessor processadorCopia = processadorRGB.duplicate();
    
    int vetorRGB[] = new int[3];
    

    public void run(String arg) {
    	for(int x = 0; x < processadorRGB.getWidth(); x++) {
    		for(int y = 0; y < processadorRGB.getHeight(); y++) {
    			vetorRGB[0] = processadorRGB.getPixel(x, y, null) [0];
    			vetorRGB[1] = processadorRGB.getPixel(x, y, null) [1];
    			vetorRGB[2] = processadorRGB.getPixel(x, y, null) [2];
    			processadorCopia.putPixel(x, y, vetorRGB);
    		}
    	}
    	
        GenericDialog interfaceGrafica = new GenericDialog("Conversão Ponto a Ponto");
        interfaceGrafica.addDialogListener(this);
        interfaceGrafica.addSlider("Brilho", -255, 255, 0, 1);
        interfaceGrafica.addSlider("Contraste", -255, 255, 0, 1);
        interfaceGrafica.addSlider("Solarização", 0, 255, 0, 1);
        interfaceGrafica.addSlider("Dessaturação", 0, 1, 1, 0.01);
        interfaceGrafica.showDialog();
        

        if (interfaceGrafica.wasCanceled()) {
        	restauraImagem();
        	imagemRGB.updateAndDraw();
            //IJ.showMessage("Conversão cancelada!");
        } else {
            if (interfaceGrafica.wasOKed()) {
            	imagemRGB.updateAndDraw();
            	//IJ.showMessage("Modificações realizadas!");
            }
        }
        imagemRGB.updateAndDraw();


    }
    
    public void restauraImagem() {
    	for(int x = 0; x < processadorRGB.getWidth(); x++) {
    		for(int y = 0; y < processadorRGB.getHeight(); y++) {
    			vetorRGB[0] = processadorCopia.getPixel(x, y, null) [0];
    			vetorRGB[1] = processadorCopia.getPixel(x, y, null) [1];
    			vetorRGB[2] = processadorCopia.getPixel(x, y, null) [2];
    			processadorRGB.putPixel(x, y, vetorRGB);
    		}
    	}
    }

    @Override
    public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
        if (interfaceGrafica.wasCanceled()) return false;
        //IJ.log("Valores selecionados:");
        //IJ.log("Brilho:" + interfaceGrafica.getNextNumber());
        //IJ.log("Contraste:" + interfaceGrafica.getNextNumber());
        //IJ.log("Solarização:" + interfaceGrafica.getNextNumber());
        //IJ.log("Dessaturação:" + interfaceGrafica.getNextNumber());
        //IJ.log("\n");

        int brilho = (int) interfaceGrafica.getNextNumber();
        int contraste = (int) interfaceGrafica.getNextNumber();
        int solarizacao = (int) interfaceGrafica.getNextNumber();
        int dessaturacao = (int) interfaceGrafica.getNextNumber();

        atualizaImagem(brilho, contraste, solarizacao, dessaturacao);

        return true;
    }
    
    public void atualizaImagem(int brilho, int contraste, int solarizacao, int dessaturacao) {
    	double fatorContraste = ((259*(contraste + 255)) / (255*(259 - contraste)));
    	
		for(int x = 0; x < processadorRGB.getWidth(); x++) {
    		for(int y = 0; y < processadorRGB.getHeight(); y++) {
    			//******************************************
    			//Brilho R
    			vetorRGB[0] = brilho + processadorCopia.getPixel(x, y, null) [0];
    			if(vetorRGB[0] > 255) {
    				vetorRGB[0] = 255;
    			}else {
    				if(vetorRGB[0] < 0) {
    					vetorRGB[0] = 0;
    				}
    			}
    			
    			//Brilho G
    			vetorRGB[1] = brilho + processadorCopia.getPixel(x, y, null) [1];
    			if(vetorRGB[1] > 255) {
    				vetorRGB[1] = 255;
    			}else {
    				if(vetorRGB[1] < 0) {
    					vetorRGB[1] = 0;
    				}
    			}
    			
    			//Brilho B
    			vetorRGB[2] = brilho + processadorCopia.getPixel(x, y, null) [2];
    			if(vetorRGB[2] > 255) {
    				vetorRGB[2] = 255;
    			}else {
    				if(vetorRGB[2] < 0) {
    					vetorRGB[2] = 0;
    				}
    			}
    			
    			//******************************************
    			
				//Contraste R
				vetorRGB[0] = (int) fatorContraste*(vetorRGB[0] - 128) + 128;
    			if(vetorRGB[0] > 255) {
    				vetorRGB[0] = 255;
    			}else {
    				if(vetorRGB[0] < 0) {
    					vetorRGB[0] = 0;
    				}
    			}
    			
    			//Contraste G
    			vetorRGB[1] = (int) fatorContraste*(vetorRGB[1] - 128) + 128;
    			if(vetorRGB[1] > 255) {
    				vetorRGB[1] = 255;
    			}else {
    				if(vetorRGB[1] < 0) {
    					vetorRGB[1] = 0;
    				}
    			}
    			
    			//Contraste B
    			vetorRGB[2] = (int) fatorContraste*(vetorRGB[2] - 128) + 128;
    			if(vetorRGB[2] > 255) {
    				vetorRGB[2] = 255;
    			}else {
    				if(vetorRGB[2] < 0) {
    					vetorRGB[2] = 0;
    				}
    			}
    			
    			//******************************************
    			//Solarização Acima da Limiar
    			if(processadorRGB.getPixel(x, y, null) [0] > solarizacao) {
    				vetorRGB[0] = vetorRGB[0] - solarizacao;
    				if(vetorRGB[0] > 255) {
    					vetorRGB[0] = 255;
    				}else {
    					if(vetorRGB[0] < 0) {
    						vetorRGB[0] = 0;
    					}
    				}
    			}
    			
    			if(processadorRGB.getPixel(x, y, null) [1] > solarizacao) {
    				vetorRGB[1] = vetorRGB[1] - solarizacao;
    				if(vetorRGB[1] > 255) {
    					vetorRGB[1] = 255;
    				}else {
    					if(vetorRGB[1] < 0) {
    						vetorRGB[1] = 0;
    					}
    				}
    			}
    			
    			if(processadorRGB.getPixel(x, y, null) [2] > solarizacao) {
    				vetorRGB[2] = vetorRGB[2] - solarizacao;
    				if(vetorRGB[2] > 255) {
    					vetorRGB[2] = 255;
    				}else {
    					if(vetorRGB[2] < 0) {
    						vetorRGB[2] = 0;
    					}
    				}
    			}
    			
    			//******************************************
    			//Dessaturação
    			double valorY = 0.299 * vetorRGB[0] + 0.587 * vetorRGB[1] + 0.114 * vetorRGB[2];

    			vetorRGB[0] = (int) (valorY + dessaturacao * (vetorRGB[0] - valorY));
    			vetorRGB[1] = (int) (valorY + dessaturacao * (vetorRGB[1] - valorY));
    			vetorRGB[2] = (int) (valorY + dessaturacao * (vetorRGB[2] - valorY));

    			
    			processadorRGB.putPixel(x, y, vetorRGB);
    		}
    	}
		imagemRGB.updateAndDraw();
    }
    	
}
