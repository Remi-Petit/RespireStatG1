package vue;

import javax.swing.SwingUtilities;

public class RespireStat {
	public static void main(String[] args) {		
		//ConvertCSV.chargerEtablissements("ecoles-creches-idf.csv");
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				
				FenetrePrincipale fenetre = new FenetrePrincipale();
				fenetre.setVisible(true);
			}
		});
	}
}
