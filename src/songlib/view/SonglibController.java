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

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

public class SonglibController {

	@FXML Button add;
	@FXML Button edit;
	@FXML Button delete;
	@FXML TextField song;
	@FXML TextField artist;
	@FXML TextField album;
	@FXML TextField year;
	@FXML ListView<String> listView;
	
	private ObservableList<String> obsList;
	private ArrayList<String> list=new ArrayList<String>();
	
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
		 String item = (String) listView.getSelectionModel().getSelectedItem();
		 song.setText(item);
	 }
	 
	 private void showItemInputDialog(Stage mainStage) {                
		   String item = (String) listView.getSelectionModel().getSelectedItem();
		   int index = listView.getSelectionModel().getSelectedIndex();

		   TextInputDialog dialog = new TextInputDialog(item);
		   dialog.initOwner(mainStage); dialog.setTitle("List Item");
		   dialog.setHeaderText("Selected Item (Index: " + index + ")");
		   dialog.setContentText("Enter name: ");
		   TextInputDialog dialog2 = new TextInputDialog(item);
		   dialog.initOwner(mainStage); dialog2.setTitle("List Item");
		   dialog.setHeaderText("Selected Item (Index: " + index + ")");
		   dialog.setContentText("Enter name: ");
		   
		   Optional<String> result = dialog.showAndWait();
		   if (result.isPresent()) { obsList.set(index, result.get()); }
	}
	
	private void updateList(){
		obsList = FXCollections.observableArrayList(                               
                list);                
	      listView.setItems(obsList);  
	      // select the first item
	     listView.getSelectionModel().select(0);
	}
	 
	private void add(String item){
		if(item!=null){
			if(list.isEmpty())
				list.add(item);
			else{
				int counter=0;
			
				while(counter<list.size()&&Integer.parseInt(item)>Integer.parseInt(list.get(counter))){
					counter++;
				}
				list.add(counter, item);
			}
		}else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Entering Error");
			alert.setHeaderText("Missing Value");
			alert.setContentText("Please Enter a value before adding/editing");

			alert.showAndWait();
		}
		updateList();
	}
	 
	public void delete(int index){
		if(index<0){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Selection Error");
			alert.setHeaderText("No Selection in ListView");
			alert.setContentText("Please make a selection on the list before deleting");

			alert.showAndWait();
		}else{
			list.remove(index);
			updateList();
		}
	}
	 
	public void convert(ActionEvent e) {
		Button b = (Button)e.getSource();
		if (b == add) {
			add(song.getText());
		}else if (b == edit){
			add(song.getText());
			delete(listView.getSelectionModel().getSelectedIndex());
		}else{
			delete(listView.getSelectionModel().getSelectedIndex());
		}
	}
	
}