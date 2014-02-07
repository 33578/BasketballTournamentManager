package com.myweb.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.myweb.shared.FieldVerifier;

public class SquadPage extends Composite{
	
	private final WorkServiceAsync workService = GWT
			.create(WorkService.class);
	
	TabPanel panel= new TabPanel();
	VerticalPanel[] info=new VerticalPanel[6];
	ScrollPanel[] scrollpanel=new ScrollPanel[6];
	VerticalPanel[] holder=new VerticalPanel[6];
	TextArea[] typein = new TextArea[6];
	
	String[][] dates=new String[6][]; // receive date in order 6 means 6 pages

	
	public SquadPage(){
		initWidget(panel);
		for (int i=0;i<=5;i++){
			info[i]=new VerticalPanel();
			holder[i]=new VerticalPanel();
			scrollpanel[i]=new ScrollPanel();
			scrollpanel[i].setStyleName("scrollpan");
			typein[i]=new TextArea();
			scrollpanel[i].add(holder[i]); //initialize panel/widgets
		}
		panel.setStyleName("squad");
		panel.add(teamwall(MyWeb.JIS),"JIS");
		panel.add(teamwall(MyWeb.ISB),"ISB");
		panel.add(teamwall(MyWeb.ISM),"ISM");
		panel.add(teamwall(MyWeb.TAS),"TAS");
		panel.add(teamwall(MyWeb.SAS),"SAS");
		panel.add(teamwall(MyWeb.ISKL),"ISKL");
	//	panel.selectTab(0);
	}
	
	//
	private HorizontalPanel teamwall(int team){
		HorizontalPanel tw = new HorizontalPanel(); // big body
		
		info[team].setStyleName("infoPanel");
		tw.add(infobox(team));
		tw.add(wall(team));
		return tw;
	}

	// belong to wall-------------------------------------------------------------------------------
	private VerticalPanel infobox(final int team){

		String temp="";
		switch (team) {
		case MyWeb.JIS : temp="jis"; break;
		case MyWeb.ISM : temp="ism"; break;
		case MyWeb.ISB : temp="isb"; break;
		case MyWeb.SAS : temp="sas"; break;
		case MyWeb.TAS : temp="tas"; break;
		case MyWeb.ISKL : temp="iskl"; break;
		}
		
		 Image teamlogo= new Image("../"+temp+"_mini.png");
		 teamlogo.setSize("220px", "113px");
		 
		 
		 info[team].add(teamlogo);
		 
		 panel.addSelectionHandler(new SelectionHandler<Integer>(){
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				 loadCommentDates(team);
				loadCommentsByTeam(team);
			}
		 });
			 loadCommentDates(team);
			 loadNamesByTeam(team);
		return info[team];
	}

	private AbsolutePanel wall(final int team){
		AbsolutePanel wallp= new AbsolutePanel();
		wallp.setStyleName("wallpost");
		
	
		typein[team].setSize("500px", "70px");
		typein[team].setVisibleLines(3);
		
		final Button postbtn = new Button("Post !");
		ClickHandler handle=new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if (!FieldVerifier.isValidName(typein[team].getText())) {
					Window.alert("Your comment is too short!");
					return;
				}
				loadCommentDates(team);
				postbtn.setEnabled(false);
				Timer antispam= new Timer() {
					@Override
					public void run() {
						postbtn.setEnabled(true);
					}
				};
			postToServer(team);
			antispam.schedule(3000); // On how to use Timer/Schedule  http://stackoverflow.com/questions/12368316/gwt-timer-and-scheduler-classes
			}
		};
		postbtn.setSize("70px", "70px");
		postbtn.addClickHandler(handle);
		
		
		Label commenttitle=new Label("Team Wall");
		Label tip=new Label("Oldest Comments");
		wallp.add(typein[team]);
		wallp.add(postbtn);
		wallp.add(commenttitle);
		wallp.add(scrollpanel[team]);
		wallp.add(tip);
		
		wallp.setWidgetPosition(commenttitle, 25, 1);
		wallp.setWidgetPosition(typein[team], 25, 20);
	wallp.setWidgetPosition(postbtn, 550, 25);
	wallp.setWidgetPosition(scrollpanel[team], 25, 130);
		wallp.setWidgetPosition(tip,500,106);
		return wallp;
	}
	//--------------------------------------------------------------
	private void postToServer(final int team){
		AsyncCallback callback = new AsyncCallback<Void>() {
			public void onFailure(Throwable error) {
				// do something, when fail
				Window.alert("failed");
			}
			public void onSuccess(Void ignore) {
				//loadCommentsByTeam(team);
				displayComment(typein[team].getValue(),new Date().toString(),team);
				typein[team].setText("");  //do something to ui
			}
		};
		workService.addComment(typein[team].getValue(), team, callback);
	}
	
	private void loadNamesByTeam(final int team) {
		workService.getNamesByTeam(team, new AsyncCallback<String[]>() {
			public void onFailure(Throwable error) {
			}
			public void onSuccess(String[] names) {
				loadTeamsToList(names,team);
			}
		});
	}

	private void loadTeamsToList(String[] names, int team){
		 for (String a:names){
			 Label mo=new Label(a);
			 DOM.setElementAttribute(mo.getElement(), "id", "namesmo");
			info[team].add(mo);
		 }
	}
	
	
	private void loadCommentsByTeam(final int team) {
		workService.getCommentsByTeam(team, new AsyncCallback<String[]>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(final String[] result) {
				holder[team].clear();
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				    @Override
				    public void execute() {
				    	displayComments(result,team);
				    }
				});
				
			}
		});
	}

	
	
	private void displayComments(String[] cmts, int team){
		for (int i=0; i<cmts.length;i++)
		{
       displayComment(cmts[i],dates[team][i],team);
       }
	 
	 
	}
	private void loadCommentDates(final int tm){
		workService.getCommentDatesByTeam(tm, new AsyncCallback<String[]>(){
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(String[] result) {
				dates[tm]=result;
				
			}
		});
}
	
	private void displayComment(String a,String b, int team){
				Label co=new Label(a);
				Label dat=new Label(b);
				Label div=new Label("________________________________________________________________");
				DOM.setElementAttribute(co.getElement(), "id", "comments");
				DOM.setElementAttribute(dat.getElement(), "id", "dates");
				holder[team].add(co);
				holder[team].add(dat);
				holder[team].add(div);
				scrollpanel[team].scrollToBottom();
	}
}
