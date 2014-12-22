package Scheduler;

import java.awt.Dimension;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.graph.*;
//import org.apache.commons.collections15.*;

import Singletons.Graf;

public class Visual extends JFrame{

	
	private static final long serialVersionUID = 1L;

	public Visual (){
	    super("Mój pierwszy graf");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    Graf gr = Graf.getInstance();
	    Graph g = getGraph(gr);
	    VisualizationViewer<Integer,String> vv = 
	     new VisualizationViewer<Integer,String>(new CircleLayout(g),
	     new Dimension (500, 500));
	    vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
	    vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
	    getContentPane().add(vv);
	 
	    pack();
	    setVisible(true);
	  }
	 
	  public Graph getGraph(Graf gr) {
		int k = 0;
	    Graph<Integer, String> g = new SparseMultigraph<Integer, String>();
	    for(int i =0 ; i < gr.getPeaks().size(); i++) {
	    	System.out.println("wchodzimy po raz i-ty" +i);
	    	g.addVertex(i);
	    	for(int j = 0; j < gr.getPeaks().elementAt(i).getEdges().size(); j++) {
	    		System.out.println("wchodzimy po raz j-ty" +j);
	    		g.addEdge("Edge " + k, gr.getPeaks().elementAt(i).getNr(), gr.getPeaks().elementAt(i).getEdges().elementAt(j).getTarget());
	    		System.out.println("wpisalem");
	    		k++;
	    	}
	    }
	    
	   /* g.addEdge("Edge-A", 1, 2);
	    g.addEdge("Edg", 1, 4);
	    g.addEdge("Edge-B", 2, 3);
	    g.addEdge("Edge-C", 3, 1);*/
	    
	    return g;
	  }
}
