package graph;
import graph.WeightedGraph;
import maze.Juncture;
import maze.Maze;

/** 
 * <P>The MazeGraph is an extension of WeightedGraph.  
 * The constructor converts a Maze into a graph.</P>
 */
public class MazeGraph extends WeightedGraph<Juncture> {

	/* STUDENTS:  SEE THE PROJECT DESCRIPTION FOR A MUCH
	 * MORE DETAILED EXPLANATION ABOUT HOW TO WRITE
	 * THIS CONSTRUCTOR
	 */
	
	/** 
	 * <P>Construct the MazeGraph using the "maze" contained
	 * in the parameter to specify the vertices (Junctures)
	 * and weighted edges.</P>
	 * 
	 * <P>The Maze is a rectangular grid of "junctures", each
	 * defined by its X and Y coordinates, using the usual
	 * convention of (0, 0) being the upper left corner.</P>
	 * 
	 * <P>Each juncture in the maze should be added as a
	 * vertex to this graph.</P>
	 * 
	 * <P>For every pair of adjacent junctures (A and B) which
	 * are not blocked by a wall, two edges should be added:  
	 * One from A to B, and another from B to A.  The weight
	 * to be used for these edges is provided by the Maze. 
	 * (The Maze methods getMazeWidth and getMazeHeight can
	 * be used to determine the number of Junctures in the
	 * maze. The Maze methods called "isWallAbove", "isWallToRight",
	 * etc. can be used to detect whether or not there
	 * is a wall between any two adjacent junctures.  The 
	 * Maze methods called "getWeightAbove", "getWeightToRight",
	 * etc. should be used to obtain the weights.)</P>
	 * 
	 * @param maze to be used as the source of information for
	 * adding vertices and edges to this MazeGraph.
	 */
	public MazeGraph(Maze maze) {
		super();
		for (int toright = 0; toright < maze.getMazeWidth(); toright++) {
			for (int todown = 0; todown < maze.getMazeHeight(); todown++) {
				Juncture toadd = new Juncture(toright, todown);
				Juncture left = null;
				Juncture above = null;
				for (Juncture junct : this.map.keySet()) {
					if (junct.getX() == toright && (junct.getY() + 1) == todown) {
						above = junct;
					}
					if ((junct.getX() + 1) == toright && junct.getY() == todown) {
						left = junct;
					}
				}
				this.addVertex(toadd);
				if (!maze.isWallAbove(toadd)) {
					this.addEdge(toadd, above, maze.getWeightAbove(toadd));
					this.addEdge(above, toadd, maze.getWeightAbove(toadd));
				}
				if (!maze.isWallToLeft(toadd)) {
					this.addEdge(toadd, left, maze.getWeightToLeft(toadd));
					this.addEdge(left, toadd, maze.getWeightToLeft(toadd));
				}
			}
		}

	}
}
