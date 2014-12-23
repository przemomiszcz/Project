package Main;

import Loading.CitiesLoading;
import Loading.ConnectionsLoading;
import Loading.PackagesLoading;
import Singletons.Entry;
import Singletons.Graf;
import Singletons.Order;
import Scheduler.Scheduler;

public class Main {

	public static void main(String[] args) {
		
		int carsAmount = Integer.parseInt(args[0]);
		int capacity = Integer.parseInt(args[1]);
		
		Entry enter = Entry.getInstance(carsAmount, capacity);
		CitiesLoading cl = new CitiesLoading();
		ConnectionsLoading ccl = new ConnectionsLoading();
		PackagesLoading pl = new PackagesLoading();
		
		cl.load();
		ccl.load();
		pl.load();
		
		Graf graf =  Graf.getInstance();
		Order order = Order.getInstance();
				
		/*for(int i =0; i < 5; i++) {
			for(int j=0; j<=5; j++) {
				if(graph.getPeaks().elementAt(i).getConcretEdge(j) != null) {
					System.out.println("z miasta i= "+i+" edge ma nr= "+graph.getPeaks().elementAt(i).getConcretEdge(j));
				}
			}
		}*/
		Dijkstra dijkstra = new Dijkstra(graf.getPeaks(), order.getElem(0).getBase());
		dijkstra.start();
		Scheduler scheduler = new Scheduler(enter, dijkstra.getParents(), graf, order.getOrder());
		scheduler.start();
	}

}
