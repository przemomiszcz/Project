package Loading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import Silngletons.Order;
import Parts.Package;

public class PackagesLoading implements Loading{
	private FileReader fr;
	private BufferedReader bfr;
	private Order order;
	
	public PackagesLoading() {
		order = Order.getInstance();
	}
	
	@Override
	public void load() {
		try {
			fr = new FileReader("Files/Packages.txt");
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
				order.addPackage(new Package(Integer.parseInt(line[0]),
						Integer.parseInt(line[1]),
						Integer.parseInt(line[2]),
						line[3],
						Integer.parseInt(line[4])));
						
						
						
			 
				
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
