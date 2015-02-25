package nl.robinc.view;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import nl.robinc.model.Aanbieding;
import nl.robinc.model.Gebruiker;
import nl.robinc.model.Vereniging;
import nl.robinc.model.converter.GebruikerConverter;
import nl.robinc.model.converter.VerenigingConverter;

public class AanbiedingPane extends TableView<Aanbieding> {
	
	// Kolommen voor de aandelen
	private TableColumn<Aanbieding, Integer> nummerKolom;
	private TableColumn<Aanbieding, Gebruiker> gebruikerKolom;
	private TableColumn<Aanbieding, Vereniging> verenigingKolom;
	private TableColumn<Aanbieding, Integer> aantalKolom;
	private TableColumn<Aanbieding, Double> prijsKolom;
	
	public AanbiedingPane() {
		nummerKolom = new TableColumn<>("Nummer");
		nummerKolom.setCellValueFactory(new PropertyValueFactory<Aanbieding, Integer>("PRIMARYKEY"));
		
		gebruikerKolom = new TableColumn<>("Gebruiker");
		gebruikerKolom.setCellValueFactory(new PropertyValueFactory<Aanbieding, Gebruiker>("gebruiker"));
		gebruikerKolom.setCellFactory(TextFieldTableCell.forTableColumn(new GebruikerConverter()));
		
		verenigingKolom = new TableColumn<>("Vereniging");
		verenigingKolom.setCellValueFactory(new PropertyValueFactory<Aanbieding, Vereniging>("vereniging"));
		verenigingKolom.setCellFactory(TextFieldTableCell.forTableColumn(new VerenigingConverter()));
		
		aantalKolom = new TableColumn<>("Aantal");
		aantalKolom.setCellValueFactory(new PropertyValueFactory<Aanbieding, Integer>("aantal"));

		prijsKolom = new TableColumn<>("Prijs");
		prijsKolom.setCellValueFactory(new PropertyValueFactory<Aanbieding, Double>("prijs"));
		
		this.getColumns().add(nummerKolom);
		this.getColumns().add(gebruikerKolom);
		this.getColumns().add(verenigingKolom);
		this.getColumns().add(aantalKolom);
		this.getColumns().add(prijsKolom);
	}
}
