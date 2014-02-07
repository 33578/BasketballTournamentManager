package com.myweb.server;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Comment implements Comparable<Comment> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String content;
	@Persistent
	private Date createDate;
	@Persistent
	private int team;
	
	public Comment() {
		this.createDate = new Date();
	}
 
	public Comment(String symbol,int tm) {
		this();
		this.content = symbol;
		this.team=tm;
	}
 
	public Long getId() {
		return this.id;
	}
 
	public String getComment() {
		return this.content;
	}
 
	public Date getCreateDate() {
		return this.createDate;
	}
 
	public void setNote(String note) {
		this.content = note;
	}
	public void setTeam(int n) {
		this.team = n;
	}
	public int getTeam() {
		return this.team;
	}

	@Override
	public int compareTo(Comment o) {
		 return getCreateDate().compareTo(o.getCreateDate());
	} //overriding compareTo method in Comparable interface
	  //  to sort Date easily  http://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date
	
}
