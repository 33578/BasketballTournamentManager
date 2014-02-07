package com.myweb.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.myweb.shared.FieldVerifier;
public class Admin extends Composite{
	 static boolean ADMIN_MODE=false;
	 
	 private PasswordTextBox pwBox=new PasswordTextBox();
	 
	private ListBox nameList=new ListBox();  // gwt API page
	//widget 0
	final private TextBox winsBox=new TextBox();
	final private TextBox losesBox=new TextBox();
	final private TextBox lossesBox=new TextBox();
	//widget 2
	final private TextBox scoreBox=new TextBox();
	final private TextBox rbBox=new TextBox();
	final private TextBox flBox=new TextBox();
	final Button removePlayer= new Button("DELETE THIS PLAYER");
 
 int tempScore=0;
 int tempRB=0;
 int tempFl=0;
 
 int tempWins=0;
 int tempLoses=0;
 int tempLosses=0;
 
 //final static 
 private final WorkServiceAsync workService = GWT
			.create(WorkService.class);
 
  public Admin(){
	  HorizontalPanel panel = new HorizontalPanel();
		initWidget(panel);
		panel.add(playerList());
		initSchl();
  }
 
 private HorizontalPanel playerList(){

	 
	 final HorizontalPanel pan = new HorizontalPanel();
	 final VerticalPanel widget0=new VerticalPanel();
	final VerticalPanel widget1=new VerticalPanel();
	final VerticalPanel widget2=new VerticalPanel();
	//widget0 secondary
	Label iwins=new Label("Wins");
	Label iloses=new Label("Loses");
	Label ilosses=new Label("Score Conceded");
	 winsBox.setWidth("80px");
	 losesBox.setWidth("80px");
	 lossesBox.setWidth("80px");
	 //widget 1 secondary
Label iname=new Label("Name");
 final TextBox nameBox= new TextBox();
	 nameBox.setWidth("280px");
	 //widget 2 secondary
	 Label iscore=new Label("Score");
	 Label ifoul=new Label("Fouls");
	 Label irebound=new Label("Rebounds");
	 final Label fyi=new Label("(values will automatically save as you type)");
	 scoreBox.setWidth("80px");
	 scoreBox.setVisible(false);
	 rbBox.setWidth("80px");
	 rbBox.setVisible(false);
	 flBox.setWidth("80px");
	 flBox.setVisible(false);
	 fyi.setVisible(false);
	 // nameList is configured first.
		nameList.setSize("300px", "200px");
		nameList.setVisibleItemCount(6);
		//----School Select Menu----------------------
	 final ListBox schoolList= new ListBox();
	 schoolList.setSize("100px","110px");
	 schoolList.setVisibleItemCount(6);
	 schoolList.addItem("JIS");//0
	 schoolList.addItem("ISM");//1
	 schoolList.addItem("ISB");//2
	 schoolList.addItem("TAS");//3
	 schoolList.addItem("SAS");//4
	 schoolList.addItem("ISKL");//5
	 schoolList.addChangeHandler(new ChangeHandler()  //Change List when change is detected
	 {
		  public void onChange(ChangeEvent event)
		  {
			  int selectedIndex=schoolList.getSelectedIndex();
			  nameList.clear();
				loadNamesByTeam(selectedIndex);//
				scoreBox.setVisible(false);
				rbBox.setVisible(false);
				flBox.setVisible(false);
				fyi.setVisible(false);
				 removePlayer.setVisible(false);
				// DONE load total score and total fouls
				  loadWinsBySchool(selectedIndex); // save player's score to temp integer var
				  loadLosesBySchool(selectedIndex);
				  loadLossesBySchool(selectedIndex);
				  
		  }
		 });

	 nameList.addChangeHandler(new ChangeHandler()   // edit score box
	 {
		  public void onChange(ChangeEvent event)
		  {
			  int selectedIndex=nameList.getSelectedIndex();
			  if (selectedIndex!=-1) {
				  scoreBox.setVisible(true);
				  rbBox.setVisible(true);
				  flBox.setVisible(true);
				  fyi.setVisible(true);
				  removePlayer.setVisible(true);
				  loadScoreByName(nameList.getValue(selectedIndex)); // save player's score to temp integer var
				  loadReboundByName(nameList.getValue(selectedIndex));
				  loadFoulByName(nameList.getValue(selectedIndex));
			  }
		  }
		 });
	 
	 scoreBox.addKeyUpHandler(new KeyUpHandler() {
		@Override
		public void onKeyUp(KeyUpEvent event) {
			int selectedIndex=nameList.getSelectedIndex();
			int selectedIndex2=schoolList.getSelectedIndex();
			saveScoreByName(nameList.getValue(selectedIndex), Integer.parseInt(scoreBox.getValue()));
			refreshTeamTotals(selectedIndex2);
		}
	 });
	 rbBox.addKeyUpHandler(new KeyUpHandler() {
		@Override
		public void onKeyUp(KeyUpEvent event) {
			int selectedIndex=nameList.getSelectedIndex();
			saveReboundByName(nameList.getValue(selectedIndex), Integer.parseInt(rbBox.getValue()));
		}
	 });
	 
	 flBox.addKeyUpHandler(new KeyUpHandler() {
		@Override
		public void onKeyUp(KeyUpEvent event) {
			int selectedIndex=nameList.getSelectedIndex();
			saveFoulByName(nameList.getValue(selectedIndex), Integer.parseInt(flBox.getValue()));
		}
	 });
	 
	 winsBox.addKeyUpHandler(new KeyUpHandler() {
		@Override
		public void onKeyUp(KeyUpEvent event) {
			int selectedIndex=schoolList.getSelectedIndex();
			saveWinsByTeam(selectedIndex, Integer.parseInt(winsBox.getValue()));
		}
	 });
	 losesBox.addKeyUpHandler(new KeyUpHandler() {
		@Override
		public void onKeyUp(KeyUpEvent event) {
			int selectedIndex=schoolList.getSelectedIndex();
			saveLosesByTeam(selectedIndex, Integer.parseInt(losesBox.getValue()));
		}
	 });
	 lossesBox.addKeyUpHandler(new KeyUpHandler() {
		@Override
		public void onKeyUp(KeyUpEvent event) {
			int selectedIndex=schoolList.getSelectedIndex();
			saveLossesByTeam(selectedIndex, Integer.parseInt(lossesBox.getValue()));
		}
	 });
	 
	 Button create= new Button("Create", new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if (!FieldVerifier.isValidName(nameBox.getText())) {
					Window.alert("Please enter at least four characters");
					return;
				}
				int selectedIndex=schoolList.getSelectedIndex();
				if (selectedIndex!=-1 && selectedIndex<10){ // must be lower than 10
				addPlayer(nameBox.getText(), selectedIndex);  // newly added player has 0 score
				nameBox.setText("");
				// also refresh(add);
				} else {
					Window.alert("Please Select School First");
				}
			}
	 });

	 removePlayer.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				removePlayer(nameList.getValue(nameList.getSelectedIndex()));			
				}
		});
	 removePlayer.setVisible(false);
	 final Button removeAll= new Button("DELETE ALL PLAYER DATA (Takes time)", new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				removeAll();			
				}
		});
	 final Button removeAllComment= new Button("DELETE ALL COMMENTS (Takes time)", new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				removeAllComments();			
				}
		});
	 
	 widget0.add(schoolList);
	 widget0.add(iwins);
	 widget0.add(winsBox);
	 widget0.add(iloses);
	 widget0.add(losesBox);
	 widget0.add(ilosses);
	 widget0.add(lossesBox);
	 
	 
	 widget1.add(nameList);
	 widget1.add(iname);
	 widget1.add(nameBox);
	 widget1.add(create);
	 
	 widget2.add(iscore);
	 widget2.add(scoreBox);
	 widget2.add(irebound);
	 widget2.add(rbBox);
	 widget2.add(ifoul);
	 widget2.add(flBox);
	 widget2.add(fyi);
	 widget2.add(removePlayer);
	 pan.add(pwBox);
	 //
	 final VerticalPanel anholder=new VerticalPanel();
	 Label aninf=new Label("Announcement");
	 Label aninf2=new Label("CurrentMatch");
	final TextArea an=new TextArea();
	final TextArea an2=new TextArea();
	 Button setan=new Button("Set Announcement");
	 Button setan2=new Button("Set Current Match");
	 an.setSize("200px", "120px");
	 an2.setSize("200px", "60px");
	 an.setVisibleLines(5);
	 an2.setVisibleLines(3);
	 anholder.add(aninf);
	 anholder.add(an);
	 anholder.add(setan);
	 anholder.add(aninf2);
	 anholder.add(an2);
	 anholder.add(setan2);
	 
	 setan.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				setAn(an.getValue());			
				}
		});
	 setan2.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				setCM(an2.getValue());			
				}
		});
	 
	 final VerticalPanel removeAllbtns=new VerticalPanel();
	 removeAllbtns.add(removeAll);
	 removeAllbtns.add(removeAllComment);
	 
	 pwBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (pwBox.getValue().equals("adminmod")){
					pwBox.setText("");
					pan.add(widget0);
					 pan.add(widget1);
					 pan.add(widget2);
					 pan.add(anholder);
					 pan.add(removeAllbtns);
				}
			}
		 });
	 
	 return pan;
 }
 private void removePlayer(String n){
		AsyncCallback callback = new AsyncCallback<Void>() {
			public void onFailure(Throwable error) {
				// do something, when fail
				Window.alert("failed");
			}
			public void onSuccess(Void ignore) {
				nameList.removeItem(nameList.getSelectedIndex());
				Window.alert("PLAYER DELETED");
			}
		};
		workService.removePlayer(n,callback);
	}
 
