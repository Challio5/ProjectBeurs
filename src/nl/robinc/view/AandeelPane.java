package nl.robinc.view;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import nl.robinc.model.Aandeel;
import nl.robinc.model.Gebruiker;
import nl.robinc.model.Vereniging;
import nl.robinc.model.converter.GebruikerConverter;
import nl.robinc.model.converter.VerenigingConverter;

public class AandeelPane extends TableView<Aandeel> {

	// Kolommen voor de aandelen
	private TableColumn<Aandeel, Integer> nummerKolom;
	private TableColumn<Aandeel, Gebruiker> gebruikerKolom;
	private TableColumn<Aandeel, Vereniging> verenigingKolom;
	private TableColumn<Aandeel, Integer> aantalKolom;

	public AandeelPane() {
		nummerKolom = new TableColumn<>("Nummer");
		nummerKolom.setCellValueFactory(new PropertyValueFactory<Aandeel, Integer>("PRIMARYKEY"));
		
		gebruikerKolom = new TableColumn<>("Gebruiker");
		gebruikerKolom.setCellValueFactory(new PropertyValueFactory<Aandeel, Gebruiker>("gebruiker"));
		gebruikerKolom.setCellFactory(TextFieldTableCell.forTableColumn(new GebruikerConverter()));
		
		verenigingKolom = new TableColumn<>("Vereniging");
		verenigingKolom.setCellValueFactory(new PropertyValueFactory<Aandeel, Vereniging>("vereniging"));
		verenigingKolom.setCellFactory(TextFieldTableCell.forTableColumn(new VerenigingConverter()));
		
		aantalKolom = new TableColumn<>("Aantal");
		aantalKolom.setCellValueFactory(new PropertyValueFactory<Aandeel, Integer>("aantal"));

		this.getColumns().add(nummerKolom);
		this.getColumns().add(gebruikerKolom);
		this.getColumns().add(verenigingKolom);
		this.getColumns().add(aantalKolom);
	}
}
