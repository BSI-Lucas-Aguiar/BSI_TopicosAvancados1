import java.awt.AWTEvent;
import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Ajustar_Histograma implements PlugIn, DialogListener {

	//Vari�veis declaradas fora do run para serem chamadas por qualquer outra fun��o
	private ImagePlus imagem;
	private ImageProcessor processador;
	private ImageProcessor processadorDuplicado;
	
	private ImagePlus imagemCinza;
	private ImageProcessor processadorCinza;
	
	//Vetores para as opera��es
	private int vetorExpansao[] = new int[256];
	//Possui 256 posi��es para fazer a f�rmula da equaliza��o
	private int vetorEqualizacao[] = new int[256];
	
	//Os valores ser�o preenchidos na chamada da expans�o
	int valorMin = 0;
	int valorMax = 255;
	int valorLow = 255;
	int valorHigh = 0;
	
	public void run(String arg) {
		//Pega a imagem RGB
		this.imagem = IJ.getImage();
		//transforma a imagem em RGB em escala de Cinza
		this.processador = this.imagem.getProcessor();
		GerarProcessadorCinza();
		
		//Cria��o da Interface
		GenericDialog interfaceGrafica = new GenericDialog("Ajuste de Histograma");
		interfaceGrafica.addDialogListener(this);
		String[] estrategia = {"Expans�o", "Equaliza��o"};
		interfaceGrafica.addRadioButtonGroup("Escolha uma das duas Estrat�gias", estrategia, 1, 2,"Expans�o");
		interfaceGrafica.showDialog();

		//As a��es ser�o executadas apenas pelo clique em OK ou Cancel
		if (interfaceGrafica.wasCanceled()) {
			IJ.showMessage("Histograma cancelado!");
			cancelarHistograma();
		} else {
			if (interfaceGrafica.wasOKed()) {
				this.imagemCinza = WindowManager.getImage("Imagem Cinza");
				String opcaoSelecionada = interfaceGrafica.getNextRadioButton();
				if (opcaoSelecionada.equals("Expans�o")) {
					aplicarExpansao();
				} else if (opcaoSelecionada.equals("Equaliza��o")) {
					aplicarEqualizacao();
				}
				//Abre o histograma da imagem cinza
				IJ.run("Histogram");
			}
		}
	}

	//Preenche a imagem cinza com a m�dia de pixels da imagem RGB, foi pensado para poder abrir imagens como clown ou leaf
	private void GerarProcessadorCinza() {
		int mediaRGB;
		int vetorRGB[] = new int[3];
		imagemCinza = IJ.createImage("Imagem Cinza", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		this.processadorCinza = imagemCinza.getProcessor();
		for (int x = 0; x < processador.getWidth(); x++) {
			for (int y = 0; y < processador.getHeight(); y++) {
				vetorRGB = processador.getPixel(x, y, vetorRGB);
				mediaRGB = (vetorRGB[0] + vetorRGB[1] + vetorRGB[2]) / 3;
				processadorCinza.putPixel(x, y, mediaRGB);
			}
		}
		//Exibe a imagem cinza c�pia da original
		imagemCinza.show();
	}
	
	//Preenche os valores min max com base na imagem cinza j� preenchida
	private void calcularMinMax() {
		int valorPixel[] = { 0 };
		for (int x = 0; x < processador.getWidth(); x++) {
			for (int y = 0; y < processador.getHeight(); y++) {
				valorPixel = processadorCinza.getPixel(x, y, valorPixel);

				if (valorPixel[0] < valorLow) {
					valorLow = valorPixel[0];
				}
				if (valorPixel[0] > valorHigh) {
					valorHigh = valorPixel[0];
				}
			}
		}
	}

	//Oculta a imagem cinza que � gerada no inicio do programa caso seja escolhida a op��o de cancelar
	private void cancelarHistograma() {
		imagemCinza.setProcessor(processadorCinza);
		imagem.updateAndDraw();
		imagemCinza.hide();
	}

	//Expans�o da Imagem
	private void aplicarExpansao() {
		calcularMinMax();
		imagemCinza.setProcessor(processadorCinza);
		processadorDuplicado = this.processadorCinza.duplicate();
		//Vetor A (F�rmula) Pixel atual
		int vetorA[] = {0};
		int pixelExpandido = 0;
		for (int x = 0; x < vetorExpansao.length; x++) {
			vetorExpansao[x] = 0;
		}

		//Processamento de todos os pixels conforme a f�rmula de expans�o
		for (int x = 0; x < processador.getWidth(); x++) {
			for (int y = 0; y < processador.getHeight(); y++) {
				vetorA = processadorCinza.getPixel(x, y, vetorA);
				//A f�rmula do Slide n�o funciona, a f�rmula a seguir foi fruto de pesquisa
				pixelExpandido = (valorMin + (vetorA[0] - valorLow) * (valorMax / (valorHigh - valorLow)));
				vetorExpansao[pixelExpandido]++;
				processadorDuplicado.putPixel(x, y, pixelExpandido);
			}
		}
		imagemCinza.setProcessor(processadorDuplicado);
		imagemCinza.updateAndDraw();
	}
	
	//Equaliza��o da Imagem
	private void aplicarEqualizacao() {
		//Define o processador cinza para a Imagem Cinza
		imagemCinza.setProcessor(processadorCinza);
		//Define um "backup" para processamento da imagem, visto que o processador cinza ser� editado
		processadorDuplicado = processadorCinza.duplicate();
		float totalElementosEqualizacao = 0;
		
		//Probabilidade de incidencia de um pixel
		float vetorProbabilidade[] = new float[256];
		
		
		//Valores de intensidade poss�veis
		int pixelEqualizado[] = {0};
		for (int x = 0; x < processador.getWidth(); x++) {
			for (int y = 0; y < processador.getHeight(); y++) {
				pixelEqualizado = processadorCinza.getPixel(x, y, pixelEqualizado);
				//Vetor declarado no in�cio
				vetorEqualizacao[pixelEqualizado[0]]++;
				totalElementosEqualizacao++;
			}
		}

		float probabilidadeCumulativa[] = new float[256];
		for (int x = 0; x < vetorEqualizacao.length; x++) {
			//Probabilidade de ocorrencia, dividida pelo total de elementos contados anteriormente
			//Valor da segunda coluna dividido pelo produto das dimens�es da imagem j� calculados
			vetorProbabilidade[x] = vetorEqualizacao[x]/totalElementosEqualizacao;
			if(x == 0) {
				probabilidadeCumulativa[x] = vetorProbabilidade[x];
			}
			if(x > 0){
				probabilidadeCumulativa[x] = probabilidadeCumulativa[x-1] + vetorProbabilidade[x];
			}
		}
		
		//Multiplica��o pensada para 20 tons como no slide
		for (int x = 0; x < processador.getWidth(); x++) {
			for (int y = 0; y < processador.getHeight(); y++) {
				pixelEqualizado = processadorCinza.getPixel(x, y, pixelEqualizado);
				int novaIntensidade = (int) (probabilidadeCumulativa[pixelEqualizado[0]] * 20);
				processadorDuplicado.putPixel(x, y, novaIntensidade);
			}
		}
		//Define o novo processador equalizado na imagem Cinza
		imagemCinza.setProcessor(processadorDuplicado);
		imagemCinza.updateAndDraw();
	}

	
	//Resolvi chamar no WasOked, para realizar a opera��o somente uma vez
	@Override
	public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
		if (interfaceGrafica.wasCanceled())	return false;
		return true;
	}
}