private void removeAll(){
	AsyncCallback callback = new AsyncCallback<Void>() {
		public void onFailure(Throwable error) {
			// do something, when fail
			Window.alert("failed");
		}
		public void onSuccess(Void ignore) {
			nameList.clear();
			Window.alert("ALL PLAYERS DELETED");
		}
	};
	workService.removeAll(callback);
}
private void removeAllComments(){
	AsyncCallback callback = new AsyncCallback<Void>() {
		public void onFailure(Throwable error) {
			// do something, when fail
			Window.alert("failed");
		}
		public void onSuccess(Void ignore) {
			nameList.clear();
			Window.alert("ALL COMMENTS DELETED");
		}
	};
	workService.removeAllComments(callback);
}

private void addPlayer(final String n,final int team){
	AsyncCallback callback = new AsyncCallback<Void>() {
		public void onFailure(Throwable error) {
			// do something, when fail
			Window.alert("failed");
		}
		public void onSuccess(Void ignore) {
       //   Window.alert("Name: "+n+"\n" +"Team:"+team+"\n"+"Score: "+sc);
			nameList.addItem(n);
		}
	};
	workService.addPlayer(n,team, callback);
}

private void loadNamesByTeam(int team) {
	workService.getNamesByTeam(team, new AsyncCallback<String[]>() {
		public void onFailure(Throwable error) {
		}
		public void onSuccess(String[] names) {
			addTeamsToList(names);
		}
	});
}

