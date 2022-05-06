import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Tecnicas_Morfologicas implements PlugIn {

	ImagePlus imagemOriginal = IJ.openImage("C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/sendrak.jpg");
	ImageProcessor processadorOriginal = imagemOriginal.getProcessor();
	ImagePlus imagemEditada = IJ.createImage("Imagem Editada", "8-bit", imagemOriginal.getWidth(), imagemOriginal.getHeight(), 1);
	ImageProcessor processadorEditado = imagemEditada.getProcessor();
	
	int[] vetor = new int[9];
	
	public void run(String arg) {
		imagemOriginal.show();
		
		GenericDialog interfaceGrafica = new GenericDialog("Aplica��o de t�cnicas Morfol�gicas");
		String[] selecaoImagens = {"Dilata��o", "Eros�o", "Fechamento", "Abertura", "Borda"};
		interfaceGrafica.addRadioButtonGroup("Selecione o tipo de Filtro:", selecaoImagens, 1, 5,  "Dilata��o");
		interfaceGrafica.showDialog();
		
		if (interfaceGrafica.wasCanceled()) {
			IJ.showMessage("Aplica��o de t�cnica cancelada!");
		}else{
			if(interfaceGrafica.wasOKed()) {
				imagemEditada.show();
				String opcaoSelecionada = interfaceGrafica.getNextRadioButton();
				if(opcaoSelecionada == "Dilata��o") {
					percorreImagem(opcaoSelecionada);
				}else if(opcaoSelecionada == "Eros�o") {
					percorreImagem(opcaoSelecionada);
				}else if(opcaoSelecionada == "Fechamento") {
					percorreImagem(opcaoSelecionada);
				}else if(opcaoSelecionada == "Abertura") {
					percorreImagem(opcaoSelecionada);
				}else if(opcaoSelecionada == "Borda") {
					percorreImagem(opcaoSelecionada);
				}
			}
		}
	}
	
	public void verificaPontos(int x, int y) {
		vetor[0] = processadorOriginal.getPixel(x-1,y+1);
     	vetor[1] = processadorOriginal.getPixel(x+1,y+1);
     	vetor[2] = processadorOriginal.getPixel(x, y+1);
     	vetor[3] = processadorOriginal.getPixel(x-1, y-1);
     	vetor[4] = processadorOriginal.getPixel(x+1,y-1);
     	vetor[5] = processadorOriginal.getPixel(x-1, y);
     	vetor[6] = processadorOriginal.getPixel(x+1, y);
     	vetor[7] = processadorOriginal.getPixel(x, y-1);
     	vetor[8] = processadorOriginal.getPixel(x, y);
	}
	
	public void percorreImagem(String opcao) {
		if(opcao == "Dilata��o") {
			for (int x = 0; x< processadorOriginal.getWidth(); x++) {
	            for (int y = 0; y < processadorOriginal.getHeight(); y++) {
	            	realizaDilatacao(x, y);
	            }
			}
		}else if(opcao == "Eros�o") {
			for (int x = 0; x< processadorOriginal.getWidth(); x++) {
	            for (int y = 0; y < processadorOriginal.getHeight(); y++) {
	            	realizaErosao(x, y);
	            }
			}
		}else if(opcao == "Fechamento") {
			
		}else if(opcao == "Abertura") {
			
		}else if(opcao == "Borda") {
			
		}
	}
	

	
	public void realizaDilatacao(int x, int y) {
		verificaPontos(x, y);
    	if(vetor[8] == 0) {
    		processadorEditado.putPixel(x-1,y+1, 0);
    		processadorEditado.putPixel(x+1,y+1, 0);
    		processadorEditado.putPixel(x, y+1, 0);
    		processadorEditado.putPixel(x-1, y-1, 0);
    		processadorEditado.putPixel(x+1,y-1, 0);
    		processadorEditado.putPixel(x-1, y, 0);
    		processadorEditado.putPixel(x+1, y, 0);
    		processadorEditado.putPixel(x, y-1, 0);
    		processadorEditado.putPixel(x, y, 0);    			
    	}
	}
	
	
	public void realizaErosao(int x, int y) {
		verificaPontos(x, y);
		int element = 0;
    	if(vetor[8] == 0) {
    		for (int xImage = x - 1; xImage <= x + 1; xImage++) {
				for (int yImage = y - 1; yImage <= y + 1; yImage++) {
					if(processadorOriginal.getPixel(xImage, yImage) == 0) {
						element++;
					}
				}
			}
			if (element == 9) processadorEditado.putPixel(x, y, 0);
    	}
	}
	
	public void realizaFechamento() {
		
	}
	
	public void realizaAbertura() {
		
	}
	
	public void realizaBorda() {
		
	}
}
