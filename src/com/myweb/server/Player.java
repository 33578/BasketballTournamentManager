package com.myweb.server;


import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Player implements Comparable<Player>{
	//Learning how to store persistent data using JDO   http://www.programcreek.com/2011/01/a-example-application-of-gwt/
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String name;
	@Persistent
	private int team;
	@Persistent
	private int score;
	@Persistent
	private int rebound;
	@Persistent
	private int foul;
	@Persistent
	private int vote;
	
	


public Player(String n, int team) {
	 this.name=n;
	 this.team=team;
	 this.score=0;
	 this.rebound=0;
	 this.foul=0;
	 this.vote=0;
 }
 
	public Long getId() {
		return this.id;
	}
	 public int getVote() {
			return vote;
		}

		public void addVote() {
			this.vote++;
		}
	public void setName(String n) {
		this.name = n;
	}
	public String getName() {
		return this.name;
	}
	public void setTeam(int n) {
		this.team = n;
	}
	public int getTeam() {
		return this.team;
	}
	public void setScore(int n) {
		this.score = n;
	}
	public int getScore() {
		return this.score;
	}
	public void setRebound(int n) {
		this.rebound = n;
	}
	public int getRebound() {
		return this.rebound;
	}
	public void setFoul(int n) {
		this.foul = n;
	}
	public int getFoul() {
		return this.foul;
	}

	@Override
	public int compareTo(Player o) {
		 if (getScore()<o.getScore())
				return 1; // Decreasing order
				 else if (getScore()==o.getScore())
					 return 0;
				 else return -1;
	}
	
}
