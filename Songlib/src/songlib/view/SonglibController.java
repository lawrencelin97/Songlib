package songlib.view;

import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import songlib.app.Song;
import songlib.app.SongList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

public class SonglibController {

	@FXML Button add;
	@FXML Button edit;
	@FXML Button delete;
	@FXML TextField title;
	@FXML TextField artist;
	@FXML TextField album;
	@FXML TextField year;
	@FXML ListView<String> listView;
	
	private ObservableList<String> obsList;
	private SongList songList= new SongList();
	
	 public void start(Stage mainStage) {                
	      // create an ObservableList 
	      // from an ArrayList      
		 
		 updateList();

	      // set listener for the items
	      listView
	        .getSelectionModel()
	        .selectedIndexProperty()
	        .addListener(
	           (obs, oldVal, newVal) -> 
	               showSongInfo());

	   }
	 
	 private void showSongInfo(){
		 int index = listView.getSelectionModel().getSelectedIndex();
		 System.out.println(index);
		 if(index>-1){
			 Song song = songList.getSong(index);
			 title.setText(song.title);
			 artist.setText(song.artist);
			 album.setText(song.album);
			 year.setText(song.year);
		 }
	 }
	
	private void updateList(){
		obsList = FXCollections.observableArrayList(                               
                songList.toArrayList());                
	     listView.setItems(obsList);  
	}
	 
	private void add(){
		int state = songList.addSong(title.getText(), artist.getText(), album.getText(), year.getText());
		Alert alert = new Alert(AlertType.ERROR);
		switch(state){
		case -2:
			alert.setTitle("Adding Error");
			alert.setHeaderText("Repeated Entry");
			alert.setContentText("The song title and artist name is already used by another song");

			alert.showAndWait();
			break;
		case -1:
			alert.setTitle("Adding Error");
			alert.setHeaderText("Missing Entry");
			alert.setContentText("Either the song title or artist is left blank");

			alert.showAndWait();
			break;
		default:
			break;
		}
		updateList();
	}
	 
	public void delete(int index){
		songList.deleteSong(index);
		updateList();
	}
	 
	public void convert(ActionEvent e) {
		Button b = (Button)e.getSource();
		if (b == add) {
			add();
		}else if(listView.getSelectionModel().getSelectedIndex()>-1){
			if (b == edit){
				add();
				delete(listView.getSelectionModel().getSelectedIndex());
			}else{
				delete(listView.getSelectionModel().getSelectedIndex());
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Selection Error");
			alert.setHeaderText("No Selection in ListView");
			alert.setContentText("Please make a selection on the list before deleting");

			alert.showAndWait();
		}
	}
	
}