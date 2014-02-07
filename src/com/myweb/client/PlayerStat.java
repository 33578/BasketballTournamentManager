package com.myweb.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PlayerStat extends Composite {
	
	HorizontalPanel panel= new HorizontalPanel(); 
	FlexTable statsTable=new FlexTable();
	final SuggestBox typeName=new SuggestBox(oracle);
	static MultiWordSuggestOracle oracle=new MultiWordSuggestOracle();
	static List<String> playerNames=new ArrayList<String>();
	int tempIndex;
	
	 private final WorkServiceAsync workService = GWT
				.create(WorkService.class);
	
	public PlayerStat(){
		AbsolutePanel panel2=new AbsolutePanel();
		Label lab=new Label("In this page, you can add desired players' stats to the table and compare them. ");
		lab.setStyleName("statpageintro");
		panel2.add(lab);
		panel2.add(panel);
		panel2.setWidgetPosition(panel, 0, 25);
		initWidget(panel2);
		panel.add(usefulWidget());
	}
	
	private HorizontalPanel usefulWidget(){
		statsTable.setStyleName("StatTable");//css class
		
		HorizontalPanel pan=new HorizontalPanel();
		
		
		Label[] firstRow=new Label[]{new Label("Name"),new Label("Score"),new Label("Rebounds"),new Label("Fouls")};
		for (int i=0;i<4;i++) {
			
			statsTable.setWidget(0, i, firstRow[i]);
			firstRow[i].setStyleName("Th");
			statsTable.getCellFormatter().addStyleName(0,i,"T-Header");
		}
		
		Button findBtn=new Button("Add Player To Table!");
		findBtn.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				// TODO check tables height to limit adding more to the table
				if (statsTable.getRowCount()<17) {
				if (playerNames.contains(typeName.getValue()))
					getPlayerStat(typeName.getValue());
				typeName.setText("");
				}else{
					Window.alert("Table is full.");
				}
			}
		});
		Button xBtn=new Button("Delete This Row");
		xBtn.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				// TODO delete row
				if (statsTable.getRowCount()>1)
				statsTable.removeRow(1);//	 //get this row
			}
		});
		VerticalPanel buttons=new VerticalPanel();
		buttons.add(findBtn);
		buttons.add(xBtn);
		

		
		pan.add(typeName);
		pan.add(buttons);
		pan.add(statsTable);
		return pan;
	}
	

	private void cellformat(){
		for (int j=1;j<statsTable.getRowCount();j++){
			if (j%2==1){
				statsTable.getRowFormatter().addStyleName(j, "ToddRow");
			} else {
				statsTable.getRowFormatter().addStyleName(j, "TevenRow");
			} // give style name to odd/even row
		for (int i=0;i<statsTable.getCellCount(1);i++)
			statsTable.getCellFormatter().addStyleName(j,i,"T-Cell");
		}
	}
	
	private void getPlayerStat(String n){
		tempIndex=statsTable.getRowCount();
		// load methods to add to stats
		statsTable.setWidget(statsTable.getRowCount(),0,new Label(typeName.getValue()));
		loadScore(n);
		loadRebound(n);
		loadFoul(n);
		
		//
		cellformat();
	}
	// All kinds of getters (get from server)
	private void loadScore(String n){
		workService.getScoreByName(n, new AsyncCallback<Integer>(){
			@Override
			public void onFailure(Throwable caught) {
			Window.alert("FAILED");	// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(Integer result) {
				Label temp=new Label(""+result);
				temp.setStyleName("Ttext");
				statsTable.setWidget(tempIndex,1,temp);
				statsTable.getCellFormatter().addStyleName(tempIndex,1,"T-Cell");
			}
		});
	}
	private void loadRebound(String n){
		workService.getReboundByName(n, new AsyncCallback<Integer>(){
			@Override
			public void onFailure(Throwable caught) {
			Window.alert("FAILED");	// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(Integer result) {
				Label temp=new Label(""+result);
				temp.setStyleName("Ttext");
				statsTable.setWidget(tempIndex,2,temp);
				statsTable.getCellFormatter().addStyleName(tempIndex,2,"T-Cell");
			}
		});
	}
	private void loadFoul(String n){
		workService.getReboundByName(n, new AsyncCallback<Integer>(){
			@Override
			public void onFailure(Throwable caught) {
			Window.alert("FAILED");	// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(Integer result) {
				Label temp=new Label(""+result);
				temp.setStyleName("Ttext");
				statsTable.setWidget(tempIndex,3,temp);
				statsTable.getCellFormatter().addStyleName(tempIndex,3,"T-Cell");
			}
		});
	}
	
	
}
