package Loading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Parts.Peak;
import Silngletons.*;


public class CitiesLoading implements Loading{

	private FileReader fr;
	private BufferedReader bfr;
	private Graph graph;
	
	public CitiesLoading() {
		graph = Graph.getInstance();
	}
	
	@Override
	public void load() {
		try {
			fr = new FileReader("Files/Cities.txt");
			bfr = new BufferedReader(fr);
		} catch(IOException e) {
			System.out.println("Bledny plik you pice'a'shit!");
			e.printStackTrace();
		}
		
		String[] line = new String[10];
		String s;
		try {
			bfr.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			while((s=bfr.readLine())!=null) {
				line = s.split("  ");
				graph.addPeak(Integer.parseInt(line[0]), new Peak(Integer.parseInt(line[0]), line[1]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
