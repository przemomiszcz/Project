package Scheduler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.Vector;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.Pair;
import Parts.Package;
import Singletons.Graf;

public class Visual extends JFrame{

	private Graf gr;
	private VisualizationViewer<Integer, String> vv;
	private static final long serialVersionUID = 1L;
	private Vector<Package> vec;
	//private int id;
	
	public Visual (Vector<Package> vector, int id){
	    super("Samochod nr "+id);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    gr = Graf.getInstance();
	    Graph<Integer, String> g = getGraph();
	    vv=new VisualizationViewer<Integer,String>(new CircleLayout<Integer, String>(g),
			    new Dimension (500, 500));
	    showGraph(g);
	    this.vec = vector; 
	  }
	 
	  public Graph<Integer, String> getGraph() {
		int k = 0;
	    Graph<Integer, String> g = new DirectedSparseMultigraph<Integer, String>();
	    for(int i =0 ; i < gr.getPeaks().size(); i++) {
	    	g.addVertex(i);
	    	for(int j = 0; j < gr.getPeaks().elementAt(i).getEdges().size(); j++) {
	    		g.addEdge(" "+gr.getPeaks().elementAt(i).getEdges().elementAt(j).getTime() + " - "+k, 
	    				gr.getPeaks().elementAt(i).getNr(), gr.getPeaks().elementAt(i).getEdges().elementAt(j).getTarget());
	    		k++;
	    	}
	    }
	    return g;
	  }
	  
	  public void updateGraph(Vector<Integer> passed, int nr) {
		  final Graph<Integer, String> g = getGraph();
		  final int tmp = nr;
		  final Vector<Integer> tmpVector = passed;
		  
		  Transformer<String, Paint> edgePaint = new Transformer<String, Paint>() {
			    public Paint transform(String str) {
			    	Pair<Integer> x = g.getEndpoints(str);
			        if(x.getFirst() == tmp && x.getSecond() == tmpVector.elementAt(0) || x.getFirst() == tmpVector.elementAt(0) && x.getSecond() == tmp) {
			        	return Color.MAGENTA;
			        } 
			        for(int i =0; i<tmpVector.size()-1; i++) {
			        	if(x.getFirst() == tmpVector.elementAt(i) && x.getSecond() == tmpVector.elementAt(i+1) || x.getFirst() == tmpVector.elementAt(i+1) && x.getSecond() == tmpVector.elementAt(i)) {
			        		return Color.MAGENTA;
			        	}
			        }
			        return Color.GRAY;
			    }
			};
			
			Transformer<Integer,Paint> vertexColor = new Transformer<Integer,Paint>() {
	            public Paint transform(Integer i) {
	                if(i == vec.elementAt(0).getBase()) {
	                	return Color.GREEN;
	                }
	                return Color.RED;
	            }
	        };
	        Transformer<Integer,Shape> vertexSize = new Transformer<Integer,Shape>(){
	            public Shape transform(Integer i){
	                Ellipse2D circle = new Ellipse2D.Double(-15, -15, 30, 30);
	                	if(i == vec.elementAt(0).getBase()) {
	                		return AffineTransform.getScaleInstance(2, 2).createTransformedShape(circle);
	                	} else {
	                		return circle;
	                	}
	            }
	        };
	        vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);
	        vv.getRenderContext().setVertexShapeTransformer(vertexSize);
			vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
		  	showGraph(g);
	  }
	  
	  public void showGraph(Graph<Integer, String> g) {
				  vv =  new VisualizationViewer<Integer,String>(new CircleLayout<Integer, String>(g),
						  new Dimension (500, 500));
				  	vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Integer>());
				    vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<String>());
				    getContentPane().add(vv);
				 
				    pack();
				    setVisible(true);
	  }
}
