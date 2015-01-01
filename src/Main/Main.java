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
		//System.out.println("1");
		int carsAmount = Integer.parseInt(args[0]);
		int capacity = Integer.parseInt(args[1]);
		
		Entry enter = Entry.getInstance(carsAmount, capacity);
		CitiesLoading cl = new CitiesLoading();
		ConnectionsLoading ccl = new ConnectionsLoading();
		PackagesLoading pl = new PackagesLoading();
		//System.out.println("2");
		cl.load();
		ccl.load();
		pl.load();
		
		Graf graf =  Graf.getInstance();
		Order order = Order.getInstance();
		//System.out.println(graf);
		
		Dijkstra dijkstra = new Dijkstra(graf.getPeaks(), order.getElem(0).getBase());
		dijkstra.start();
		//System.out.println("4");
		Scheduler scheduler = new Scheduler(enter, dijkstra.getParents(), graf, order.getOrder());
		//System.out.println("5");
		scheduler.start();
	}

}
