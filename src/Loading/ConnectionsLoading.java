package Loading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Singletons.*;
import Parts.Edge;

public class ConnectionsLoading implements Loading{ //wczytywanie polaczen jako krawdzi grafu

	private FileReader fr;
	private BufferedReader bfr;
	private Graf graf;
	
	public ConnectionsLoading() {
		graf = Graf.getInstance();
	}
	
	@Override
	public void load() {
		try {
			fr = new FileReader("Files/Connections.txt");
			bfr = new BufferedReader(fr);
		} catch(IOException e) {
			System.out.println("Bledny plik you pice'a'shit!");
			e.printStackTrace();
		}
		
		String[] line = new String[10];
		String s;
		/*try {
			bfr.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		try {
			while((s=bfr.readLine())!=null) {
				line = s.split("  ");
				graf.addEdge(Integer.parseInt(line[0]), new Edge(Integer.parseInt(line[1]), Integer.parseInt(line[2])));
				//graf.addEdge(Integer.parseInt(line[1]), new Edge(Integer.parseInt(line[0]), Integer.parseInt(line[2])));
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
