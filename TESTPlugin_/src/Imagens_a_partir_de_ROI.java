import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;

public class Imagens_a_partir_de_ROI implements PlugIn{

	public void run(String arg) {
		
		ImagePlus imp = IJ.openImage("C:/Users/lukas/Desktop/BSI_TopicosAvancados1/TESTPlugin_/Imagens/sprite.jpg");
		
		IJ.run("ROI Manager...", "");
		Object rm;
		imp.setRoi(38,28,70,114);
		rm.addRoi(roi);
		imp.setRoi(121,30,66,109);
		rm.addRoi(roi);
		imp.setRoi(194,30,72,106);
		rm.addRoi(roi);
		imp.setRoi(297,33,82,106);
		rm.addRoi(roi);
		imp.setRoi(385,32,99,106);
		rm.addRoi(roi);
		imp.setRoi(489,32,99,106);
		rm.addRoi(roi);
		imp.setRoi(33,156,99,106);
		rm.addRoi(roi);
		imp.setRoi(125,156,99,106);
		rm.addRoi(roi);
		imp.setRoi(218,153,99,106);
		rm.addRoi(roi);
		imp.setRoi(308,153,99,106);
		rm.addRoi(roi);
		imp.setRoi(399,156,99,106);
		rm.addRoi(roi);
		imp.setRoi(494,157,99,106);
		rm.addRoi(roi);
		imp.setRoi(21,304,99,106);
		rm.addRoi(roi);
		imp.setRoi(122,295,99,106);
		rm.addRoi(roi);
		imp.setRoi(222,279,99,106);
		rm.addRoi(roi);
		imp.setRoi(315,277,99,106);
		rm.addRoi(roi);
		imp.setRoi(403,292,99,106);
		rm.addRoi(roi);
		imp.setRoi(501,303,99,106);
		rm.addRoi(roi);
		imp.setRoi(26,429,99,106);
		rm.addRoi(roi);
		imp.setRoi(109,430,99,106);
		rm.addRoi(roi);
		imp.setRoi(195,429,99,106);
		rm.addRoi(roi);
		imp.setRoi(273,431,99,106);
		rm.addRoi(roi);
		imp.setRoi(365,427,80,106);
		rm.addRoi(roi);
		imp.setRoi(438,423,75,106);
		rm.addRoi(roi);
		imp.setRoi(510,422,75,106);
		rm.addRoi(roi);
		
	}
	
	

}
