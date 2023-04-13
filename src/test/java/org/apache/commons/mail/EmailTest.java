package org.apache.commons.mail;

//imports
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class EmailTest {

	private static final String[] TEST_EMAILS = {"ab@bc.com", "a.b@c.org", "abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd"};
	
	private EmailConcrete email;		//Concrete Email class for testing
	
	//Before case that sets up your emailTest class to EmailConcrete
	@Before
	public void setUpEmailTest() throws Exception {
		email = new EmailConcrete();
	}
	
	//After case that tears down your emailTest class
	@After
	public void TearDownEmailTest() throws Exception {}
	
	//asserts addBcc to getBccAddresses
	//Success case of addBcc
	@Test
	public void testAddBcc() throws Exception {
		for (String address : TEST_EMAILS)
			email.addBcc(address);
		
		assertEquals(3, email.getBccAddresses().size());
	}
	
	//asserts addCC to getCcAddresses
	//Success case of addCC
	@Test
	public void testAddCc() throws Exception {
		for (String address : TEST_EMAILS)
			email.addCc(address);
		
		assertEquals(3, email.getCcAddresses().size());
	}
	
	//asserts addReplyTo to ReplyToAddresses
	//Success case of AddReplyTo
	@Test
	public void testAddReplyTo() throws Exception {
		for (String address : TEST_EMAILS)
			email.addReplyTo(address);
		
		assertEquals(3, email.getReplyToAddresses().size());
		
	}
	
	//asserts adding a header to getHeaders via name and value
	//Success case of AddHeader
	@Test
	public void testAddHeader() throws Exception {
		email.addHeader("Name", "Value");
		assertEquals(1, email.getHeaders().size());
	}
	
	//asserts adding a header to getHeaders via value and null name
	//Fail case of AddHeader when Name is null
	@Test(expected=IllegalArgumentException.class)
	public void testAddHeaderNullName() throws Exception {
		email.addHeader(null, "Value");
		assertEquals(1, email.getHeaders().size());
	}
	
	//asserts adding a header to getHeaders via name and null value
	//Fail case of AddHeader when Value is null
	@Test(expected=IllegalArgumentException.class)
	public void testAddHeaderNullValue() throws Exception {
		email.addHeader("Name", null);
		assertEquals(1, email.getHeaders().size());
	}

	//asserts setFrom to FromAddress
	//Success case of setFrom
	@Test
	public void testSetFrom() throws Exception {
		InternetAddress fromAddressExpected = new InternetAddress("abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd");
		for (String address : TEST_EMAILS) {
			email.setFrom(address);
		}
		assertEquals(email.getFromAddress(), fromAddressExpected);
	}

	//asserts an expected time to getSentDate
	//Success case of getting a Sent Date
	@Test
	public void testGetSentDate() throws Exception {
		Date DateExpected = new Date();
		DateExpected.setTime(1440);
		email.setSentDate(DateExpected);
		assertEquals(DateExpected, email.getSentDate());
	}
	
	//asserts a Host Name to getHostName with a session
	//Success case of getting a Host Name with a session
	@Test
	public void testGetHostName() throws Exception{
		Properties propsExpected = new Properties();
		Session sessionExpected = Session.getInstance(propsExpected);
		email.setMailSession(sessionExpected);
		assertEquals(null, email.getHostName());
	}
	
	//asserts a Host Name to getHostName without a session
	//Success case of getting a Host Name without a session
	@Test
	public void testGetHostNameNullSession() throws Exception{
		email.setHostName("NullSession");
		assertEquals("NullSession", email.getHostName());
	}
	
	//asserts a Mail Session to a property of MAIL_SMTP via SMTP Port
	//Success case of getting a mail session
	@Test
	public void testGetMailSession() throws Exception {
		email.setSmtpPort(1111);
		email.setSslSmtpPort(" ");
		email.setHostName("Host");
		email.setSSLOnConnect(true);
		Session sessionExpected = email.getMailSession();;
		assertEquals(sessionExpected.getProperty("MAIL_SMTP"), email.getSmtpPort());
	}
	
	//asserts Socket Connection Timer equal to 500
	//Success case of timing out a socket
	@Test
	public void testGetSocketConnectionTimeout() throws Exception {
		email.setSocketConnectionTimeout(500);		
		assertEquals(500, email.getSocketConnectionTimeout());
	}
	
	//asserts MimeMessage equal to the subject
	//Success case of building a mime message
	@Test
	public void testBuildMimeMessage() throws Exception {

		Properties propsExpected = new Properties();
		Session sessionExpected = Session.getInstance(propsExpected);
		Object ObjExpected = new Object();
		
		for (String address : TEST_EMAILS) {
			email.setMailSession(sessionExpected);
			email.setSubject("Subject");
			email.addCc(address);
			email.addBcc(address);
			email.setFrom(address);
			email.addTo(address);
			email.addReplyTo(address);
			email.addHeader("Name", "value");
			email.setContent(ObjExpected, "String");
			email.buildMimeMessage();
		}		
		MimeMessage msg = email.getMimeMessage();
		assertEquals(msg.getSubject(), "Subject");

	}
	
	//Error Test
	//Tests Exception error for Email class when attempting to add POP to Email
	@Test(expected=EmailException.class)
	public void testBuildMimeMessageEmailException() throws Exception {

		Properties propsExpected = new Properties();
		Session sessionExpected = Session.getInstance(propsExpected);
		Object ObjExpected = new Object();
		
		for (String address : TEST_EMAILS) {
			email.setMailSession(sessionExpected);
			email.setSubject("Subject");
			email.addCc(address);
			email.addBcc(address);
			email.setFrom(address);
			email.addTo(address);
			email.addReplyTo(address);
			email.addHeader("Name", "value");
			email.setContent(ObjExpected, "String");
			email.setPopBeforeSmtp(true, "PopHost", "PopUser", "Pop123456");
			email.buildMimeMessage();
		}	
	}
	
	//Error Test
	//Tests Exception error for Illegal State is thrown when trying to build another MimeMessage
	@Test(expected=IllegalStateException.class)
	public void testBuildMimeMessageIllegalStateException() throws Exception {
		testBuildMimeMessage();
		email.buildMimeMessage();
	}
}
