package com.myweb.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Schedule extends Composite {

	Image day1=new Image("../day1.PNG");
	Image day2=new Image("../day2.PNG");
	Image day3=new Image("../day3.PNG");
	Button day1btn=new Button("DAY 1 ");
	Button day2btn=new Button("DAY 2 ");
	Button day3btn=new Button("DAY 3 ");
	
	public Schedule(){
		VerticalPanel panel=new VerticalPanel();
		HorizontalPanel btns=new HorizontalPanel();
		btns.add(day1btn);
		btns.add(day2btn);
		btns.add(day3btn);
		
		day1btn.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				day1.setVisible(true);
				day2.setVisible(false);
				day3.setVisible(false);
			}
		});
		day2btn.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				day1.setVisible(false);
				day2.setVisible(true);
				day3.setVisible(false);
			}
		});
		day3btn.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				day1.setVisible(false);
				day2.setVisible(false);
				day3.setVisible(true);
			}
		});
		day1.setSize("1100px", "400px");
		day2.setSize("1100px", "400px");
		day3.setSize("1100px", "400px");
		day1.setStyleName("sched");
		day2.setStyleName("sched");
		day3.setStyleName("sched");
		initWidget(panel);
		
		day2.setVisible(false);
		day3.setVisible(false);
		panel.add(btns);
		panel.add(day1);
		panel.add(day2);
		panel.add(day3);
		
	}
}
