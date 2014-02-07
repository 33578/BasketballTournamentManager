package com.myweb.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class School implements Comparable<School> {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private int team;
	@Persistent
	private  int wins;
	@Persistent
	private int loses;
	@Persistent
	private int totalScore;
	@Persistent
	private int losses;
	@Persistent 
	private int totalFouls;
	@Persistent 
	private String announcement;
	
	public String getAnnouncement() {
		return announcement;
	}


	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}


	public School(int tm){
		this.team=tm;
		this.wins=0;
		this.loses=0;
		this.totalScore=0;
		this.losses=0;
		this.totalFouls=0;
		if (tm==0)
		this.announcement="Any Announcement Goes here.";
		if (tm==1)
			this.announcement="ISX VS ISY at hh:mm !!!";
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getTeam() {
		return team;
	}
	public void setTeam(int team) {
		this.team = team;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getLoses() {
		return loses;
	}
	public void setLoses(int loses) {
		this.loses = loses;
	}
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public int getLosses() {
		return losses;
	}
	public void setLosses(int losses) {
		this.losses = losses;
	}

	public int getTotalFouls() {
		return totalFouls;
	}
	public void setTotalFouls(int totalFouls) {
		this.totalFouls = totalFouls;
	}

	@Override
	public int compareTo(School o) { // compare total score by default
		 if (getWins()<o.getWins())
		return 1;
		 else if (getWins()==o.getWins())
			 return 0;
		 else return -1;
	}
	
}
