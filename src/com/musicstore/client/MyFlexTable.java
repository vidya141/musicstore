package com.musicstore.client;

import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;

public class MyFlexTable extends FlexTable implements HasMouseOutHandlers, HasMouseOverHandlers {

	public MyFlexTable() {
	super();
	Button hiddenButton=new Button("I am hidden");
	hiddenButton.setVisible(false);
	setWidget(0, 0, new Hyperlink("Some Link", "somelink"));
	setWidget(0, 1, hiddenButton);
	setBorderWidth(1);
	setWidget(1, 0, new HTML("Some empty line.."));
	getFlexCellFormatter().setColSpan(1, 0, 2);//just to see the mouse rollover in other rows of the table.
	
	}

 @Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
	// TODO Auto-generated method stub
	return addDomHandler(handler, MouseOutEvent.getType());
	}

 @Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
	// TODO Auto-generated method stub
	return addDomHandler(handler, MouseOverEvent.getType());
	}

}