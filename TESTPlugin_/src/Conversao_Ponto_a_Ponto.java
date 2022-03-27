import ij.IJ;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
 
public class Conversao_Ponto_a_Ponto  implements PlugIn {
	public void run(String arg) {
		GenericDialog interfaceGrafica = new GenericDialog("Conversão Ponto a Ponto");
		interfaceGrafica.addSlider("Mudar um valor dado um intervalo", 0, 255, 128, 1);
		interfaceGrafica.showDialog();
		
		
		
	}
}