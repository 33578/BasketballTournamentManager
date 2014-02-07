package com.myweb.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FullLeagueTable extends Composite{
	private final WorkServiceAsync workService = GWT
			.create(WorkService.class);
	
	FlexTable FullLeagueTable=new FlexTable();
	
	public FullLeagueTable(){
		
		VerticalPanel panel=new VerticalPanel();
		
		initWidget(panel);
		panel.add(DrawLeagueTable());
		loadSortedTeams();
		new Timer() { 
	            @Override 
	            public void run() { 
	            	loadSortedTeams();
	            } 

	        }.scheduleRepeating(300000); // every 5 minutes
	        
		
	}
	
	private FlexTable DrawLeagueTable(){
		FullLeagueTable.setStyleName("FLTable");//css class
		return FullLeagueTable;
	}
	
	private void cellformat(){
	for (int j=1;j<7;j++) //only 7 rows in table
		for (int i=0;i<FullLeagueTable.getCellCount(j);i++)
			 FullLeagueTable.getCellFormatter().addStyleName(j,i,"T-Cell");
	}
	
	private void loadSortedTeams(){
		workService.getSortedTeams(new AsyncCallback<int[]>(){
			@Override
			public void onFailure(Throwable caught) {
			Window.alert("FAILED");	// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(int[] result) {
				FullLeagueTable.clear();
				displayFLTable(result);
			}
		});
	}
	
	private void displayFLTable(int[] teams){
		Label[] a=new Label[]{new Label("Rank"),new Label("School"),new Label("Wins"),
				new Label("Loses"),new Label("Total Score"),new Label("Total Score Conceded"),new Label("Total Fouls")};
		for (int i=0;i<a.length;i++) {
		a[i].setStyleName("Th");
		FullLeagueTable.setWidget(0, i, a[i]);
		FullLeagueTable.getCellFormatter().addStyleName(0,i,"T-Header");
		}
		for (int i=1;i<7;i++){
			displayRow(teams[i-1],i);
		}
		cellformat();
	}
	private void displayRow(int f,int index){
		String temp = "";
		switch (f) {
		case MyWeb.JIS : temp="JIS"; break;
		case MyWeb.ISM : temp="ISM"; break;
		case MyWeb.ISB : temp="ISB"; break;
		case MyWeb.SAS : temp="SAS"; break;
		case MyWeb.TAS : temp="TAS"; break;
		case MyWeb.ISKL : temp="ISKL"; break;
			}
		
		Label a=new Label(index+""); // number
		Label b=new Label(temp); // school
		a.setStyleName("Ttext");
		b.setStyleName("Ttext");
		
		FullLeagueTable.setWidget(index,0,a);
		FullLeagueTable.setWidget(index, 1, b);
		loadWins(f,index); // wins
		loadLoses(f,index);	//loses
		loadTS(f,index);//TS
		loadLosses(f,index);//TSA
		loadFouls(f,index);//TFs
		
			if (index%2==1){
				FullLeagueTable.getRowFormatter().addStyleName(index, "ToddRow");
			} else {
				FullLeagueTable.getRowFormatter().addStyleName(index, "TevenRow");
			} // give style name to odd/even row
	}
	// Way to do, displayRow calls service method that get values- from that onSuccess draw label
	private void loadWins(int t,final int index){
		workService.getWinsByTeam(t, new AsyncCallback<Integer>(){
			@Override
			public void onFailure(Throwable caught) {
			Window.alert("FAILED");	// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(Integer result) {
				Label a=new Label(result+"");
				a.setStyleName("Ttext");
				FullLeagueTable.setWidget(index, 2, a);
				FullLeagueTable.getCellFormatter().addStyleName(index,2,"T-Cell");
			}
		});
	}
	private void loadLoses(int t,final int index){
		workService.getLosesByTeam(t, new AsyncCallback<Integer>(){
			@Override
			public void onFailure(Throwable caught) {
			Window.alert("FAILED");	// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(Integer result) {
				Label a=new Label(result+"");
				a.setStyleName("Ttext");
				FullLeagueTable.setWidget(index, 3, a);
				FullLeagueTable.getCellFormatter().addStyleName(index,3,"T-Cell");
			}
		});
	}
	private void loadTS(int t,final int index){
		workService.getTeamScore(t, new AsyncCallback<Integer>(){
			@Override
			public void onFailure(Throwable caught) {
			Window.alert("FAILED");	// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(Integer result) {
				Label a=new Label(result+"");
				a.setStyleName("Ttext");
				FullLeagueTable.setWidget(index, 4, a);
				FullLeagueTable.getCellFormatter().addStyleName(index,4,"T-Cell");
			}
		});
	}
	private void loadLosses(int t,final int index){
		workService.getLossesByTeam(t, new AsyncCallback<Integer>(){
			@Override
			public void onFailure(Throwable caught) {
			Window.alert("FAILED");	// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(Integer result) {
				Label a=new Label(result+"");
				a.setStyleName("Ttext");
				FullLeagueTable.setWidget(index, 5, a);
				FullLeagueTable.getCellFormatter().addStyleName(index,5,"T-Cell");
			}
		});
	}
	private void loadFouls(int t,final int index){
		workService.getTeamFoul(t,new AsyncCallback<Integer>(){
			@Override
			public void onFailure(Throwable caught) {
			Window.alert("FAILED");	// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(Integer result) {
				Label a=new Label(result+"");
				a.setStyleName("Ttext");
				FullLeagueTable.setWidget(index, 6, a);
				FullLeagueTable.getCellFormatter().addStyleName(index,6,"T-Cell");
			}
		});
	}
}
