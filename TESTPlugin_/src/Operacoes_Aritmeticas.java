import java.awt.AWTEvent;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.ImageCalculator;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;


public class Operacoes_Aritmeticas implements PlugIn, DialogListener{
	
	ImagePlus imp1 = IJ.openImage("C:/Users/lukas/Desktop/Faculdade -  Sétimo/Tópicos Avançados 1/Trabalho 8/imagem1.jpg");
	ImagePlus imp2 = IJ.openImage("C:/Users/lukas/Desktop/Faculdade -  Sétimo/Tópicos Avançados 1/Trabalho 8/imagem2.jpg");
	ImagePlus imp3 = IJ.openImage("C:/Users/lukas/Desktop/Faculdade -  Sétimo/Tópicos Avançados 1/Trabalho 8/imagem3.jpg");
	ImagePlus imp4 = IJ.openImage("C:/Users/lukas/Desktop/Faculdade -  Sétimo/Tópicos Avançados 1/Trabalho 8/imagem4.jpg");
	ImagePlus imp5 = IJ.openImage("C:/Users/lukas/Desktop/Faculdade -  Sétimo/Tópicos Avançados 1/Trabalho 8/imagem5.jpg");
	ImagePlus imp6 = IJ.openImage("C:/Users/lukas/Desktop/Faculdade -  Sétimo/Tópicos Avançados 1/Trabalho 8/imagem6.jpg");
	
	public void run(String arg) {

		imp1.show();
		imp2.show();
		
		//ImagePlus imagem = IJ.getImage();
		//ImageProcessor processador = IJ.getProcessor();
		
		
		
		GenericDialog interfaceGrafica = new GenericDialog("Operações Aritméticas");
		interfaceGrafica.addMessage("Selecione as imagens que serão utilizadas");
		interfaceGrafica.addDialogListener(this);
		String[] selecaoImagens = {"Imagem 1 e 2 - Células", "Imagem 3 e 4 - 8-bit","Imagem 5 e 6 - RGB"};
		interfaceGrafica.addRadioButtonGroup("Escolha quais imagens serão trabalhadas:", selecaoImagens, 1, 3,  "Imagem 1 e 2 - Células");
		interfaceGrafica.showDialog();
		
		
		if (interfaceGrafica.wasCanceled()) {
			cancelar();
			//IJ.showMessage("Binarização cancelada!");
		}else{
			String opcaoSelecionada = interfaceGrafica.getNextRadioButton();
			realizarOperacoes(opcaoSelecionada);
			//imagem.updateAndDraw();
			//IJ.showMessage("Binarização concluida!");
		}
		
		
	}
	
	void realizarOperacoes(String opcaoSelecionada){
		if(opcaoSelecionada == "Imagem 1 e 2 - Células") {
			ImagePlus imagem1e2Soma = ImageCalculator.run(imp1, imp2, "Add create");
				
			
		}else if(opcaoSelecionada == "Imagem 3 e 4 - 8-bit") {

		}else if(opcaoSelecionada == "Imagem 5 e 6 - RGB") {

		}
	}
	
	private void cancelar() {
		imp1.close();
		imp2.close();
		imp3.close();
		imp4.close();
		imp5.close();
		imp6.close();
	}
	
	private void mostrarImagens(String opcaoSelecionada){
		if(opcaoSelecionada == "Imagem 1 e 2 - Células") {
			imp1.show();
			imp2.show();
			
			imp3.hide();
			imp4.hide();
			imp5.hide();
			imp6.hide();
		}else if(opcaoSelecionada == "Imagem 3 e 4 - 8-bit") {
			imp3.show();
			imp4.show();
			
			imp1.hide();
			imp2.hide();
			imp5.hide();
			imp6.hide();
		}else if(opcaoSelecionada == "Imagem 5 e 6 - RGB") {
			imp5.show();
			imp6.show();
			
			imp1.hide();
			imp2.hide();
			imp3.hide();
			imp4.hide();
		}
	}
	
	@Override
    public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
		if (interfaceGrafica.wasCanceled()) return false;
		
		String opcaoSelecionada = interfaceGrafica.getNextRadioButton();
		mostrarImagens(opcaoSelecionada);
		
		return true;
	}
}
