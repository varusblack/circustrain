package utiles.factoria;

import java.util.List;

public interface Graph {

	public void addVertex(Vertex v);
	public void addEdge(Vertex v1,Vertex v2);
	public Boolean hasEdge(Vertex v1,Vertex v2);
	public List<Vertex> getVertexList();
	
}
