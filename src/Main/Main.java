package Main;

import Loading.CitiesLoading;
import Loading.ConnectionsLoading;
import Loading.PackagesLoading;
import Silngletons.Graph;
import Silngletons.Order;


public class Main {

	public static void main(String[] args) {
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

		Dijkstra dijkstra = new Dijkstra(graph.getPeaks(), order.getElem().getBase());
		dijkstra.start();
		System.out.println("Skoñczona dupa");
	}

}
