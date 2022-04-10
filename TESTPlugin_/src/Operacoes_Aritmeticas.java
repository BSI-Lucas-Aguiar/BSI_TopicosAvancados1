import java.awt.AWTEvent;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.ImageCalculator;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;


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
		
		
		if (interfaceGrafica.wasCanceled()) {
			cancelar();
			//IJ.showMessage("Binarização cancelada!");
		}else{
			if (interfaceGrafica.wasOKed()) {
				String opcaoSelecionada = interfaceGrafica.getNextRadioButton();
				if(opcaoSelecionada == "Imagem 1 e 2 - Células") {
					realizarOperacoes(imp1, imp2);
				}else if(opcaoSelecionada == "Imagem 3 e 4 - 8-bit") {
					realizarOperacoes(imp3, imp4);
				}else if(opcaoSelecionada == "Imagem 5 e 6 - RGB") {
					realizarOperacoes(imp5, imp6);
				}
				
				IJ.showMessage("Operações realizadas e imagens salvas!");
			}
		}
	}
	
	
	void realizarOperacoes(ImagePlus impRecebida1, ImagePlus impRecebida2){
			//Soma Add
			ImagePlus imagemSoma = ImageCalculator.run(impRecebida1, impRecebida2, "Add create");
			imagemSoma.setTitle(impRecebida1.getTitle()+" Add "+impRecebida2.getTitle());
			imagemSoma.show();
			IJ.saveAs(imagemSoma, "PNG", "C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/Imagens Após Operações/"+imagemSoma.getTitle());
			
			//Subtração Subtract
			ImagePlus imagemSubtracao = ImageCalculator.run(impRecebida1, impRecebida2, "Subtract create");
			imagemSubtracao.setTitle(impRecebida1.getTitle()+" Subtract "+impRecebida2.getTitle());
			imagemSubtracao.show();
			IJ.saveAs(imagemSubtracao, "PNG", "C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/Imagens Após Operações/"+imagemSubtracao.getTitle());
			
			//Multiplicar Multiply
			ImagePlus imagemMultiplicacao = ImageCalculator.run(impRecebida1, impRecebida2, "Multiply create");
			imagemMultiplicacao.setTitle(impRecebida1.getTitle()+" Multiply "+impRecebida2.getTitle());
			imagemMultiplicacao.show();
			IJ.saveAs(imagemMultiplicacao, "PNG", "C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/Imagens Após Operações/"+imagemMultiplicacao.getTitle());
			
			//Dividir Divide
			ImagePlus imagemDividir = ImageCalculator.run(impRecebida1, impRecebida2, "Divide create");
			imagemDividir.setTitle(impRecebida1.getTitle()+" Divide "+impRecebida2.getTitle());
			imagemDividir.show();
			IJ.saveAs(imagemDividir, "PNG", "C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/Imagens Após Operações/"+imagemDividir.getTitle());
			
			//AND E
			ImagePlus imagemAND = ImageCalculator.run(impRecebida1, impRecebida2, "AND create");
			imagemAND.setTitle(impRecebida1.getTitle()+" AND "+impRecebida2.getTitle());
			imagemAND.show();
			IJ.saveAs(imagemAND, "PNG", "C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/Imagens Após Operações/"+imagemAND.getTitle());
			
			//OR OU
			ImagePlus imagemOU = ImageCalculator.run(impRecebida1, impRecebida2, "OR create");
			imagemOU.setTitle(impRecebida1.getTitle()+" OR "+impRecebida2.getTitle());
			imagemOU.show();
			IJ.saveAs(imagemOU, "PNG", "C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/Imagens Após Operações/"+imagemOU.getTitle());
			
			//XOR OU Exclusivo
			ImagePlus imagemXOR = ImageCalculator.run(impRecebida1, impRecebida2, "XOR create");
			imagemXOR.setTitle(impRecebida1.getTitle()+" XOR "+impRecebida2.getTitle());
			imagemXOR.show();
			IJ.saveAs(imagemXOR, "PNG", "C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/Imagens Após Operações/"+imagemXOR.getTitle());
			
			//Min Mínimo
			ImagePlus imagemMin = ImageCalculator.run(impRecebida1, impRecebida2, "Min create");
			imagemMin.setTitle(impRecebida1.getTitle()+" Min "+impRecebida2.getTitle());
			imagemMin.show();
			IJ.saveAs(imagemMin, "PNG", "C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/Imagens Após Operações/"+imagemMin.getTitle());
			
			//Max Máximo
			ImagePlus imagemMax = ImageCalculator.run(impRecebida1, impRecebida2, "Max create");
			imagemMax.setTitle(impRecebida1.getTitle()+" Max "+impRecebida2.getTitle());
			imagemMax.show();
			IJ.saveAs(imagemMax, "PNG", "C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/Imagens Após Operações/"+imagemMax.getTitle());
			
			//Average Média
			ImagePlus imagemAverage = ImageCalculator.run(impRecebida1, impRecebida2, "Average create");
			imagemAverage.setTitle(impRecebida1.getTitle()+" Average "+impRecebida2.getTitle());
			imagemAverage.show();
			IJ.saveAs(imagemAverage, "PNG", "C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/Imagens Após Operações/"+imagemAverage.getTitle());
			
			//Difference Diferença
			ImagePlus imagemDifference = ImageCalculator.run(impRecebida1, impRecebida2, "Difference create");
			imagemDifference.setTitle(impRecebida1.getTitle()+" Difference "+impRecebida2.getTitle());
			imagemDifference.show();
			IJ.saveAs(imagemDifference, "PNG", "C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/Imagens Após Operações/"+imagemDifference.getTitle());
			
			//Copy Copiar
			ImagePlus imagemCopy = ImageCalculator.run(impRecebida1, impRecebida2, "Copy create");
			imagemCopy.setTitle(impRecebida1.getTitle()+" Copy "+impRecebida2.getTitle());
			imagemCopy.show();
			IJ.saveAs(imagemCopy, "PNG", "C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/Imagens Após Operações/"+imagemCopy.getTitle());
			
			//Transparent-zero Transparente Zero
			ImagePlus imagemTransparent = ImageCalculator.run(impRecebida1, impRecebida2, "Transparent-zero create");
			imagemTransparent.setTitle(impRecebida1.getTitle()+" Transparent-zero "+impRecebida2.getTitle());
			imagemTransparent.show();
			IJ.saveAs(imagemTransparent, "PNG", "C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/Imagens Após Operações/"+imagemTransparent.getTitle());
			

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
