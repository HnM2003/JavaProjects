package graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;


/**
 * <P>This class represents a general "directed graph", which could 
 * be used for any purpose.  The graph is viewed as a collection 
 * of vertices, which are sometimes connected by weighted, directed
 * edges.</P> 
 * 
 * <P>This graph will never store duplicate vertices.</P>
 * 
 * <P>The weights will always be non-negative integers.</P>
 * 
 * <P>The WeightedGraph will be capable of performing three algorithms:
 * Depth-First-Search, Breadth-First-Search, and Djikatra's.</P>
 * 
 * <P>The Weighted Graph will maintain a collection of 
 * "GraphAlgorithmObservers", which will be notified during the
 * performance of the graph algorithms to update the observers
 * on how the algorithms are progressing.</P>
 */
public class WeightedGraph<V> {

	/* STUDENTS:  You decide what data structure(s) to use to
	 * implement this class.
	 * 
	 * You may use any data structures you like, and any Java 
	 * collections that we learned about this semester.  Remember 
	 * that you are implementing a weighted, directed graph.
	 */
	
	/*
	 * A map from a given vertex to a map for the connected vertex's and the weight
	 * between each pair of vertices.
	 */
	Map<V, Map<V, Integer>> map;
	
	/* Collection of observers.  Be sure to initialize this list
	 * in the constructor.  The method "addObserver" will be
	 * called to populate this collection.  Your graph algorithms 
	 * (DFS, BFS, and Dijkstra) will notify these observers to let 
	 * them know how the algorithms are progressing. 
	 */
	private Collection<GraphAlgorithmObserver<V>> observerList;
	

	/** Initialize the data structures to "empty", including
	 * the collection of GraphAlgorithmObservers (observerList).
	 */
	public WeightedGraph() {
		observerList = new HashSet<GraphAlgorithmObserver<V>>();
		map = new HashMap<V, Map<V,Integer>>();
	}

	/** Add a GraphAlgorithmObserver to the collection maintained
	 * by this graph (observerList).
	 * 
	 * @param observer
	 */
	public void addObserver(GraphAlgorithmObserver<V> observer) {
		observerList.add(observer);
	}

	/** Add a vertex to the graph.  If the vertex is already in the
	 * graph, throw an IllegalArgumentException.
	 * 
	 * @param vertex vertex to be added to the graph
	 * @throws IllegalArgumentException if the vertex is already in
	 * the graph
	 */
	public void addVertex(V vertex) {
		if(map.containsKey(vertex)) {
			throw new IllegalArgumentException();
		}
		map.put(vertex, new HashMap<V, Integer>());
	}
	
	/** Searches for a given vertex.
	 * 
	 * @param vertex the vertex we are looking for
	 * @return true if the vertex is in the graph, false otherwise.
	 */
	public boolean containsVertex(V vertex) {
		if(map.containsKey(vertex)) {
			return true;
		}
		return false;
	}

	/** 
	 * <P>Add an edge from one vertex of the graph to another, with
	 * the weight specified.</P>
	 * 
	 * <P>The two vertices must already be present in the graph.</P>
	 * 
	 * <P>This method throws an IllegalArgumentExeption in three
	 * cases:</P>
	 * <P>1. The "from" vertex is not already in the graph.</P>
	 * <P>2. The "to" vertex is not already in the graph.</P>
	 * <P>3. The weight is less than 0.</P>
	 * 
	 * @param from the vertex the edge leads from
	 * @param to the vertex the edge leads to
	 * @param weight the (non-negative) weight of this edge
	 * @throws IllegalArgumentException when either vertex
	 * is not in the graph, or the weight is negative.
	 */
	public void addEdge(V from, V to, Integer weight) {
		if(!map.containsKey(from) || !map.containsKey(to) || weight < 0){
			throw new IllegalArgumentException();
		}
		map.get(from).put(to, weight);
	}

	/** 
	 * <P>Returns weight of the edge connecting one vertex
	 * to another.  Returns null if the edge does not
	 * exist.</P>
	 * 
	 * <P>Throws an IllegalArgumentException if either
	 * of the vertices specified are not in the graph.</P>
	 * 
	 * @param from vertex where edge begins
	 * @param to vertex where edge terminates
	 * @return weight of the edge, or null if there is
	 * no edge connecting these vertices
	 * @throws IllegalArgumentException if either of
	 * the vertices specified are not in the graph.
	 */
	public Integer getWeight(V from, V to) {
		if(!map.containsKey(from) || !map.containsKey(to)) {
			throw new IllegalArgumentException();
		}
		if(!map.get(from).containsKey(to)) {
			return null;
		}
		return map.get(from).get(to);
	}

