package com.musicstore.client;



import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MusicStoreGWT implements EntryPoint {
	
	/* Instance Variables*/
	private Button httpRequestButton = new Button("Request");
	final TextBox nameField = new TextBox();
	private FlexTable songTable = new FlexTable();
	
	/**
	 * Form for entering new songs
	 */
	private TextBox bandName = new TextBox();
	private TextBox songName = new TextBox();
	private TextBox price = new TextBox();
	private Button submitButton = new Button("Submit");
	private Label bandLabel = new Label("Band: ");
	private Label songLabel = new Label("Song: ");
	private Label priceLabel = new Label("Price: ");
	
	/*SignUp Page*/
	private Label signUpLabel = new Label("Not a Member, Sign Up here");
	private Label firstNameLabel = new Label("First Name");
	private Label passwordLabel = new Label("Password: ");	
	private Label lastNameLabel = new Label("Last Name");
	private Label emailLabel = new Label("Email: ");
	private Button signUpButton = new Button("sign up");
	private TextBox emailTextBox = new TextBox();
	private PasswordTextBox passwordTextBox = new PasswordTextBox();
	private TextBox firstNameTextBox = new TextBox();
	private TextBox lastNameTextBox = new TextBox();
	Grid signUpGrid = new Grid(5,2);
	private VerticalPanel loginPage = new VerticalPanel();
	private Label signUpSuccesLabel = new Label("One more step a confirmation email has been sent please confirm by clicking on it");
	
	/*Login page */
	private HorizontalPanel loginPanel = new HorizontalPanel();
	private Label loginEmailLabel = new Label("Email: ");
	private Label loginPasswordLabel = new Label("Password: ");
	private TextBox loginEmailTextBox = new TextBox();
	private TextBox loginPasswordTextBox = new TextBox();
	private Button logInButton = new Button("Login");
	
	/*Creating Tabs in page*/
	private TabPanel tabs = new TabPanel();
	private FlowPanel songListFlowPanel = new FlowPanel();
	private FlowPanel loginFlowPanel = new FlowPanel();
	final String url = "http://127.0.0.1:8080/musicstoregwt/song";
	final String searchUrl = "http://127.0.0.1:8080/musicstoregwt/search";
	final String songDetailsURL = "http://127.0.0.1:8080/musicstoregwt/songDetails";
	final String uservalidateURL = "http://127.0.0.1:8080/musicstoregwt/user";
	/*Creating Tab for search*/
	FlowPanel searchFlowPanel = new FlowPanel();
	ListBox songListBox = new ListBox();
	TextBox searchTextBox = new TextBox();
	Button goButton = new Button("Go");
	private FlexTable searchTable = new FlexTable();
	
	/*Shopping cart Panel*/
	FlowPanel shoppingCartPanel = new FlowPanel();
	ArrayList<String> shoppingCart = new ArrayList<String>();
	Button addToCartButton[];
	TextBox quantityTextBox[];
	
	public void onModuleLoad() {
		
    final String signupUrl = "http://127.0.0.1:8080/musicstoregwt/signup";
		
		
		nameField.setText("Hello");
	//	RootPanel.get("songList").add(httpRequestButton);
	//	RootPanel.get("songList").add(nameField);
		nameField.setFocus(true);
		nameField.selectAll();
		
		addForm();		
		addLogin();
		addSignUp();
	//	Cookies.removeCookie("Shopping Cart");
	//	if(Cookies.getCookie("Shopping Cart")!=null){
			addShoppingCart(Cookies.getCookie("Shopping Cart"));
	//	}
		
		loginPage.add(loginPanel);
		loginPage.add(signUpLabel);
		loginPage.add(signUpGrid);
		
	//	RootPanel.get().add(loginPage);
		
		addLoginTab();
		addSongListTab();
		addSearchTab();
		

		RootPanel.get().add(tabs);
	
		
		
		/*On Click of SignUp Button*/
		 signUpButton.addClickHandler(new ClickHandler(){
	    	public void onClick(ClickEvent event){
	    		JSONObject json = new JSONObject();
		    	 json.put("firstName",new JSONString(firstNameTextBox.getText()));
		    	 json.put("lastName", new JSONString(lastNameTextBox.getText()));
		    	 json.put("email", new JSONString(emailTextBox.getText()));
		    	 json.put("pwd", new JSONString(passwordTextBox.getText()));
	    		 signUpRequest(signupUrl,json.toString());
	    	}

	    });
		 
		 
		/*On Click Login Button*/
		 logInButton.addClickHandler(new ClickHandler(){
		    	public void onClick(ClickEvent event){
		    		JSONObject json = new JSONObject();
			    	 json.put("email",new JSONString(loginEmailTextBox.getText()));
			    	 json.put("pwd", new JSONString(loginPasswordTextBox.getText()));
		    		 validateUser(uservalidateURL,json.toString());
		    	}

		    });
		 
		/*Request button*/
		httpRequestButton.addClickHandler(new ClickHandler(){
	    	public void onClick(ClickEvent event){
	    		 httprequest(url);
	    	}

	    });
		
		/*Submit the form on clicking the submit button*/
		submitButton.addClickHandler(new ClickHandler(){
	    	public void onClick(ClickEvent event){
	    		 JSONObject json = new JSONObject();
		    	 json.put("bandName",new JSONString(bandName.getText()));
		    	 json.put("songName", new JSONString(songName.getText()));
		    	 json.put("songPrice", new JSONString(price.getText()));
		    	 insertFormIntoDB(url,json.toString());
	    	}
	    });
		
		/*On clicking the Go button*/
		goButton.addClickHandler(new ClickHandler(){
	    	public void onClick(ClickEvent event){
	    		JSONObject json = new JSONObject();
	    		json.put("dropDownValue", new JSONString(songListBox.getItemText(songListBox.getSelectedIndex())));
	    		 json.put("searchValue", new JSONString(searchTextBox.getText()));
	    		 getSearchResults(searchUrl, json.toString());
	    	}
	    });	
	}
	private void addSearchTab() {
		
		songListBox.addItem("Song");
		songListBox.addItem("Band");
		songListBox.setVisibleItemCount(1);
		searchFlowPanel.add(songListBox);
		searchFlowPanel.add(searchTextBox);
		searchFlowPanel.add(goButton);
		tabs.add(searchFlowPanel,"Search");
	}
	private void addSongListTab() {
		songListFlowPanel = new FlowPanel();
		songListFlowPanel.add(new Label("List of songs in the store"));
		
		httprequest(url);
		tabs.add(songListFlowPanel, "Song List");		
	}
	
	private void addLoginTab() {
		loginFlowPanel.add(new Label("Login Page"));	
		loginFlowPanel.add(loginPanel);
		loginFlowPanel.add(signUpGrid);
		tabs.add(loginFlowPanel, "Login");		
	}
	
	private void addSignUp() {	
		signUpGrid.setWidget(0, 0, firstNameLabel);
		signUpGrid.setWidget(0, 1, firstNameTextBox);
		signUpGrid.setWidget(1, 0, lastNameLabel);
		signUpGrid.setWidget(1, 1, lastNameTextBox);
		signUpGrid.setWidget(2, 0, passwordLabel);
		signUpGrid.setWidget(2, 1, passwordTextBox);
		signUpGrid.setWidget(3, 0, emailLabel);
		signUpGrid.setWidget(3, 1, emailTextBox);
		signUpGrid.setWidget(4, 1, signUpButton);
		
	}
	/**
	 * Lets the user enter login info and login
	 */
	private void addLogin() {
		loginPanel.add(loginEmailLabel);
		loginPanel.add(loginEmailTextBox);
		loginPanel.add(loginPasswordLabel);
		loginPanel.add(loginPasswordTextBox);
		loginPanel.add(logInButton);				
	}
	
	private void addShoppingCart(String shopping){
		FlexTable shoppingTable = new FlexTable();
		shoppingTable.setText(0, 0, shopping);
		shoppingCartPanel.add(shoppingTable);
		tabs.add(shoppingCartPanel,"Shoppin Cart");
	}

	/**
	 * Call the servlet and send the data in the form to be inserted intoDB
	 * @param url
	 * @param postData
	 */
	protected void insertFormIntoDB(String url, String postData) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);

	    try {
	    	builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
	        Request response = builder.sendRequest("songs="+postData, new RequestCallback() {
	        public void onError(Request request, Throwable exception) {
	          Window.alert("error" );
	        }

	        public void onResponseReceived(Request request, Response response) {
	        	nameField.setText("Added to DB");
	        	
	        }
	      });
	    } catch (RequestException e) {
	      Window.alert("Failed to send the request: " + e.getMessage());
	    }
		
	}
	
	
	/**
	 * Add the form which collects the song Info to the page
	 */
	private void addForm() {
		Grid grid = new Grid(4,2);
		grid.setWidget(0, 0, bandLabel);
		grid.setWidget(0, 1, bandName);
		grid.setWidget(1, 0, songLabel);
		grid.setWidget(1, 1, songName);
		grid.setWidget(2, 0, priceLabel);
		grid.setWidget(2, 1, price);
		grid.setWidget(3, 1, submitButton);
	
	}
	
	
	/**
	 * Testing the doGet
	 * @param url
	 */
	private void httprequest (final String url){
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));

		try {
		 @SuppressWarnings("unused")
		Request request = builder.sendRequest(null, new RequestCallback(){
		   public void onError(Request request, Throwable exception) {
		      Window.alert("Error - " + exception.getMessage());     
		   }

		@Override
		public void onResponseReceived(Request request, Response response) {
			 if (200 == response.getStatusCode()) {
			  //   Window.alert("Success - " + response.getStatusCode());
			     parseJSONAndDisplay(response.getText());
			     nameField.setText(response.getText());
			     } else {
			     Window.alert("Error - " + response.getStatusCode());
			     }
		}       
		 });
		} catch (RequestException e) {
		Window.alert(" RequestException Error - " + e.getMessage());         
		}
	}
	
	
	private void getSearchResults(final String url,String searchData){
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(url));

		try {
		builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
		 @SuppressWarnings("unused")
		 
		Request request = builder.sendRequest("search="+searchData, new RequestCallback(){
		   public void onError(Request request, Throwable exception) {
		      Window.alert("Error - " + exception.getMessage());     
		   }

		@Override
		public void onResponseReceived(Request request, Response response) {
			 if (200 == response.getStatusCode()) {
				 parseSearchResultsAndDisplay(response.getText());
			  //   getSearchResults(url);
			     } else {
			     Window.alert("Error - " + response.getStatusCode());
			     }
		}       
		 });
		} catch (RequestException e) {
		Window.alert(" RequestException Error - " + e.getMessage());         
		}
	}
	
	
	
	protected void signUpRequest(String url, String postData) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);

	    try {
	    	builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
	        Request response = builder.sendRequest("signupDetails="+postData, new RequestCallback() {
	        public void onError(Request request, Throwable exception) {
	          Window.alert("error" );
	        }

	        public void onResponseReceived(Request request, Response response) {
	        	loginPage.setVisible(false);
	        	RootPanel.get().add(signUpSuccesLabel);
	        	
	        	
	        }
	      });
	    } catch (RequestException e) {
	      Window.alert("Failed to send the request: " + e.getMessage());
	    }
		
	}
	
	private void parseJSONAndDisplay(String jsonString){
		 JSONValue jsonValue = JSONParser.parseStrict(jsonString);
	     JSONArray jsonArray = jsonValue.isArray();
	     songTable.setText(0, 1, "Song");
		 songTable.setText(0, 2, "Band");
		 songTable.setText(0, 3, "Price");
		 addToCartButton = new Button[jsonArray.size()];
		 quantityTextBox = new TextBox[jsonArray.size()];
		 final FlexTable shoppingTable = new FlexTable();
	     for(int i=0;i<jsonArray.size();i++){
	    	 JSONValue song = jsonArray.get(i);
	    	 final String songName = song.isObject().get("songName").isString().stringValue();
	    	 String bandName = song.isObject().get("bandName").isString().stringValue();
	    	 double price = song.isObject().get("songPrice").isNumber().doubleValue();
	    	 final String priceValue = Double.toString(price);
	    	 addToCartButton[i] = new Button("Add To Cart");
	    	 addTheValuesToTable(songName,bandName,priceValue,i+1,addToCartButton[i]); 
	    	 /* If the add to cart button is cliecked add the song to
	    	  * the shopping cart
	    	  */
	    	 final int row = i;
	    	 addToCartButton[i].addClickHandler(new ClickHandler(){
	 	    	public void onClick(ClickEvent event){
	 	    		/*Check it the song already in the shopping cart
	 	    		 * if it exists just increase the quantity else add
	 	    		 */
	 	    		if(shoppingCart.contains(songName)){
	 	    			quantityTextBox[row].setValue("1");
		 	    		shoppingTable.setWidget(row,1,quantityTextBox[row]);
	 	    		}else{
	 	    			shoppingCart.add(songName);
		 	    		/*Print it in shoppinpanel*/
		 	    		shoppingTable.setText(row,0,songName);
		 	    	//	shoppingTable.setWidget(row, 1, quantityTextBox[row]);
		 	    		shoppingTable.setText(row, 2, priceValue);
		 	    		String currentCookie = Cookies.getCookie("Shopping Cart");
		 	    		System.out.println("shopping cart: " + shoppingCart);
		 	    		System.out.println("Cookie value: " + currentCookie);
		 	    		if(currentCookie==null) currentCookie="";
		 	    		{
			 	    		if(currentCookie.equals(shoppingCart.toString())){
			 	    			//saveCookie(shoppingCart.toString());
			 	    		}else{
			 	    			saveCookie(currentCookie.concat(shoppingCart.toString()));
			 	    		}
		 	    		}
	 	    		}
		    	}
	    	 });
	     }
	     shoppingCartPanel.add(shoppingTable);
	     /*Display the table add it to the songList Tab*/
    	 songListFlowPanel.add(songTable);

	}
	
	private void saveCookie(final String value){
		Date now = new Date();
		long nowLong = now.getTime();
		nowLong = nowLong + (1000 * 60 * 60 * 24 * 7);//seven days
		now.setTime(nowLong);
		Cookies.setCookie("Shopping Cart", value, now);
	}
	
	
	
	private void parseSearchResultsAndDisplay(String jsonString){
		 JSONValue jsonValue = JSONParser.parseStrict(jsonString);
	     JSONArray jsonArray = jsonValue.isArray();
	     searchTable.setText(0, 1, "Song");
	     searchTable.setText(0, 2, "Band");
	     searchTable.setText(0, 3, "Price");
	     
	     for(int i=0;i<jsonArray.size();i++){
	    	 JSONValue song = jsonArray.get(i);
	    	 String songName = song.isObject().get("songName").isString().stringValue();
	    	 String bandName = song.isObject().get("bandName").isString().stringValue();
	    	 double price = song.isObject().get("songPrice").isNumber().doubleValue();
	    	 String priceValue = Double.toString(price);
	    	 addTheValuesToSearchTable(songName,bandName,priceValue,i+1); 
	     }
	     /*Display the table add it to the songList Tab*/
   	 searchFlowPanel.add(searchTable);

	}

	private void addTheValuesToTable(String songName, String bandName,
			String price, int index, Button cartButton) {		    
			songTable.setWidget(index, 1, new Hyperlink(songName, songName));
			songTable.setText(index, 2, bandName);
			songTable.setText(index, 3, price);	
			songTable.setWidget(index, 4, cartButton);
			songTable.addClickHandler(new ClickHandler() {
			    public void onClick(ClickEvent event) {
			        Cell cell = songTable.getCellForEvent(event);
			        int receiverRowIndex = cell.getRowIndex(); 
			        System.out.println("Row: "+receiverRowIndex);
			        String songNameClicked = songTable.getText(receiverRowIndex, 1);
			        System.out.println("Row: "+receiverRowIndex);
			        /*Configure the Popup Panel as to what should be displayed
			         * In this case will display the song Name, year made, artist, Rating
			         */
			        JSONObject json = new JSONObject();
			        json.put("songName",new JSONString(songNameClicked));
			        getPopUpSongDetails(songDetailsURL, json.toString());
			        
			    }
			});
	}
	
	
	protected void getPopUpSongDetails(String url, String postData) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);

	    try {
	    	builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
	        Request response = builder.sendRequest("songName="+postData, new RequestCallback() {
	        public void onError(Request request, Throwable exception) {
	          Window.alert("error" );
	        }

	        public void onResponseReceived(Request request, Response response) {
	        	setUpPopUpPanel(response.getText());
	        	
	        	
	        }
	      });
	    } catch (RequestException e) {
	      Window.alert("Failed to send the request: " + e.getMessage());
	    }
		
	}
	
	private void setUpPopUpPanel(String jsonString){
		System.out.println(jsonString);
		JSONValue jsonvalue = JSONParser.parseStrict(jsonString);
		String songName = jsonvalue.isObject().get("songName").isString().stringValue();
		String artist = jsonvalue.isObject().get("artist").isString().stringValue();
		String genre = jsonvalue.isObject().get("genre").isString().stringValue();
		double ratingDouble = jsonvalue.isObject().get("rating").isNumber().doubleValue();
		int rating = (int)ratingDouble;
		double yearDouble = jsonvalue.isObject().get("year").isNumber().doubleValue();
		int year = (int)yearDouble;
		
		PopupPanel songDetailsPopUp = new PopupPanel();
		Grid popupGrid = new Grid(5,2);
		Label songLabel = new Label("Song Name");
		Label artistLabel = new Label("Artist");
		Label genreLabel = new Label("Genre");
		Label ratingLabel = new Label("Rating");
		Label yearLabel = new Label("Year");
		
		popupGrid.setWidget(0,0,songLabel);
		popupGrid.setText(0, 1, songName);
		popupGrid.setWidget(1,0,artistLabel);
		popupGrid.setText(1, 1, artist);
		popupGrid.setWidget(2,0,genreLabel);
		popupGrid.setText(2, 1, genre);
		popupGrid.setWidget(3,0,ratingLabel);
		popupGrid.setText(3, 1, Integer.toString(rating));
		popupGrid.setWidget(4,0,yearLabel);
		popupGrid.setText(4, 1, Integer.toString(year));
		songDetailsPopUp.add(popupGrid);
		songDetailsPopUp.center();
		songDetailsPopUp.show();
		
			
	}
	
	private void addTheValuesToSearchTable(String songName, String bandName,
			String price, int index) {
			searchTable.setText(index, 1, songName);
			searchTable.setText(index, 2, bandName);
			searchTable.setText(index, 3, price);		
	}
	
	private void validateUser(final String url,String userData){
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(url));

		try {
		builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
		 @SuppressWarnings("unused")
		 
		Request request = builder.sendRequest("user="+userData, new RequestCallback(){
		   public void onError(Request request, Throwable exception) {
		      Window.alert("Error - " + exception.getMessage());     
		   }

		@Override
		public void onResponseReceived(Request request, Response response) {
			 if (200 == response.getStatusCode()) {
				 takeActionOnValidation(response.getText());
			  
			     } else {
			     Window.alert("Error - " + response.getStatusCode());
			     }
		}       
		 });
		} catch (RequestException e) {
		Window.alert(" RequestException Error - " + e.getMessage());         
		}
	}
	
	public void takeActionOnValidation(String jsonString){
		JSONValue jsonvalue = JSONParser.parseStrict(jsonString);
		String validate = jsonvalue.isObject().get("valid").isString().stringValue();
		if(validate.equals("True")){
			signUpGrid.setVisible(false);
			
		}
	}
	
}
