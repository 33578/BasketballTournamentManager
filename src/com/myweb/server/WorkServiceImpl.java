package com.myweb.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.myweb.client.WorkService;
import com.myweb.shared.FieldVerifier;

public class WorkServiceImpl extends RemoteServiceServlet implements WorkService{
	private static final long serialVersionUID = 1L;
	private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional"); 
			//Learning how to store persistent data using JDO   http://www.programcreek.com/2011/01/a-example-application-of-gwt/
	
	@Override
	public void addPlayer(String n, int team){
		if (!FieldVerifier.isValidName(n))
		throw new IllegalArgumentException("Name must be at least 4 characters long");
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(new Player(n,team)); //add player 
		} finally {
			pm.close();
		}
	}
	@Override
	public void addComment(String input, int tm) {
		if (!FieldVerifier.isValidName(input))
		throw new IllegalArgumentException("Your comment is too short!");
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(new Comment(input,tm)); //add comment
		} finally {
			pm.close();
		}
	}
	
	private List<Comment> getComments(int tm){      // helper method
		PersistenceManager pm = getPersistenceManager();
		List<Comment> symbols = new ArrayList<Comment>();
		try {
			Query query = pm.newQuery(Comment.class);
			@SuppressWarnings("unchecked")
			List<Comment> cmts = (List<Comment>)  query.execute();
			// sort by date
			for (Comment a : cmts) {
				if (a.getTeam()==tm){  //only add to list when team number matches
					symbols.add(a); 
				}
			}
		} finally {
			pm.close();
		}
		Collections.sort(symbols); // sort by date
		return symbols;
	}
	
	@Override
	public String[] getCommentsByTeam(int tm) {
		String[] ret = new String[getComments(tm).size()]; 
		int i =0;
		for (Comment s : getComments(tm)) {
			ret[i] = s.getComment();
			i++;
		}
		return ret; 
	}
	
	@Override
	public String[] getCommentDatesByTeam(int tm) {
		String[] ret = new String[getComments(tm).size()]; 
		int i =0;
		for (Comment s : getComments(tm)) {
			ret[i] = s.getCreateDate().toString();
			i++;
		}
		return ret; 
	}
	
	@Override
	public void removeAll() {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(Player.class);
			@SuppressWarnings("unchecked")
			List<Player> Players = (List<Player>)  query.execute();
			for (Player a : Players) {
					pm.deletePersistent(a);
				}
		} finally {
			pm.close();
		}
	}
	@Override
	public void removePlayer(String n) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(Player.class);
			@SuppressWarnings("unchecked")
			List<Player> Players = (List<Player>)  query.execute();
			for (Player a : Players) {
				if (a.getName().equals(n))
					pm.deletePersistent(a);
				}
		} finally {
			pm.close();
		}
	}
	
	@Override
	public String[] getNames() { //sorted by score maybe? not useful right now
		PersistenceManager pm = getPersistenceManager();
		List<String> symbols = new ArrayList<String>();
		try {
			Query query = pm.newQuery(Player.class);
			@SuppressWarnings("unchecked")
			List<Player> Players = (List<Player>)  query.execute();
			Collections.sort(Players);
			for (Player a : Players) {
				symbols.add(a.getName());
			}
		} finally {
			pm.close();
		}
 
		String[] ret = new String[symbols.size()];
 
		int i = 0;
		for (String s : symbols) {
			ret[i] = s;
			i++;
		}
 
		return ret;
	}
	public String[] getNamesRB() { //sorted by score maybe? not useful right now
		PersistenceManager pm = getPersistenceManager();
		List<String> symbols = new ArrayList<String>();
		try {
			Query query = pm.newQuery(Player.class);
			@SuppressWarnings("unchecked")
			List<Player> Players = (List<Player>)  query.execute();
			Collections.sort(Players, new Comparator<Player>(){
				@Override
				public int compare(Player p1, Player p2) {
					if (p1.getRebound()<p2.getRebound())
					return 1;
					else if (p1.getRebound()==p2.getRebound())
						return 0;
					else return -1;
				}
			});
			for (Player a : Players) {
				symbols.add(a.getName());
			}
		} finally {
			pm.close();
		}
 
		String[] ret = new String[symbols.size()];
 
		int i = 0;
		for (String s : symbols) {
			ret[i] = s;
			i++;
		}
 
		return ret;
	}
	
	public String[] getNamesFL() { //sorted by score maybe? not useful right now
		PersistenceManager pm = getPersistenceManager();
		List<String> symbols = new ArrayList<String>();
		try {
			Query query = pm.newQuery(Player.class);
			@SuppressWarnings("unchecked")
			List<Player> Players = (List<Player>)  query.execute();
			Collections.sort(Players, new Comparator<Player>(){
				@Override
				public int compare(Player p1, Player p2) {
					if (p1.getFoul()<p2.getFoul())
					return 1;
					else if (p1.getFoul()==p2.getFoul())
						return 0;
					else return -1;
				}
			});
			for (Player a : Players) {
				symbols.add(a.getName());
			}
		} finally {
			pm.close();
		}
 
		String[] ret = new String[symbols.size()];
 
		int i = 0;
		for (String s : symbols) {
			ret[i] = s;
			i++;
		}
 
		return ret;
	}
