import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;
import ij.gui.Roi;
import ij.plugin.PlugIn;
import ij.plugin.frame.RoiManager;

public class Imagens_a_partir_de_ROI implements PlugIn{

	public void run(String arg) {
		
		//Aberta automaticamente a imagem e feito todo o processamento
		ImagePlus imp = IJ.openImage("C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/Sprite/sprite.jpg");
		imp.show();
		
		//Segunda imagem copiada para pegar o roi corretamente
		ImagePlus imp2 = imp.duplicate();
		imp2.show();
		Prefs.blackBackground = false;
		IJ.run(imp2, "Convert to Mask", "");
		IJ.run(imp2, "Fill Holes", "");
		
		IJ.run("ROI Manager...", "");
		IJ.run(imp2, "Analyze Particles...", "add");
		
		RoiManager roiManager = RoiManager.getRoiManager();
		Roi[] rm = roiManager.getRoisAsArray();
		
		int contador = 0;
		
		//Percorre o vetor roi 
		for (int i = 0; i < rm.length; i++) {
			contador++;
			//Define o Roi atual do vetor roi da imagem duplicada na imagem original(colorida)
			imp.setRoi(rm[i].getBounds());
			//Recorta com base no roi gerado porém na imagem original para manter as cores
			ImagePlus imagemSprite = imp.crop();
			imagemSprite.setTitle("Sprite Recortado " + contador);
			IJ.saveAs(imagemSprite, "PNG", "C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/Sprite/"+imagemSprite.getTitle());
		}
		
		IJ.run("Close");
		imp.changes = false;
		imp.close();
		imp2.changes = false;
		imp2.close();
		IJ.showMessage("Imagem recortada e sprites separados salvos");
	}
}
