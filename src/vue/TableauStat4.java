package vue;

import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import controleur.ConvertCSV;

public class TableauStat4 extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	//TODO Créer les attributs
	private final String[] entetes = {"Departements", "NO2", "PM10", "PM25"};	
	private final HashMap<String, Double> evolutionDepartementNO2;
	private final HashMap<String, Double> evolutionDepartementPM10;
	private final HashMap<String, Double> evolutionDepartementPM25;
	
	//TODO Créer le constructeur
	public TableauStat4(HashMap<String, Double> evolutionDepartementNO2, HashMap<String, Double> evolutionDepartementPM10, HashMap<String, Double> evolutionDepartementPM25) {
		this.evolutionDepartementNO2 = evolutionDepartementNO2;
		this.evolutionDepartementPM10 = evolutionDepartementPM10;
		this.evolutionDepartementPM25 = evolutionDepartementPM25;
	}
	
	@Override
	public int getColumnCount() {
		// TODO Compléter getColumnCount
		return entetes.length;
	}
	@Override
	public String getColumnName(int columnIndex) {
		// TODO Compléter getColumnName
		//return "";
		return entetes[columnIndex];
	}

	@Override
	public int getRowCount() {
		// TODO Compléter getRowCount
		return ConvertCSV.listeDepartements.size();
	}
	

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Compléter getValueAt
		//return null;
		switch (columnIndex) {
		case 0:
			return ConvertCSV.listeDepartements.get(rowIndex);
		case 1:
			return evolutionDepartementNO2.get(ConvertCSV.listeDepartements.get(rowIndex));

		case 2:
			return evolutionDepartementPM10.get(ConvertCSV.listeDepartements.get(rowIndex));

		case 3:
			return evolutionDepartementPM25.get(ConvertCSV.listeDepartements.get(rowIndex));

		default:
			throw new IllegalArgumentException();
		}
	}
}