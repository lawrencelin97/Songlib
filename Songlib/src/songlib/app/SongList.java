package songlib.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class SongList{
	
	ArrayList<Song> songDir = new ArrayList<Song>(0);
	
	public void loadSongs(){
		
		String currText;
		String currTitle;
		String currArt;
		String currAl;
		String currYe;
		
		try{
			File storedList = new File("storedList.txt");
			BufferedReader reader = new BufferedReader(new FileReader(storedList));
			
			currText = reader.readLine();
			if(currText.equals("Song Count = 0")){
				
			}else{
				
				while((currTitle = reader.readLine()) != null){
					currArt = reader.readLine();
					currAl = reader.readLine();
					currYe = reader.readLine();
					songDir.add(new Song(currTitle, currArt, currAl, currYe));
					currText = reader.readLine();
					
				}
				
				
			}
			
			
			
			reader.close();
			
		}catch(Exception e){
			
		}
		
		
	}
	public void writeSongs(){
		
		try{
			File storedList = new File("storedList.txt");
			storedList.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(storedList));
			
			writer.write("Song Count = " + songDir.size());
			writer.newLine();
			
			for(int i = 0; i<songDir.size(); i++){
				writer.write(songDir.get(i).title);
				writer.newLine();
				writer.write(songDir.get(i).artist);
				writer.newLine();
				writer.write(songDir.get(i).album);
				writer.newLine();
				writer.write(songDir.get(i).year);
				writer.newLine();
				writer.newLine();

			}
			writer.close();
			
			
		}catch(Exception e){
			System.out.println("Sorry, not finsihed deletion yet!!");//DEBUG
		}
		
		
		
	}
	
	
	//searches list for song. If present already, error. Otherwise, adds to list
	//if year or album are empty, feed in empty string ("")
	public int addSong(String title, String artist, String album, String year){
		//No title or artist
		if(title.equals("")||artist.equals(""))
			return -1;
		//repeated song
		if(searchList(title, artist) != -1){
			return -2;
		}
		Song newEntry = new Song(title, artist);
		if(!album.equals(""))
			newEntry.setAlbum(album);
		if(!year.equals(""))
			newEntry.setYear(year);
		
		for(int i = 0; i< songDir.size(); i++){
			if(title.compareToIgnoreCase(songDir.get(i).title) < 0){
				songDir.add(i, newEntry);
				writeSongs();
				return 0;
			//case of same title, different artist
			}else if(title.compareToIgnoreCase(songDir.get(i).title) == 0 &&
					artist.compareToIgnoreCase(songDir.get(i).artist) < 0){
				songDir.add(i, newEntry);
				writeSongs();
				return 0;
			}
		}
		//final case, it belongs at end of list
		songDir.add(newEntry);
		writeSongs();
		return 0;
	}
	
	//This method takes old song details and removes song from list. Then adds new song
	//(or the old one, if the new one was unacceptable/already present)
	public void editSong(String oldTitle, String oldArtist, String oldAlbum, String oldYear,
			String newTitle, String newArtist, String newAlbum, String newYear){
		
		deleteSong(oldTitle, oldArtist);//deletes old song
		
		if(searchList(newTitle, newArtist) != -1){ //if invalid, adds old song back in
			addSong(oldTitle, oldArtist, oldAlbum, oldYear);
			System.out.println("Invalid edit: song exists already!!!"); //TESTING
			
		}else{//else, adds new song to list, updates text file
			addSong(newTitle, newArtist, newAlbum, newYear);
			writeSongs();
		}
	}
	
	
	//searches list for song, deletes if found, error otherwise
	public void deleteSong(String title, String artist){
		
		int index = searchList(title, artist);
		if(index == -1){//requested delete is invalid
			System.out.println("Invalid deletion: song not present!!!");//TESTING
			
		}else{//else, remove from list, update
			songDir.remove(index);
			writeSongs();
		}		
	}
	
	//delets using index
	public void deleteSong(int index){
		
		if(index == -1){//requested delete is invalid
			System.out.println("Invalid deletion: song not present!!!");//TESTING
			
		}else{//else, remove from list, update
			songDir.remove(index);
			writeSongs();
		}		
	}
	
	
	//searches list for a song. Returns index if found, -1 otherwise
	public int searchList(String title, String artist){
		
		for(int i = 0; i < songDir.size(); i++){
			if((title.equals(songDir.get(i).title)) && (artist.equals(songDir.get(i).artist))){
				return i;
			}
		}
		
		return -1;
	}
	public void printList(){
	
		for(int i = 0; i <songDir.size(); i++){
		
			System.out.println(songDir.get(i).toString()+ "\n");
	
		}
	}
	
	//outputs a Arraylist of the song name and artist
	public ArrayList<String> toArrayList(){
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i <songDir.size(); i++){
			
			list.add(songDir.get(i).toLabel());
	
		}
		return list;
	}
	
	public Song getSong(int index){
		return songDir.get(index);
	}
}