package it.polito.tdp.PremierLeague.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{
	
	private LocalDateTime data;
	private Match match;
	
	public Evento(LocalDateTime data, Match match) {
		super();
		this.data = data;
		this.match = match;
	}

	@Override
	public int compareTo(Evento other) {
		return this.data.compareTo(other.data);
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

}
