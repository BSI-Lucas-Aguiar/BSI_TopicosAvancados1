package Filtros_Lineares;

import java.awt.AWTEvent;

import ij.IJ;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;

public class Filtro_Linear implements PlugIn, DialogListener {

	public void run(String arg) {
		GenericDialog interfaceGrafica = new GenericDialog("Aplicação de Filtros Lineares");
		interfaceGrafica.addDialogListener(this);
		String[] selecaoImagens = {"Passa Baixas", "Passa Altas","Borda"};
		interfaceGrafica.addRadioButtonGroup("Selecione o tipo de Filtro:", selecaoImagens, 1, 3,  "Passa Baixas");
		interfaceGrafica.showDialog();
		
		if (interfaceGrafica.wasCanceled()) {
			
		}else{
			if(interfaceGrafica.wasOKed()) {
				
			}
		}
	}
	
	
	
	public boolean dialogItemChanged(GenericDialog gd, AWTEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
