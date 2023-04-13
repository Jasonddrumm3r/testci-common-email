package org.apache.commons.mail;

import java.util.Map;


//emailConcrete class to set up variables for the Email Test
public class EmailConcrete extends Email{

	//gets messages for email
	public Email setMsg(String msg) throws EmailException {
		return null;
	}
	
	//gets Map for your headers
	public Map<String, String> getHeaders() {
		return this.headers;
	}
	
	//gets your content type
	public String getContentType() {
		return this.contentType;
	}
}
