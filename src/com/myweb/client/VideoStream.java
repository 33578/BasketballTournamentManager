package com.myweb.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class VideoStream extends Composite {
  HorizontalPanel panel=new HorizontalPanel();
  HTML v= new HTML("<iframe width=\"990\" height=\"500\" src=\"//www.youtube.com/embed/O2x2t16IyGM" frameborder=\"0\" allowfullscreen></iframe>");
  
	public VideoStream() {
		initWidget(panel);	
		panel.setStyleName("video");
		panel.add(v);
		
	}
	
	
	
}