private void addTeamsToList(String[] names){
	for (String a:names){
		nameList.addItem(a);
	}
}

private void loadScoreByName(String n){
	workService.getScoreByName(n, new AsyncCallback<Integer>() {
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onSuccess(Integer result) {
			tempScore=result;	
			  scoreBox.setText(""+tempScore);
		}
	});
}
private void saveScoreByName(final String n,final int score){
	AsyncCallback callback = new AsyncCallback<Void>() {
		public void onFailure(Throwable error) {
			// do something, when fail
			Window.alert("failed");
		}
		public void onSuccess(Void ignore) {
           scoreBox.setText(""+score);
		}
	};
	workService.setScoreByName(n, score, callback);
}

private void loadReboundByName(String n){
	workService.getReboundByName(n, new AsyncCallback<Integer>() {
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onSuccess(Integer result) {
			tempRB=result;	
			  rbBox.setText(""+tempRB);
		}
	});
}
private void saveReboundByName(final String n,final int rb){
	AsyncCallback callback = new AsyncCallback<Void>() {
		public void onFailure(Throwable error) {
			// do something, when fail
			Window.alert("failed");
		}
		public void onSuccess(Void ignore) {
           rbBox.setText(""+rb);
		}
	};
	workService.setReboundByName(n, rb, callback);
}

