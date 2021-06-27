package it.polito.tdp.PremierLeague.model;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;


public class Simulatore {
	
	//eventi
	private PriorityQueue<Evento> queue;
	
	//parametri
	private Graph<Team, DefaultWeightedEdge> grafo;
	private Map<Integer, Team> map;
	
	
	private int numReporter;
	private int critico;
	
	//output
	private Map<Integer, Integer> reporterPerPartita; //ha ogni MatchId associo il num di reporter
	private int partiteCritiche;
	
	public Simulatore(int N, int critico, Graph<Team, DefaultWeightedEdge> grafo, List<Match> matches, Map<Integer, Team> idMap) {
		this.numReporter=N;
		this.critico=critico;
		this.grafo=grafo;
		this.map=idMap;
		
		//inizializzo
		this.queue=new PriorityQueue<>();
		partiteCritiche=0;
		
		for(Integer i:idMap.keySet()) {
			idMap.get(i).setNumReporter(N);
		}
		
		for(Match m:matches) {
			queue.add(new Evento(m.date, m));
		}
	}
	
	public void run() {
		while(!queue.isEmpty()) {
			Evento e=queue.poll();
			processEvent(e);
		}
	}
	
	private void processEvent(Evento e) {
		Match m=e.getMatch();
		int numReporter;
		
		if(m.resultOfTeamHome==1) {
			//casa vince e ospiti perde
			Team vince=map.get(m.teamHomeID);
			Team perde=map.get(m.teamAwayID);
			
			numReporter=perde.getNumReporter()+vince.getNumReporter();
			
			//guardo casa
			for(int i=0; i<vince.getNumReporter(); i++) {
				//per ogni reporter
				if(Math.random()<0.5) {
					//il reporter vien 'promosso'
					if(grafo.incomingEdgesOf(vince).size()>0) {
						//se ha almeno una squadra migliore
						//prendo un team casuale tra i vertici opposti degli archi entranti 
						//aggiorno il numero di reporter di entrambi i team
					}
				}
			} //fine ciclo for su sq. vincente
			
			//guardo ospiti
			for(int i=0; i<perde.getNumReporter(); i++) {
				//per ogni reporter
				if(Math.random()<0.2) {
					//il reporter vien 'promosso'
					if(grafo.incomingEdgesOf(perde).size()>0) {
						//se ha almeno una squadra migliore
						//prendo un team casuale tra i vertici opposti degli archi entranti 
						//aggiorno il numero di reporter di entrambi i team
					}
				}
			} //fine ciclo for su sq. perdente
			
		} //fine if 'se casa vince
		
		else if(m.resultOfTeamHome==-1) {
			//casa vince e ospiti perde
			Team perde=map.get(m.teamHomeID);
			Team vince=map.get(m.teamAwayID);
			
			numReporter=perde.getNumReporter()+vince.getNumReporter();
			
			//guardo casa
			for(int i=0; i<vince.getNumReporter(); i++) {
				//per ogni reporter
				if(Math.random()<0.5) {
					//il reporter vien 'promosso'
					if(grafo.incomingEdgesOf(vince).size()>0) {
						//se ha almeno una squadra migliore
						//prendo un team casuale tra i vertici opposti degli archi entranti 
						//aggiorno il numero di reporter di entrambi i team
					}
				}
			} //fine ciclo for su sq. vincente
			
			//guardo ospiti
			for(int i=0; i<perde.getNumReporter(); i++) {
				//per ogni reporter
				if(Math.random()<0.2) {
					//il reporter vien 'declassato'
					if(grafo.outgoingEdgesOf(vince).size()>0) {
						//se ha almeno una squadra migliore
						//prendo un team casuale tra i vertici opposti degli archi entranti 
						//aggiorno il numero di reporter di entrambi i team
					}
				}
			} //fine ciclo for su sq. perdente
			
		} //fine if 'se casa perde'
		else {
			//pareggio
			Team perde=map.get(m.teamHomeID);
			Team vince=map.get(m.teamAwayID);
			
			numReporter=perde.getNumReporter()+vince.getNumReporter();
		}
		
		if(numReporter<critico) {
			this.partiteCritiche++;
		}
		
		reporterPerPartita.put(m.matchID, numReporter);
	}
	
	public int getNumPartiteCritiche() {
		return this.partiteCritiche;
	}
	
	public int getMediaReporter() {
		int numPartite=0;
		int numReporter=0;
		
		for(Integer i: reporterPerPartita.keySet()) {
			numPartite++;
			numReporter+=reporterPerPartita.get(i);
		}
		
		return numReporter/numPartite;		
	}

}
