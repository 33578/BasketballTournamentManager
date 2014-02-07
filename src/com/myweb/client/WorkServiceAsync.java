package com.myweb.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("work")
public interface WorkServiceAsync {

	void addComment(String input, int tm, AsyncCallback<Void> callback);
	void removeAll(AsyncCallback<Void> callback);
	void addPlayer(String n, int team, AsyncCallback<Void> callback);
	void getNames(AsyncCallback<String[]> callback);
	void getNamesByTeam(int team, AsyncCallback<String[]> callback);
	void getScoreByName(String n, AsyncCallback<Integer> callback);
	void setScoreByName(String n,int score, AsyncCallback<Void> callback);
	void getReboundByName(String n, AsyncCallback<Integer> callback);
	void setReboundByName(String n, int rb, AsyncCallback<Void> callback);
	void getFoulByName(String n, AsyncCallback<Integer> callback);
	void setFoulByName(String n, int fl, AsyncCallback<Void> callback);
	void getCommentsByTeam(int tm, AsyncCallback<String[]> callback);
	void getCommentDatesByTeam(int tm, AsyncCallback<String[]> callback);
	void removeAllComments(AsyncCallback<Void> callback);
	void getSortedTeams(AsyncCallback<int[]> callback);
	void getTeamScore(int tm, AsyncCallback<Integer> callback);
	void addSchool(int n, AsyncCallback<Void> callback);
	void getWinsByTeam(int n, AsyncCallback<Integer> callback);
	void setWinsByTeam(int n, int wins, AsyncCallback<Void> callback);
	void getLosesByTeam(int n, AsyncCallback<Integer> callback);
	void setLosesByTeam(int n, int loses, AsyncCallback<Void> callback);
	void getLossesByTeam(int n, AsyncCallback<Integer> callback);
	void setLossesByTeam(int n, int losses, AsyncCallback<Void> callback);
	void setTotalScoresByTeam(int n, AsyncCallback<Void> callback);
	void setTotalFoulsByTeam(int n, AsyncCallback<Void> callback);
	void getTeamFoul(int tm, AsyncCallback<Integer> callback);
	void getVoteByName(String n, AsyncCallback<Integer> callback);
	void addVoteByName(String n, AsyncCallback<String> callback);
	void addIP(AsyncCallback<Void> callback);
	void getMVP(AsyncCallback<String> callback);
	void removePlayer(String n, AsyncCallback<Void> callback);
	void setAn(String n, AsyncCallback<Void> callback);
	void getAn(AsyncCallback<String> callback);
	void getNamesRB(AsyncCallback<String[]> callback);
	void getNamesFL(AsyncCallback<String[]> callback);
	void setCM(String n, AsyncCallback<Void> callback);
	void getCM(AsyncCallback<String> callback);

}