private void loadFoulByName(String n){
	workService.getFoulByName(n, new AsyncCallback<Integer>() {
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onSuccess(Integer result) {
			tempFl=result;	
			  flBox.setText(""+tempFl);
		}
	});
}
private void saveFoulByName(final String n,final int fl){
	AsyncCallback callback = new AsyncCallback<Void>() {
		public void onFailure(Throwable error) {
			// do something, when fail
			Window.alert("failed");
		}
		public void onSuccess(Void ignore) {
           flBox.setText(""+fl);
		}
	};
	workService.setFoulByName(n, fl, callback);
}
////////////////////-----------------------------------------------------------
private void initSchl(){
	workService.getSortedTeams(new AsyncCallback<int[]>(){
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(int[] result) {
			if (result.length<1){
				makeSchool();
				Window.alert("School Initialized.");
			}
		}
	});
}

private void makeSchool(){
	 for (int i=0;i<6;i++)
		addschl(i); 
}

private void addschl(int n){
	AsyncCallback callback = new AsyncCallback<Void>() {
		public void onFailure(Throwable error) {
			// do something, when fail
			Window.alert("failed");
		}
		public void onSuccess(Void ignore) {
		}
	};
	workService.addSchool(n, callback);
}
private void loadWinsBySchool(int n){
	workService.getWinsByTeam(n, new AsyncCallback<Integer>() {
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onSuccess(Integer result) {
			tempWins=result;	
			  winsBox.setText(""+tempWins);
		}
	});
}
private void saveWinsByTeam(final int n,final int wins){
	AsyncCallback callback = new AsyncCallback<Void>() {
		public void onFailure(Throwable error) {
			// do something, when fail
			Window.alert("failed");
		}
		public void onSuccess(Void ignore) {
           winsBox.setText(""+wins);
		}
	};
	workService.setWinsByTeam(n, wins, callback);
}
private void loadLosesBySchool(int n){
	workService.getLosesByTeam(n, new AsyncCallback<Integer>() {
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onSuccess(Integer result) {
			tempLoses=result;	
			  losesBox.setText(""+tempLoses);
		}
	});
}
private void saveLosesByTeam(final int n,final int loses){
	AsyncCallback callback = new AsyncCallback<Void>() {
		public void onFailure(Throwable error) {
			// do something, when fail
			Window.alert("failed");
		}
		public void onSuccess(Void ignore) {
           losesBox.setText(""+loses);
		}
	};
	workService.setLosesByTeam(n, loses, callback);
}
private void loadLossesBySchool(int n){
	workService.getLossesByTeam(n, new AsyncCallback<Integer>() {
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onSuccess(Integer result) {
			tempLosses=result;	
			  lossesBox.setText(""+tempLosses);
		}
	});
}
private void saveLossesByTeam(final int n,final int losses){
	AsyncCallback callback = new AsyncCallback<Void>() {
		public void onFailure(Throwable error) {
			// do something, when fail
			Window.alert("failed");
		}
		public void onSuccess(Void ignore) {
           lossesBox.setText(""+losses);
		}
	};
	workService.setLossesByTeam(n, losses, callback);
}
 private void refreshTeamTotals(int n){
		AsyncCallback callback = new AsyncCallback<Void>() {
			public void onFailure(Throwable error) {
				// do something, when fail
				Window.alert("failed");
			}
			public void onSuccess(Void ignore) {
			}
		};
		workService.setTotalScoresByTeam(n, callback);
		workService.setTotalFoulsByTeam(n, callback);//TODO setTotalFoulsByTeam
 }
 private void setAn(String n){
		AsyncCallback callback = new AsyncCallback<Void>() {
			public void onFailure(Throwable error) {
				// do something, when fail
				Window.alert("setting AN failed");
			}
			public void onSuccess(Void ignore) {
				Window.alert("Announcement saved");
			}
		};
		workService.setAn(n, callback);
 }
 private void setCM(String n){
		AsyncCallback callback = new AsyncCallback<Void>() {
			public void onFailure(Throwable error) {
				// do something, when fail
				Window.alert("setting AN failed");
			}
			public void onSuccess(Void ignore) {
				Window.alert("Announcement saved");
			}
		};
		workService.setCM(n, callback);
}
 
}
