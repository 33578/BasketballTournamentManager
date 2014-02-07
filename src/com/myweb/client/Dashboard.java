package com.myweb.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Dashboard extends Composite{
    //Get an idea of Composite to better organize page/widget.  http://examples.roughian.com/#Widgets~Composite
	FlexTable LeagueTable=new FlexTable();
	FlexTable TopScorerTable=new FlexTable();
	FlexTable TopRebounderTable=new FlexTable();
	FlexTable TopFoulerTable=new FlexTable();
	HorizontalPanel anp2=new HorizontalPanel();
	static Label announcement=new Label(); 
	static Label announcement2=new Label();
	
	final SuggestBox typeName=new SuggestBox(PlayerStat.oracle);
	
	Label LTTitle= new Label("- League Table -");
	Label TSTitle= new Label("- Top Scorers -");
	Label TRTitle= new Label("- Top Rebounders -");
	Label TFTitle= new Label("- Top Foulers -");
	Label info=new Label("Vote For MVP !");
	Label info2=new Label("Current MVP is : ");
	
	
	Button refreshb = new Button("Refresh Tables");
	Label mvpis= new Label("MVP");
	
	private final WorkServiceAsync workService = GWT
			.create(WorkService.class);
	
	public Dashboard(){
		final AbsolutePanel panel = new AbsolutePanel();
		VerticalPanel holder1= new VerticalPanel();//LT
		VerticalPanel holder2= new VerticalPanel();//TS
		VerticalPanel holder3= new VerticalPanel();//TR
		VerticalPanel holder4= new VerticalPanel();//TF
		VerticalPanel holder5= new VerticalPanel();//Votebox
		VerticalPanel holder6= new VerticalPanel();//Show mvp
		
		final Button vote=new Button("Vote For This Player");
		vote.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if (PlayerStat.playerNames.contains(typeName.getValue())){
					addVote();
					typeName.setText("");
				 vote.setEnabled(false);
				 
			} else {
				Window.alert("Please type a player's name properly.");
			}
		}
		});
		
		
		LTTitle.setStyleName("T-Title");
		TSTitle.setStyleName("T-Title");
		TRTitle.setStyleName("T-Title");
		TFTitle.setStyleName("T-Title");
		info.setStyleName("T-Title");
		info2.setStyleName("T-subtitle");
		mvpis.setStyleName("MVP");
		holder6.setStyleName("MVPHolder");
		
		holder1.add(LTTitle);
		holder1.add(LeagueTable());
		holder2.add(TSTitle);
		holder2.add(TopScorerTable());
		holder3.add(TRTitle);
		holder3.add(TopRebounderTable());
		holder4.add(TFTitle);
		holder4.add(TopFoulerTable());
		holder5.add(info);
		holder5.add(typeName);
		holder5.add(vote);
		holder6.add(info2);
		holder6.add(mvpis);
		
		initWidget(panel);
		
		panel.setStyleName("DashboardBody");
		panel.add(annpan());
		panel.add(annpan2());
		panel.add(holder1);
		panel.add(holder2);
		panel.add(holder3);
		panel.add(holder4);
		panel.add(holder5);
		panel.add(holder6);
	//	panel.add(refreshb);
		panel.setWidgetPosition(anp2, 0, 25);
		panel.setWidgetPosition(holder1, 50, 40);
		panel.setWidgetPosition(holder2, 350, 40);
		panel.setWidgetPosition(holder3, 650, 40);
		panel.setWidgetPosition(holder4, 950, 40);
		panel.setWidgetPosition(holder5, 50, 340);
		panel.setWidgetPosition(holder6, 300, 340);
	//	panel.setWidgetPosition(refreshb, 1092, 1);
		
		loadSortedTeams();
		loadSortedNames();
		loadSortedNames2();
		loadSortedNames3();
		loadAnnouncement();
		loadAnnouncement2();
		callMVP();
		   new Timer() { 
	            @Override 
	            public void run() { 
	    			loadSortedTeams();
					loadSortedNames();
					loadSortedNames2();
					loadSortedNames3();
					loadAnnouncement();
					loadAnnouncement2();
					callMVP();
	            } 

	        }.scheduleRepeating(60000); 
	/*	refreshb.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				loadSortedTeams();
				loadSortedNames();
				loadSortedNames2();
				loadSortedNames3();
				loadAnnouncement();
				loadAnnouncement2();
				callMVP();
			}
		});*/
	}
	
	private HorizontalPanel annpan(){
		HorizontalPanel anp=new HorizontalPanel();
		anp.setStyleName("anpanel");
		loadAnnouncement();
		announcement.setStyleName("antext");
		anp.add(announcement);
		return anp;
	}
	private HorizontalPanel annpan2(){

		anp2.setStyleName("anpanel");
		loadAnnouncement2();
		announcement2.setStyleName("antext2");
		anp2.add(announcement2);
		return anp2;
	}
	
	//FlexTable tips http://www.java2s.com/Code/Java/GWT/FlexTablewithrowstyle.htm
	private FlexTable LeagueTable(){
		LeagueTable.setStyleName("DBTable");//css class
		return LeagueTable;
	}
	
	private FlexTable TopScorerTable(){
		TopScorerTable.setStyleName("DBTable");//css class
		return TopScorerTable;
	}
	private FlexTable TopRebounderTable(){
		TopRebounderTable.setStyleName("DBTable");//css class
		return TopRebounderTable;
	}
	private FlexTable TopFoulerTable(){
		TopFoulerTable.setStyleName("DBTable");//css class
		return TopFoulerTable;
	}
	
	private void loadSortedTeams(){
		workService.getSortedTeams(new AsyncCallback<int[]>(){
			@Override
			public void onFailure(Throwable caught) {
			Window.alert("FAILED");	// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(int[] result) {
				LeagueTable.clear();
				displayLeagueTable(result);
			}
		});
	}
	
	private void displayLeagueTable(int[] teams){
		Label a=new Label("Rank");
		Label b=new Label("School");
		a.setStyleName("Th");
		b.setStyleName("Th");
		LeagueTable.setWidget(0, 0, a);
		LeagueTable.setWidget(0, 1, b);
		 LeagueTable.getCellFormatter().addStyleName(0,0,"T-Header");
		 LeagueTable.getCellFormatter().addStyleName(0,1,"T-Header");
		int index=1;
		for (int c: teams){
			displayRow(c,index);
		index++;	
		}
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
		Label a=new Label(index+"");
		Label b=new Label(temp);
		a.setStyleName("Ttext");
		b.setStyleName("Ttext");
			LeagueTable.setWidget(index,0,a);
			LeagueTable.setWidget(index, 1, b);
			 LeagueTable.getCellFormatter().addStyleName(index,0,"T-Cell");
			 LeagueTable.getCellFormatter().addStyleName(index,1,"T-Cell");
			if (index%2==1){
				LeagueTable.getRowFormatter().addStyleName(index, "ToddRow");
			} else {
				LeagueTable.getRowFormatter().addStyleName(index, "TevenRow");
			} // give style name to odd/even row
	}
	//high scroe
	private void loadSortedNames(){
		workService.getNames(new AsyncCallback<String[]>(){
			@Override
			public void onFailure(Throwable caught) {
			Window.alert("FAILED");	// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(String[] result) {
				if (result.length>=6){
				TopScorerTable.clear();
				displayTSTable(result);
				}
			}
		});
	}
	private void displayTSTable(String[] teams){
		Label a=new Label("Rank");
		Label b=new Label("Name");
		a.setStyleName("Th");
		b.setStyleName("Th");
		TopScorerTable.setWidget(0, 0, a);
		TopScorerTable.setWidget(0, 1, b);
		TopScorerTable.getCellFormatter().addStyleName(0,0,"T-Header");
		TopScorerTable.getCellFormatter().addStyleName(0,1,"T-Header");
		for (int i=1;i<7;i++){
			displayRow(teams[i-1],i);
		}
	}
	private void displayRow(String f,int index){
		Label a=new Label(index+"");
		Label b=new Label(f);
		a.setStyleName("Ttext");
		b.setStyleName("Ttext");
		TopScorerTable.setWidget(index,0,a);
		TopScorerTable.setWidget(index, 1, b);
		TopScorerTable.getCellFormatter().addStyleName(index,0,"T-Cell");
		TopScorerTable.getCellFormatter().addStyleName(index,1,"T-Cell");
			if (index%2==1){
				TopScorerTable.getRowFormatter().addStyleName(index, "ToddRow");
			} else {
				TopScorerTable.getRowFormatter().addStyleName(index, "TevenRow");
			} // give style name to odd/even row
	}
	private void loadSortedNames2(){
		workService.getNamesRB(new AsyncCallback<String[]>(){
			@Override
			public void onFailure(Throwable caught) {
			Window.alert("FAILED");	// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(String[] result) {
				if (result.length>=6){
				TopRebounderTable.clear();
				displayTRTable(result);
				}
			}
		});
	}
	private void displayTRTable(String[] teams){
		Label a=new Label("Rank");
		Label b=new Label("Name");
		a.setStyleName("Th");
		b.setStyleName("Th");
		TopRebounderTable.setWidget(0, 0, a);
		TopRebounderTable.setWidget(0, 1, b);
		TopRebounderTable.getCellFormatter().addStyleName(0,0,"T-Header");
		TopRebounderTable.getCellFormatter().addStyleName(0,1,"T-Header");
		for (int i=1;i<7;i++){
			displayRow2(teams[i-1],i);
		}
	}
	private void displayRow2(String f,int index){
		Label a=new Label(index+"");
		Label b=new Label(f);
		a.setStyleName("Ttext");
		b.setStyleName("Ttext");
		TopRebounderTable.setWidget(index,0,a);
		TopRebounderTable.setWidget(index, 1, b);
		TopRebounderTable.getCellFormatter().addStyleName(index,0,"T-Cell");
		TopRebounderTable.getCellFormatter().addStyleName(index,1,"T-Cell");
			if (index%2==1){
				TopRebounderTable.getRowFormatter().addStyleName(index, "ToddRow");
			} else {
				TopRebounderTable.getRowFormatter().addStyleName(index, "TevenRow");
			} // give style name to odd/even row
	}
	private void loadSortedNames3(){
		workService.getNamesFL(new AsyncCallback<String[]>(){
			@Override
			public void onFailure(Throwable caught) {
			Window.alert("FAILED");	// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(String[] result) {
				if (result.length>=6){
					TopFoulerTable.clear();
				displayTFTable(result);
				}
			}
		});
	}
	private void displayTFTable(String[] teams){
		Label a=new Label("Rank");
		Label b=new Label("Name");
		a.setStyleName("Th");
		b.setStyleName("Th");
		TopFoulerTable.setWidget(0, 0, a);
		TopFoulerTable.setWidget(0, 1, b);
		TopFoulerTable.getCellFormatter().addStyleName(0,0,"T-Header");
		TopFoulerTable.getCellFormatter().addStyleName(0,1,"T-Header");
		for (int i=1;i<7;i++){
			displayRow3(teams[i-1],i);
		}
	}
	private void displayRow3(String f,int index){
		Label a=new Label(index+"");
		Label b=new Label(f);
		a.setStyleName("Ttext");
		b.setStyleName("Ttext");
		TopFoulerTable.setWidget(index,0,a);
		TopFoulerTable.setWidget(index, 1, b);
		TopFoulerTable.getCellFormatter().addStyleName(index,0,"T-Cell");
		TopFoulerTable.getCellFormatter().addStyleName(index,1,"T-Cell");
			if (index%2==1){
				TopFoulerTable.getRowFormatter().addStyleName(index, "ToddRow");
			} else {
				TopFoulerTable.getRowFormatter().addStyleName(index, "TevenRow");
			} // give style name to odd/even row
	}
	
	private void addVote(){
		workService.addVoteByName(typeName.getValue(), new AsyncCallback<String>(){
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("FAIL");
			}
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				callMVP();
				Window.alert(result);
			}
		});
	}
	
	private void callMVP(){
		workService.getMVP(new AsyncCallback<String>(){
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				mvpis.setText(result);
			}
		});
	}

	 static void loadAnnouncement(){
		MyWeb.staticworkService.getAn(new AsyncCallback<String>(){
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(String result) {
				String temp="Announcement : ";
				// TODO Auto-generated method stub
				announcement.setText(temp+result);
			}
		});
	}
	 static void loadAnnouncement2(){
		MyWeb.staticworkService.getCM(new AsyncCallback<String>(){
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(String result) {
				String temp="Upcoming Match : ";
				// TODO Auto-generated method stub
				announcement2.setText(temp+result);
			}
		});
	}
		
}
