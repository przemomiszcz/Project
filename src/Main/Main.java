package Main;

import Loading.CitiesLoading;
import Loading.ConnectionsLoading;
import Loading.PackagesLoading;
import Silngletons.Graph;
import Silngletons.Order;
import Scheduler.Scheduler;
import Scheduler.Car;

public class Main {

	public static void main(String[] args) {
		
		//int carsAmount = Integer.parseInt(args[0]);
		//int capacity = Integer.parseInt(args[1]);
		
		System.out.println("dupa");
		CitiesLoading cl = new CitiesLoading();
		ConnectionsLoading ccl = new ConnectionsLoading();
		PackagesLoading pl = new PackagesLoading();
		
		cl.load();
		ccl.load();
		pl.load();
		
		Graph graph =  Graph.getInstance();
		Order order = Order.getInstance();
		
		//System.out.println(graph);
		//System.out.println(order);

		Dijkstra dijkstra = new Dijkstra(graph.getPeaks(), order.getElem(0).getBase());
		dijkstra.start();
		Scheduler scheduler = new Scheduler(5, 1, dijkstra.getParents(), graph, order.getOrder());
		scheduler.start();
		System.out.println("Skoñczona dupa");
	}

}
