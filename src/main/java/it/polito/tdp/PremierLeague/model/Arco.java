package it.polito.tdp.PremierLeague.model;

public class Arco implements Comparable<Arco>{
	
	private Team t;
	private double peso;
	
	public Arco(Team t, double peso) {
		super();
		this.t = t;
		this.peso = peso;
	}

	public Team getT() {
		return t;
	}

	public void setT(Team t) {
		this.t = t;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(Arco other) {
		return (int) (this.peso-other.peso);
	}

	@Override
	public String toString() {
		return t.name+" ("+(int)peso+")";
	}

}
