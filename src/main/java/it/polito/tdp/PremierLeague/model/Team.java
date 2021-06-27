package it.polito.tdp.PremierLeague.model;

public class Team {
	Integer teamID;
	String name;
	
	private int punti;
	
	private int numReporter;

	public Team(Integer teamID, String name) {
		super();
		this.teamID = teamID;
		this.name = name;
		punti=0;
	}
	
	public Integer getTeamID() {
		return teamID;
	}
	public void setTeamID(Integer teamID) {
		this.teamID = teamID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPunti(int p) {
		punti=p;
	}
	
	public int getPunti() {
		return punti;
	}
	
	public int getNumReporter() {
		return numReporter;
	}

	public void setNumReporter(int numReporter) {
		this.numReporter = numReporter;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teamID == null) ? 0 : teamID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (teamID == null) {
			if (other.teamID != null)
				return false;
		} else if (!teamID.equals(other.teamID))
			return false;
		return true;
	}
	
}
