package Scheduler;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.decorators.GradientEdgePaintTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.graph.*;
import Singletons.Graf;

public class Visual extends JFrame{

	private VisualizationViewer vv;
	private static final long serialVersionUID = 1L;
	private GradientEdgePaintTransformer<Integer, String> grad;

	public Visual (){
	    super("Samochod nr ");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Graf gr = Graf.getInstance();
	    Graph<Integer, String> g = getGraph(gr);
	    vv=new VisualizationViewer<Integer,String>(new CircleLayout<Integer, String>(g),
			    new Dimension (500, 500));
	    grad = new GradientEdgePaintTransformer<Integer, String>(Color.BLUE, Color.BLUE, vv);
	    showGraph(g);
	  }
	 
	  public Graph<Integer, String> getGraph(Graf gr) {
		int k = 0;
	    Graph<Integer, String> g = new SparseMultigraph<Integer, String>();
	    for(int i =0 ; i < gr.getPeaks().size(); i++) {
	    	g.addVertex(i);
	    	for(int j = 0; j < gr.getPeaks().elementAt(i).getEdges().size(); j++) {
	    		g.addEdge("Edge "+gr.getPeaks().elementAt(i).getEdges().elementAt(j).getTime() + " - "+k, 
	    				gr.getPeaks().elementAt(i).getNr(), gr.getPeaks().elementAt(i).getEdges().elementAt(j).getTarget());
	    		k++;
	    	}
	    }
	    
	    return g;
	  }
	  
	  public void updateGraph(Vector<Integer> passed, int nr) {
		  Graph<Integer, String> g = new SparseMultigraph<Integer, String>();
		  	showGraph(g);
	  }
	  
	  @SuppressWarnings("unchecked")
	public void showGraph(Graph<Integer, String> g) {
		  vv = 
				    new VisualizationViewer<Integer,String>(new CircleLayout<Integer, String>(g),
				    new Dimension (500, 500));
				    vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Integer>());
				    vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<String>());
				    getContentPane().add(vv);
				 
				    pack();
				    setVisible(true);
	  }
}
