package vue;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import controleur.ConvertCSV;
import controleur.StatEtab;
import model.Etablissement;

public class FenetrePrincipale extends JFrame{
	private static final long serialVersionUID = 1L;
	private String csvPath;
	private static String csvFileName = "ecoles-creches-idf.csv";
	public FenetrePrincipale(){
		super();
		build();
	}

	private void build(){
		File f;
		String message="";
        do {
        	csvPath = JOptionPane.showInputDialog( "Entrez le chemin vers le fichier : "+csvFileName );
			f = new File(csvPath+"/"+csvFileName);
			if(!f.exists())
				message = "Le fichier n'a pas √©t√© trouv√©.";
			else
				message = "Le fichier a √©t√© trouv√©.";
			JOptionPane.showMessageDialog(null, message+"\n"+csvPath+"/"+csvFileName);	
        }while(!f.exists());
		ConvertCSV.chargerEtablissements(csvPath+"/"+csvFileName);
		
		setTitle("RespireStat");
		setSize(960,720);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(buildContentPaneHomePage());
	}
	private JPanel buildContentPaneHomePage(){
		System.out.println("Build content pane");
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());

		//ONGLETS
		JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);
		onglets.setPreferredSize(new Dimension(900,680));

		//ONGLET 1
		JPanel onglet1 = new JPanel();
		JLabel label;
		label = new JLabel();
		label.setText("<html><body><p style='text-align:center'>Ouverture du fichier"+csvPath+"/"+csvFileName+"<br>Bienvenue sur l'application RespireStat<br /><br />Cliquez sur un des onglets pour acc√©der aux statistiques</p></body></html>");
		panel.add(label);
		onglet1.add(label);


		onglets.addTab("Accueil", onglet1);


		//ONGLET 2
		JPanel onglet2 = new JPanel();
		JLabel labelTableau=new JLabel();
		Etablissement etabNO2,etabPM10,etabPM25;
		JTable table;
		JScrollPane spane;
		TableauStat1 tab1;

		onglet2.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		for(int annee=2012; annee<=2017; annee++) {

			labelTableau.setText(" "+annee);

			etabNO2 = StatEtab.getPlusPolluantNO2(ConvertCSV.listeEtab, annee);
			etabPM10 = StatEtab.getPlusPolluantPM10(ConvertCSV.listeEtab, annee);			
			etabPM25 = StatEtab.getPlusPolluantPM25(ConvertCSV.listeEtab, annee);
			
			if(etabNO2!=null && etabPM10!=null && etabPM25!=null) {
				Etablissement[] etabs = new Etablissement[3];
				etabs[0] = etabNO2;
				etabs[1] = etabPM10;
				etabs[2] = etabPM25;tab1 = new TableauStat1(etabs, annee);

				table = new JTable(tab1);
				spane =new JScrollPane(table);

				table.setCellSelectionEnabled(false);

				table.setPreferredSize(new Dimension(500,80));
				table.setPreferredScrollableViewportSize(table.getPreferredSize());
				table.setFillsViewportHeight(true);

				//onglet2.add(labelTableau,c);
				onglet2.add(spane,c);
				c.gridy++;
			}
			
		}

		onglets.addTab("Les plus pollu√©s", onglet2);

		//ONGLET 3
		JPanel onglet3 = new JPanel();

		HashMap<String, Double> moyenneVillesNO2 = new HashMap<String, Double>();
		HashMap<String, Double> moyenneVillesPM10 = new HashMap<String, Double>();
		HashMap<String, Double> moyenneVillesPM25 = new HashMap<String, Double>();
		for(String ville : ConvertCSV.listeVilles) {
			moyenneVillesNO2.put(ville, StatEtab.getMoyennePolluantNO2Ville(ConvertCSV.listeEtab, ville, 2017));
			moyenneVillesPM10.put(ville, StatEtab.getMoyennePolluantPM10Ville(ConvertCSV.listeEtab, ville, 2017));
			moyenneVillesPM25.put(ville, StatEtab.getMoyennePolluantPM25Ville(ConvertCSV.listeEtab, ville, 2017));
		}

		TableauStat2 tab2 = new TableauStat2(moyenneVillesNO2, moyenneVillesPM10,moyenneVillesPM25);

		table = new JTable(tab2);
		spane = new JScrollPane(table);

		onglet3.add(spane);

		onglets.addTab("Moyenne par ville 2017", onglet3);

		//ONGLET 4
		//TODO Faire l'onglet 4 : il ressemble beaucoup √  l'onglet 3 !
		JPanel onglet4 = new JPanel();
				
		//TODO Cr√©er les 3 HashMap
		HashMap<String, Double> moyenneDepartementNO2 = new HashMap<String, Double>();
		HashMap<String, Double> moyenneDepartementPM10 = new HashMap<String, Double>();
		HashMap<String, Double> moyenneDepartementPM25 = new HashMap<String, Double>();
		//TODO Parcourir la liste des d√©partements pour mettre dans les 3 HashMap le d√©partement (cl√©) et la moyenne de chaque polluant (valeur)
		for(String departement : ConvertCSV.listeDepartements) {
			moyenneDepartementNO2.put(departement, StatEtab.getMoyennePolluantNO2Dpt(ConvertCSV.listeEtab, departement, 2017));
			moyenneDepartementPM10.put(departement, StatEtab.getMoyennePolluantPM10Dpt(ConvertCSV.listeEtab, departement, 2017));
			moyenneDepartementPM25.put(departement, StatEtab.getMoyennePolluantPM25Dpt(ConvertCSV.listeEtab, departement, 2017));
		}
		//TODO Cr√©er un objet TableauStat3 dans lequel vous met

		TableauStat3 tab3 = new TableauStat3(moyenneDepartementNO2, moyenneDepartementPM10, moyenneDepartementPM25);
				
		//TODO D√©commenter les lignes suivantes
		//tableau de l'onglet 3
		table = new JTable(tab3);
		//dÈfilement horizontal ou vertical de la fenÍtre
		spane = new JScrollPane(table);
		
		
		onglet4.add(spane);
		onglets.addTab("Moyenne par dÈpartement 2017", onglet4);
		
		//ONGLET 5
				//TODO Faire l'onglet 4 : il ressemble beaucoup √  l'onglet 3 !
				JPanel onglet5 = new JPanel();
						
				//TODO Cr√©er les 3 HashMap
				HashMap<String, Double> evolutionDepartementNO2 = new HashMap<String, Double>();
				HashMap<String, Double> evolutionDepartementPM10 = new HashMap<String, Double>();
				HashMap<String, Double> evolutionDepartementPM25 = new HashMap<String, Double>();
				//TODO Parcourir la liste des d√©partements pour mettre dans les 3 HashMap le dÈpartement (clÈ) et la moyenne de chaque polluant (valeur)
				for(String departement : ConvertCSV.listeDepartements) {
					evolutionDepartementNO2.put(departement, StatEtab.getMoyennePolluantNO2Dpt(ConvertCSV.listeEtab, departement, 2017));
					evolutionDepartementPM10.put(departement, StatEtab.getMoyennePolluantPM10Dpt(ConvertCSV.listeEtab, departement, 2017));
					evolutionDepartementPM25.put(departement, StatEtab.getMoyennePolluantPM25Dpt(ConvertCSV.listeEtab, departement, 2017));
				}
				//TODO Cr√©er un objet TableauStat3 dans lequel vous met
				
				TableauStat4 tab4 = new TableauStat4(evolutionDepartementNO2, evolutionDepartementPM10, evolutionDepartementPM25);
						
				//TODO D√©commenter les lignes suivantes
				//tableau de l'onglet 3
				table = new JTable(tab4);
				//dÈfilement horizontal ou vertical de la fenÍtre
				spane = new JScrollPane(table);
				
				onglet5.add(spane);
				onglets.addTab("…volution 2017", onglet5);
				
		panel.add(onglets);
		return panel;
	}
}