	/** 
	 * <P>This method will perform a Breadth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the
	 * collection of Observers, calling notifyBFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>
	 * 
	 * <P>After the "end" vertex has been visited, this method will
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without processing further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	public void DoBFS(V start, V end) {
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyBFSHasBegun();
		}
		HashSet<V> visited = new HashSet<V>();
		LinkedList<V> list = new LinkedList<V>();
		list.add(start);
		while (!list.isEmpty()) {
			V idx = list.remove();
			if(!visited.contains(idx)) {
			for (GraphAlgorithmObserver<V> observer : observerList) {
				observer.notifyVisit(idx);
			}
			if(idx.equals(end)) {
				for (GraphAlgorithmObserver<V> observer : observerList) {
					observer.notifySearchIsOver();
				}
				break;
			}
				visited.add(idx);
				for (V value : map.get(idx).keySet()) {
					list.add(value);
				}
			}
		}
	}

	/**
	 * <P>
	 * This method will perform a Depth-First-Search on the graph. The search will
	 * begin at the "start" vertex and conclude once the "end" vertex has been
	 * reached.
	 * </P>
	 * 
	 * <P>
	 * Before the search begins, this method will go through the collection of
	 * Observers, calling notifyDFSHasBegun on each one.
	 * </P>
	 * 
	 * <P>
	 * Just after a particular vertex is visited, this method will go through the
	 * collection of observers calling notifyVisit on each one (passing in the
	 * vertex being visited as the argument.)
	 * </P>
	 * 
	 * <P>
	 * After the "end" vertex has been visited, this method will go through the
	 * collection of observers calling notifySearchIsOver on each one, after which
	 * the method should terminate immediately, without visiting further vertices.
	 * </P>
	 * 
	 * @param start vertex where search begins
	 * @param end   the algorithm terminates just after this vertex is visited
	 */
	public void DoDFS(V start, V end) {
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDFSHasBegun();
		}
		HashSet<V> visited = new HashSet<V>();
		Stack<V> stack = new Stack<V>();
		stack.push(start);
		while(!stack.isEmpty()) {
			V idx = stack.pop();
			if(!visited.contains(idx)) {
			for (GraphAlgorithmObserver<V> observer : observerList) {
				observer.notifyVisit(idx);
			}
			if(idx.equals(end)) {
				for (GraphAlgorithmObserver<V> observer : observerList) {
					observer.notifySearchIsOver();
				}
				break;
			}
				visited.add(idx);
				for (V value : map.get(idx).keySet()) {
					stack.push(value);
				}
			}
		}
		
	}
	
	/** 
	 * <P>Perform Dijkstra's algorithm, beginning at the "start"
	 * vertex.</P>
	 * 
	 * <P>The algorithm DOES NOT terminate when the "end" vertex
	 * is reached.  It will continue until EVERY vertex in the
	 * graph has been added to the finished set.</P>
	 * 
	 * <P>Before the algorithm begins, this method goes through 
	 * the collection of Observers, calling notifyDijkstraHasBegun 
	 * on each Observer.</P>
	 * 
	 * <P>Each time a vertex is added to the "finished set", this 
	 * method goes through the collection of Observers, calling 
	 * notifyDijkstraVertexFinished on each one (passing the vertex
	 * that was just added to the finished set as the first argument,
	 * and the optimal "cost" of the path leading to that vertex as
	 * the second argument.)</P>
	 * 
	 * <P>After all of the vertices have been added to the finished
	 * set, the algorithm will calculate the "least cost" path
	 * of vertices leading from the starting vertex to the ending
	 * vertex.  Next, it will go through the collection 
	 * of observers, calling notifyDijkstraIsOver on each one, 
	 * passing in as the argument the "lowest cost" sequence of 
	 * vertices that leads from start to end (I.e. the first vertex
	 * in the list will be the "start" vertex, and the last vertex
	 * in the list will be the "end" vertex.)</P>
	 * 
	 * @param start vertex where algorithm will start
	 * @param end special vertex used as the end of the path 
	 * reported to observers via the notifyDijkstraIsOver method.
	 */
	public void DoDijsktra(V start, V end) {
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDijkstraHasBegun();
		}
		HashSet<V> visited = new HashSet<V>();
		LinkedList<V> sequence = new LinkedList<V>();
		Map<V, HashMap<Integer, V>> dijstart = new HashMap<V, HashMap<Integer, V>>();
		dijstart.put(start, new HashMap<Integer, V>());
		dijstart.get(start).put(0, start);
		V minidx = start;
		while (visited.size() != map.size()) {
			visited.add(minidx);
			int finalweight = 0;
			for(Integer finw : dijstart.get(minidx).keySet()) {
				finalweight = finw;
			}
			for (GraphAlgorithmObserver<V> observer : observerList) {
				observer.notifyDijkstraVertexFinished(minidx,finalweight);
			}
			Map<V, Integer> process = map.get(minidx);
			for (V toadd : process.keySet()) {
				if (!dijstart.containsKey(toadd)) {
					dijstart.put(toadd, new HashMap<Integer, V>());
					dijstart.get(toadd).put((process.get(toadd) + finalweight), minidx);
				} else {
					for (Integer row : dijstart.get(toadd).keySet()) {
						if ((process.get(toadd) + finalweight) < row) {
							dijstart.replace(toadd, new HashMap<Integer, V>());
							dijstart.get(toadd).put((process.get(toadd) + finalweight), minidx);
						}
					}

				}
			}
			/*
			 * Getting the vertex with the lowest value.
			 */
			int minval = Integer.MAX_VALUE;
			for (V minvertex : dijstart.keySet()) {
				if (!visited.contains(minvertex)) {
					dijstart.get(minvertex).keySet();
					for (Integer col : dijstart.get(minvertex).keySet()) {
						if (col < minval) {
							minval = col;
							minidx = minvertex;
						}
					}
				}
			}
		}
		/*
		 * Adding every thing to the list in Order
		 */
		sequence.addFirst(end);
		while(sequence.getFirst() != start) {
			for(V algval:dijstart.get(sequence.getFirst()).values()) {
				sequence.addFirst(algval);
			}
		}
	for(GraphAlgorithmObserver<V> optimal : observerList) {	
			optimal.notifyDijkstraIsOver(sequence);
		}
	}
}