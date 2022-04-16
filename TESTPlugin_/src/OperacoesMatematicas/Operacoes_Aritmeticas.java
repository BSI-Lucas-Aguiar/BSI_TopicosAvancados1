package OperacoesMatematicas;
import java.awt.AWTEvent;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.ImageCalculator;
import ij.plugin.PlugIn;


public class Operacoes_Aritmeticas implements PlugIn, DialogListener{
	
	//Diretório das imagens no meu PC - Está na mesma pasta no Git
	ImagePlus imp1 = IJ.openImage("C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/imagem1.jpg");
	ImagePlus imp2 = IJ.openImage("C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/imagem2.jpg");
	ImagePlus imp3 = IJ.openImage("C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/imagem3.jpg");
	ImagePlus imp4 = IJ.openImage("C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/imagem4.jpg");
	ImagePlus imp5 = IJ.openImage("C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/imagem5.jpg");
	ImagePlus imp6 = IJ.openImage("C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/imagem6.jpg");
	
	public void run(String arg) {

		imp1.show();
		imp2.show();
		
		
		GenericDialog interfaceGrafica = new GenericDialog("Operações Aritméticas");
		interfaceGrafica.addMessage("Selecione as imagens que serão utilizadas");
		interfaceGrafica.addDialogListener(this);
		String[] selecaoImagens = {"Imagem 1 e 2 - Células", "Imagem 3 e 4 - 8-bit","Imagem 5 e 6 - RGB"};
		interfaceGrafica.addRadioButtonGroup("Escolha quais imagens serão trabalhadas:", selecaoImagens, 1, 3,  "Imagem 1 e 2 - Células");
		interfaceGrafica.showDialog();
		
		
		String vetorOperacoes[] = {"Add create", "Subtract create", "Multiply create", 
				"Divide create", "AND create", "OR create", "XOR create", "Min create", 
				"Max create", "Average create", "Difference create", "Copy create", "Transparent-zero create"};
		
		
		if (interfaceGrafica.wasCanceled()) {
			cancelar();
			//IJ.showMessage("Binarização cancelada!");
		}else{
			if (interfaceGrafica.wasOKed()) {
				String opcaoSelecionada = interfaceGrafica.getNextRadioButton();
				if(opcaoSelecionada == "Imagem 1 e 2 - Células") {
					for(int x = 0; x <= 12; x++) {
						realizarOperacoes(imp1, imp2, vetorOperacoes[x]);
					}
					
				}else if(opcaoSelecionada == "Imagem 3 e 4 - 8-bit") {
					for(int x = 0; x <= 12; x++) {
						realizarOperacoes(imp3, imp4, vetorOperacoes[x]);
					}
				}else if(opcaoSelecionada == "Imagem 5 e 6 - RGB") {
					for(int x = 0; x <= 12; x++) {
						realizarOperacoes(imp5, imp6, vetorOperacoes[x]);
					}
				}
				
				IJ.showMessage("Operações realizadas e imagens salvas!");
			}
		}
	}
	
	
	void realizarOperacoes(ImagePlus impRecebida1, ImagePlus impRecebida2, String operacao){
			ImagePlus imagemSoma = ImageCalculator.run(impRecebida1, impRecebida2, operacao);
			imagemSoma.setTitle(impRecebida1.getTitle()+" "+operacao+" "+impRecebida2.getTitle());
			imagemSoma.show();
			IJ.saveAs(imagemSoma, "PNG", "C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/Imagens Após Operações/"+imagemSoma.getTitle());
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
