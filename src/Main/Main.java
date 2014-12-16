package Main;

import Loading.CitiesLoading;
import Loading.ConnectionsLoading;
import Loading.PackagesLoading;
import Silngletons.Entry;
import Silngletons.Graph;
import Silngletons.Order;
import Scheduler.Scheduler;
import Scheduler.Car;

public class Main {

	public static void main(String[] args) {
		
		//int carsAmount = Integer.parseInt(args[0]);
		//int capacity = Integer.parseInt(args[1]);
		
		Entry enter = Entry.getInstance(1, 1);
		CitiesLoading cl = new CitiesLoading();
		ConnectionsLoading ccl = new ConnectionsLoading();
		PackagesLoading pl = new PackagesLoading();
		
		cl.load();
		ccl.load();
		pl.load();
		
		Graph graph =  Graph.getInstance();
		Order order = Order.getInstance();
		//System.out.println("cele z 3 miasta: "+graph.countConcretTargets(1));
		
		/*for(int i =0; i < 5; i++) {
			for(int j=0; j<=5; j++) {
				if(graph.getPeaks().elementAt(i).getConcretEdge(j) != null) {
					System.out.println("z miasta i= "+i+" edge ma nr= "+graph.getPeaks().elementAt(i).getConcretEdge(j));
				}
			}
		}*/

		Dijkstra dijkstra = new Dijkstra(graph.getPeaks(), order.getElem(0).getBase());
		//System.out.println(graph.getPeaks());
		dijkstra.start();
		Scheduler scheduler = new Scheduler(1, 3, dijkstra.getParents(), graph, order.getOrder());
		scheduler.start();
	}

}