/*	@Override
	public int[] getSortedTeams() {

		teams.add(0);
		teams.add(3);
		teams.add(2);
		teams.add(4);
		teams.add(5);
		teams.add(1);
		
		int z=0;
		int[] sorted=new int[6];
		for (int i:teams){
			sorted[z]=i;
			z++;
		}
		return sorted;
}*/	
	
	
	@Override
	public String[] getNamesByTeam(int team) {
		PersistenceManager pm = getPersistenceManager();
		List<String> symbols = new ArrayList<String>();
		try {
			Query query = pm.newQuery(Player.class);
			@SuppressWarnings("unchecked")
			List<Player> Players = (List<Player>)  query.execute();
			for (Player a : Players) {
				if (a.getTeam()==team){  //only add to list when team number matches
				symbols.add(a.getName());
				}
			}
		} finally {
			pm.close();
		}
		String[] ret = new String[symbols.size()];
		int i = 0;
		for (String s : symbols) {
			ret[i] = s;
			i++;
		}
		return ret; 
	}


	private Player getPlayer(String n){
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(Player.class);
			@SuppressWarnings("unchecked")
			List<Player> Players = (List<Player>)  query.execute();
			for (Player a : Players) {
				if (a.getName().equals(n)){  
				 return a; // if name matches, return Player object
				}
			}
		} finally {
			pm.close();
		}
		return null;//if no matching name found, don't return anything
	}
	
	@Override
	public int getScoreByName(String n) {
		return getPlayer(n).getScore();
	}

	@Override
	public void setScoreByName(String n, int score) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(Player.class);
			@SuppressWarnings("unchecked")
			List<Player> Players = (List<Player>)  query.execute();
			for (Player a : Players) {
				if (a.getName().equals(n)){  
				 a.setScore(score);// 
				}
			}
		} finally {
			pm.close();
		}
	}

	@Override
	public int getReboundByName(String n) {
		return getPlayer(n).getRebound();
	}


	@Override
	public void setReboundByName(String n, int rb) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(Player.class);
			@SuppressWarnings("unchecked")
			List<Player> Players = (List<Player>)  query.execute();
			for (Player a : Players) {
				if (a.getName().equals(n)){  
				 a.setRebound(rb); // 
				}
			}
		} finally {
			pm.close();
		}
	}


	@Override
	public int getFoulByName(String n) {
		return getPlayer(n).getFoul();
	}


	@Override
	public void setFoulByName(String n, int fl) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(Player.class);
			@SuppressWarnings("unchecked")
			List<Player> Players = (List<Player>)  query.execute();
			for (Player a : Players) {
				if (a.getName().equals(n)){  
				 a.setFoul(fl); //
				}
			}
		} finally {
			pm.close();
		}
	}
	
	@Override
	public void removeAllComments() {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(Comment.class);
			@SuppressWarnings("unchecked")
			List<Comment> Comments = (List<Comment>)  query.execute();
			for (Comment a : Comments) {
					pm.deletePersistent(a);
				}
		} finally {
			pm.close();
		}
	}
	
	@Override
	public void addSchool(int n) {
			PersistenceManager pm = getPersistenceManager();
			try {
				pm.makePersistent(new School(n)); //add school only if not duplicated 
			} finally {
				pm.close();
			}
	}
	
	private School getSchool(int n){
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(School.class);
			@SuppressWarnings("unchecked")
			List<School> Schools = (List<School>)  query.execute();
			for (School a : Schools) {
				if (a.getTeam()==n){  
				 return a; // if name matches, return Player object
				}
			}
		} finally {
			pm.close();
		}
		return null;//if no matching name found, don't return anything
	}
	
	@Override
	public int[] getSortedTeams() {
		PersistenceManager pm = getPersistenceManager();
		List<Integer> teams = new ArrayList<Integer>();
		try {
			Query query = pm.newQuery(School.class);
			@SuppressWarnings("unchecked")
			List<School> Schools = (List<School>)  query.execute();
			Collections.sort(Schools);//sort by total score
			for (School a : Schools) {
				teams.add(a.getTeam());
			}
		} finally {
			pm.close();
		}
		int[] ret = new int[teams.size()];
		int i = 0;
		for (int s : teams) {
			ret[i] = s;
			i++;
		}
		return ret;
	}
	
	
	@Override
	public int getWinsByTeam(int n) {
		return getSchool(n).getWins();
	}
	@Override
	public void setWinsByTeam(int n, int wins) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(School.class);
			@SuppressWarnings("unchecked")
			List<School> Schools = (List<School>)  query.execute();
			for (School a : Schools) {
				if (a.getTeam()==n){  
				a.setWins(wins);
				}
			}
		} finally {
			pm.close();
		}
	}
	@Override
	public int getLosesByTeam(int n) {
		return getSchool(n).getLoses();
	}
	@Override
	public void setLosesByTeam(int n, int loses) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(School.class);
			@SuppressWarnings("unchecked")
			List<School> Schools = (List<School>)  query.execute();
			for (School a : Schools) {
				if (a.getTeam()==n){  
				a.setLoses(loses);
				}
			}
		} finally {
			pm.close();
		}
	}
	@Override
	public int getLossesByTeam(int n) {
		return getSchool(n).getLosses();
	}
	@Override
	public void setLossesByTeam(int n, int losses) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(School.class);
			@SuppressWarnings("unchecked")
			List<School> Schools = (List<School>)  query.execute();
			for (School a : Schools) {
				if (a.getTeam()==n){  
				a.setLosses(losses);
				}
			}
		} finally {
			pm.close();
		}
	}
	@Override
	public int getTeamScore(int tm){  // helper method
		PersistenceManager pm = getPersistenceManager();
		int score=0;
		try {
			Query query = pm.newQuery(Player.class);
			@SuppressWarnings("unchecked")
			List<Player> Players = (List<Player>)  query.execute();
			for (Player a : Players) {
				if (a.getTeam()==tm){  //only add to list when team number matches
					score+=a.getScore();
				}
			}
		} finally {
			pm.close();
		}
		return score;
	}
	@Override
	public int getTeamFoul(int tm){  // helper method
		PersistenceManager pm = getPersistenceManager();
		int foul=0;
		try {
			Query query = pm.newQuery(Player.class);
			@SuppressWarnings("unchecked")
			List<Player> Players = (List<Player>)  query.execute();
			for (Player a : Players) {
				if (a.getTeam()==tm){  //only add to list when team number matches
					foul+=a.getFoul();
				}
			}
		} finally {
			pm.close();
		}
		return foul;
	}
	@Override
	public void setTotalScoresByTeam(int n) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(School.class);
			@SuppressWarnings("unchecked")
			List<School> Schools = (List<School>)  query.execute();
			for (School a : Schools) {
				if (a.getTeam()==n){  
				a.setTotalScore(getTeamScore(n));
				}
			}
		} finally {
			pm.close();
		}
	}
	@Override
	public void setTotalFoulsByTeam(int n) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(School.class);
			@SuppressWarnings("unchecked")
			List<School> Schools = (List<School>)  query.execute();
			for (School a : Schools) {
				if (a.getTeam()==n){  
				a.setTotalFouls(getTeamFoul(n));
				}
			}
		} finally {
			pm.close();
		}
	}
	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}
	@Override
	public int getVoteByName(String n) {
		return getPlayer(n).getVote();
	}
	
	@Override
	public String getMVP(){
		PersistenceManager pm = getPersistenceManager();
		String mvp = "";
		try {
			Query query = pm.newQuery(Player.class);
			@SuppressWarnings("unchecked")
			List<Player> Players = (List<Player>)  query.execute();
			Collections.sort(Players, new Comparator<Player>(){
				@Override
				public int compare(Player o1, Player o2) {
					if (o1.getVote()<o2.getVote())
					return 1;
					else if (o1.getVote()==o2.getVote())
						return 0;
					else return -1;
				}
			});
				mvp=Players.get(0).getName();
		} finally {
			pm.close();
		}
		return mvp;
	}
	
	
	
	@Override
	public String addVoteByName(String n) {
		PersistenceManager pm = getPersistenceManager();
		String ip=getThreadLocalRequest().getRemoteAddr();
		try {
			Query query = pm.newQuery(Player.class);
			@SuppressWarnings("unchecked")
			List<Player> Players = (List<Player>)  query.execute();
			for (Player a : Players) {
				if (a.getName().equals(n)){  
					if (!getIPs().contains(ip)){
				 a.addVote();
				 addIP();
				 return "Voted!";
					} else
						return "You can only Vote for once.";
				}
			}
		} finally {
			pm.close();
		}
		return null;
	}
	@Override
	public void addIP() {
		PersistenceManager pm = getPersistenceManager();
		String ip=getThreadLocalRequest().getRemoteAddr();
		try {
			if (!getIPs().contains(ip))
				pm.makePersistent(new IPAddress(ip));
		} finally {
			pm.close();
		}
	}

	public ArrayList<String> getIPs() { //helper method
		PersistenceManager pm = getPersistenceManager();
		ArrayList<String> ips = new ArrayList<String>();
		try {
			Query query = pm.newQuery(IPAddress.class);
			@SuppressWarnings("unchecked")
			List<IPAddress> IPAddresses = (List<IPAddress>)  query.execute();
			for (IPAddress a : IPAddresses) {
				ips.add(a.getAddress());
			}
		} finally {
			pm.close();
		}
		return (ArrayList<String>) ips;
	}
	
	@Override
	public void setAn(String n) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(School.class);
			@SuppressWarnings("unchecked")
			List<School> Schools = (List<School>)  query.execute();
			for (School a : Schools) {
				if (a.getTeam()==0){  // for convinience use schl 0 for announcement
				a.setAnnouncement(n);
				}
			}
		} finally {
			pm.close();
		}
		
	}
	@Override
	public String getAn() {
		return getSchool(0).getAnnouncement();
	}
	@Override
	public void setCM(String n) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(School.class);
			@SuppressWarnings("unchecked")
			List<School> Schools = (List<School>)  query.execute();
			for (School a : Schools) {
				if (a.getTeam()==1){  // for convinience use schl 0 for announcement
				a.setAnnouncement(n);
				}
			}
		} finally {
			pm.close();
		}
		
	}
	@Override
	public String getCM() {
		return getSchool(1).getAnnouncement();
	}
}
