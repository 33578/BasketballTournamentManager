package com.myweb.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MyWeb implements EntryPoint {
	
	
	//static String[][] players=new String[6][10]; //10players 6 teams
	
	public final static int JIS=0,ISM=1,ISB=2,TAS=3,SAS=4,ISKL=5;
	//   JIS=0,ISM=1,ISB=2,TAS=3,SAS=4,ISKL=5
	
	TabPanel mainBody = new TabPanel();
	HorizontalPanel footer= new HorizontalPanel();
	

	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";


	 final static WorkServiceAsync staticworkService = GWT
			.create(WorkService.class);

	public void onModuleLoad() {
		DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById("loading")); // delete loading screen
		//Loading screen http://turbomanage.wordpress.com/2009/10/13/how-to-create-a-splash-screen-while-gwt-loads/
		//Splash image from:  http://www.iasas.asia/wp-content/uploads/2009/11/pic_iasaslogo_colors_blackround_100opac.jpg
		mainBody.add(new Dashboard()," Dashboard ");//0
		mainBody.add(new SquadPage(),"  Squad  ");//1
		mainBody.add(new FullLeagueTable(), " League Table ");//2
		mainBody.add(new PlayerStat(), " Player Stat ");//3
		mainBody.add(new Schedule(), " Schedule ");
		mainBody.add(new VideoStream(), " Streaming ");
		mainBody.add(new Admin(),"Admin");
		mainBody.selectTab(0);
		DOM.setElementAttribute(mainBody.getElement(), "class", "mainbody"); 
		// How to give gwt widget css id or class for customization
		// http://www.tutorialspoint.com/gwt/gwt_style_with_css.htm
		 loadNamesForStatPage();

		  new Timer() { 
	            @Override 
	            public void run() { 
	            	loadNamesForStatPage();
	            } 

	        }.scheduleRepeating(300000); // reload 5minutes
	        
		for (Image a:schoolLogo()) {
			a.setSize("140px", "72px");
	    footer.add(a);
		}		
		
		
		RootPanel.get("tabmenu").add(mainBody);
		RootPanel.get("ft").add(footer);
		// Create a handler for the sendButton and nameField
			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
	}
	
	private Image[] schoolLogo(){
		Image[] img = new Image[] {new Image("../jis_mini.png"), new Image("../iskl_mini.png"), new Image("../ism_mini.png"),new Image("../isb_mini.png"),new Image("../tas_mini.png"),new Image("../sas_mini.png")};
		
		return img;
	}
	
	private void loadNamesForStatPage(){
		staticworkService.getNames(new AsyncCallback<String[]>(){
			@Override
			public void onFailure(Throwable caught) {
			Window.alert("FAILED");	// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(String[] result) {
				if (!(result.length>=60))
					for (String a:result){
						if (!PlayerStat.playerNames.contains(a)) {
						PlayerStat.oracle.add(a);
						PlayerStat.playerNames.add(a);
						}
					}
				}
		});
	}

	
}
