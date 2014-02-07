package com.myweb.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("work")
public interface WorkService extends RemoteService {
	public void addPlayer(String n,int team);
	public String[] getNames();
	public String[] getNamesRB();
	public String[] getNamesFL();
	public String[] getNamesByTeam(int team);
	public int getScoreByName(String n);
	public void setScoreByName(String n,int score);
	public int getVoteByName(String n);
	public String addVoteByName(String n);
	public String getMVP();
	public int getReboundByName(String n);
	public void setReboundByName(String n,int rb);
	public int getFoulByName(String n);
	public void setFoulByName(String n,int fl);
	public void addComment(String input,int tm);
	public String[] getCommentsByTeam(int tm);
	public String[] getCommentDatesByTeam(int tm);
	public int getTeamScore(int tm);
	public int getTeamFoul(int tm);
	public int[] getSortedTeams();
	public void removeAll();
	public void removeAllComments();
	public void addSchool(int n);
	public int getWinsByTeam(int n);
	public void setWinsByTeam(int n, int wins);
	public int getLosesByTeam(int n);
	public void setLosesByTeam(int n, int loses);
	public int getLossesByTeam(int n);
	public void setLossesByTeam(int n, int losses);
	public void setTotalScoresByTeam(int n);
	public void setTotalFoulsByTeam(int n);
	public void addIP();
	public void removePlayer(String n);
	public void setAn(String n);
	public String getAn();
	public void setCM(String n);
	public String getCM();
	
}
