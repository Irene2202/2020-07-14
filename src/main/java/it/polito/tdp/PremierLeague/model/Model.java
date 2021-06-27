package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private SimpleDirectedWeightedGraph<Team, DefaultWeightedEdge> grafo;
	private Map<Integer, Team> idMap;
	
	public Model() {
		dao=new PremierLeagueDAO();
		idMap=new HashMap<>();
		 dao.listAllTeams(idMap);
	}
	
	public void creaGrafo() {
		grafo=new SimpleDirectedWeightedGraph<Team, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//vertici
		Graphs.addAllVertices(grafo, idMap.values());
		
		//archi
		//do punti per la classifica
		for(Match m: dao.listAllMatches()) {
			if(m.getResultOfTeamHome()==1) {
				idMap.get(m.getTeamHomeID()).setPunti(idMap.get(m.getTeamHomeID()).getPunti()+3);
			} else if( m.getResultOfTeamHome()==-1) {
				idMap.get(m.getTeamAwayID()).setPunti(idMap.get(m.getTeamAwayID()).getPunti()+3);
			} else {//pareggio
				idMap.get(m.getTeamHomeID()).setPunti(idMap.get(m.getTeamHomeID()).getPunti()+1);
				idMap.get(m.getTeamAwayID()).setPunti(idMap.get(m.getTeamAwayID()).getPunti()+1);
			}
		}
		
		
		//ora che ho la classifica costruisco gli archi
		for(Integer i1: idMap.keySet()) {
			Team t1=idMap.get(i1);
			for(Integer i2: idMap.keySet()) {
				Team t2=idMap.get(i2);
				
				if(!(grafo.containsEdge(t1, t2) || grafo.containsEdge(t2, t1))){
					//se grafo non contiene un arco con questi due vertici lo creo (se devo)
					if(t1.getPunti()>t2.getPunti()) {
						Graphs.addEdge(grafo, t1, t2, t1.getPunti()-t2.getPunti());
					} else if (t1.getPunti()<t2.getPunti()) {
						Graphs.addEdge(grafo, t2, t1, t2.getPunti()-t1.getPunti());
					}
				}
				
			}//fine for interno
			
		}//fine for esterno
		
		//System.out.println("numero vertici: "+grafo.vertexSet().size());
		//System.out.println("numero archi: "+grafo.edgeSet().size());
	}
	
	public Set<Team> getVertici(){
		return grafo.vertexSet();
	}
	
	public int getNumVertici() {
		return grafo.vertexSet().size();
	}
	
	public int getNumArchi() {
		return grafo.edgeSet().size();
	}
	
	public List<Arco> classificaMigliori(Team t) {
		List<Arco> result=new ArrayList<>();
		
		for(DefaultWeightedEdge e : grafo.incomingEdgesOf(t)) {
			Arco a=new Arco(Graphs.getOppositeVertex(grafo, e, t), grafo.getEdgeWeight(e));
			result.add(a);
		}
		
		Collections.sort(result);
		
		return result;
	}
	
	public List<Arco> classificaPeggiori(Team t) {
		List<Arco> result=new ArrayList<>();
		
		for(DefaultWeightedEdge e : grafo.outgoingEdgesOf(t)) {
			Arco a=new Arco(Graphs.getOppositeVertex(grafo, e, t), grafo.getEdgeWeight(e));
			result.add(a);
		}
		
		Collections.sort(result);
		
		return result;
	}
	
}
