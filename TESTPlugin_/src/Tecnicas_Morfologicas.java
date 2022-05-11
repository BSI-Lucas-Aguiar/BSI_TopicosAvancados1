import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Tecnicas_Morfologicas implements PlugIn {

	ImagePlus imagemOriginal = IJ.getImage();
	ImageProcessor processadorOriginal = imagemOriginal.getProcessor();
	ImagePlus imagemEditada = IJ.createImage("Imagem Editada", "8-bit", imagemOriginal.getWidth(), imagemOriginal.getHeight(), 1);
	ImageProcessor processadorEditado = imagemEditada.getProcessor();
	
	int[] vetor = new int[9];
	
	public void run(String arg) {
		imagemOriginal.show();
		
		GenericDialog interfaceGrafica = new GenericDialog("Aplicação de técnicas Morfológicas");
		String[] selecaoImagens = {"Dilatação", "Erosão", "Fechamento", "Abertura", "Borda"};
		interfaceGrafica.addRadioButtonGroup("Selecione o tipo de Filtro:", selecaoImagens, 1, 5,  "Dilatação");
		interfaceGrafica.showDialog();
		
		if (interfaceGrafica.wasCanceled()) {
			IJ.showMessage("Aplicação de técnica cancelada!");
		}else{
			if(interfaceGrafica.wasOKed()) {
				String opcaoSelecionada = interfaceGrafica.getNextRadioButton();
				if(opcaoSelecionada == "Dilatação") {
					percorreImagem(opcaoSelecionada);
				}else if(opcaoSelecionada == "Erosão") {
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
	
	public void percorreImagem(String opcao) {
		
		//Dilatação
		if(opcao == "Dilatação") {
			for (int x = 0; x < processadorOriginal.getWidth(); x++) {
	            for (int y = 0; y < processadorOriginal.getHeight(); y++) {
	            	realizaDilatacao(x, y, processadorEditado, "editado");
	            }
			}
			imagemOriginal.setProcessor(processadorEditado);
			imagemOriginal.updateAndDraw();
			
		//Erosão
		}else if(opcao == "Erosão") {
			for (int x = 0; x< processadorOriginal.getWidth(); x++) {
	            for (int y = 0; y < processadorOriginal.getHeight(); y++) {
	            	realizaErosao(x, y, processadorEditado, "editado");
	            }
			}
			imagemOriginal.setProcessor(processadorEditado);
			imagemOriginal.updateAndDraw();
			
		//Fechamento
		}else if(opcao == "Fechamento") {
			for (int x = 0; x < processadorOriginal.getWidth(); x++) {
	            for (int y = 0; y < processadorOriginal.getHeight(); y++) {
	            	realizaDilatacao(x, y, processadorEditado, "editado");
	            }
			}
			imagemOriginal.setProcessor(processadorEditado);
			imagemOriginal.updateAndDraw();
			processadorOriginal = processadorEditado.duplicate();
			
			for (int x = 0; x < processadorOriginal.getWidth(); x++) {
	            for (int y = 0; y < processadorOriginal.getHeight(); y++) {
	            	realizaErosao(x, y, processadorEditado, "editado");
	            }
			}
			imagemOriginal.setProcessor(processadorEditado);
			imagemOriginal.updateAndDraw();
			
		//Abertura
		}else if(opcao == "Abertura") {
			for (int x = 0; x < processadorOriginal.getWidth(); x++) {
	            for (int y = 0; y < processadorOriginal.getHeight(); y++) {
	            	realizaErosao(x, y, processadorEditado, "editado");
	            }
			}
			imagemOriginal.setProcessor(processadorEditado);
			imagemOriginal.updateAndDraw();
			processadorOriginal = processadorEditado.duplicate();
			
			for (int x = 0; x < processadorOriginal.getWidth(); x++) {
	            for (int y = 0; y < processadorOriginal.getHeight(); y++) {
	            	realizaDilatacao(x, y, processadorEditado, "editado");
	            }
			}
			imagemOriginal.setProcessor(processadorEditado);
			imagemOriginal.updateAndDraw();
			
		//Borda
		}else if(opcao == "Borda") {
			for (int x = 0; x < processadorOriginal.getWidth(); x++) {
	            for (int y = 0; y < processadorOriginal.getHeight(); y++) {
	            	realizaErosao(x, y, processadorEditado, "editado");
	            }
			}
	        realizaBorda();
			imagemOriginal.updateAndDraw();
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
	
	public void verificaPontosEditado(int x, int y) {
		vetor[0] = processadorEditado.getPixel(x-1,y+1);
     	vetor[1] = processadorEditado.getPixel(x+1,y+1);
     	vetor[2] = processadorEditado.getPixel(x, y+1);
     	vetor[3] = processadorEditado.getPixel(x-1, y-1);
     	vetor[4] = processadorEditado.getPixel(x+1,y-1);
     	vetor[5] = processadorEditado.getPixel(x-1, y);
     	vetor[6] = processadorEditado.getPixel(x+1, y);
     	vetor[7] = processadorEditado.getPixel(x, y-1);
     	vetor[8] = processadorEditado.getPixel(x, y);
	}
	
	public void realizaDilatacao(int x, int y, ImageProcessor processadorQueVaiSerEditado, String qual) {
		verificaPontos(x, y);
    	if(vetor[8] == 0) {
    		processadorQueVaiSerEditado.putPixel(x-1,y+1, 0);
    		processadorQueVaiSerEditado.putPixel(x+1,y+1, 0);
    		processadorQueVaiSerEditado.putPixel(x, y+1, 0);
    		processadorQueVaiSerEditado.putPixel(x-1, y-1, 0);
    		processadorQueVaiSerEditado.putPixel(x+1,y-1, 0);
    		processadorQueVaiSerEditado.putPixel(x-1, y, 0);
    		processadorQueVaiSerEditado.putPixel(x+1, y, 0);
    		processadorQueVaiSerEditado.putPixel(x, y-1, 0);
    		processadorQueVaiSerEditado.putPixel(x, y, 0);    			
    	}
    	
    	if(qual == "original") {
			processadorOriginal = processadorQueVaiSerEditado;
		}else if(qual == "editado") {
			processadorEditado = processadorQueVaiSerEditado;
		}
	}
	
	public void realizaErosao(int x, int y, ImageProcessor processadorQueVaiSerEditado, String qual) {
		verificaPontos(x, y);
		boolean areaDeInteresse = true;
		
		//Caso a área de interesse não seja preta não irá realizar operação
		for(int i = 0; i < 9; i++) {
			if(vetor[i] != 0) {
				areaDeInteresse = false;
			}
		}
		//Caso permaneça verdadeira é preenchido na imagem cópia
		if(areaDeInteresse) {
			processadorQueVaiSerEditado.putPixel(x, y, 0);
		}
		if(qual == "original") {
			processadorOriginal = processadorQueVaiSerEditado;
		}else if(qual == "editado") {
			processadorEditado = processadorQueVaiSerEditado;
		}
	}
	
	public void realizaBorda() {
		int imgCopia;
		
		for(int x = 0; x < processadorOriginal.getWidth(); x++) {
			for(int y = 0; y < processadorOriginal.getHeight(); y++) {
				imgCopia = processadorEditado.getPixel(x, y);
				
				if(imgCopia == 0) {
					processadorOriginal.putPixel(x, y, 255);
				}
			}
		}
	}
}
