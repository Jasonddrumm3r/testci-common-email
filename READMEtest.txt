EmailConcrete
-----

Getting started:

1. Make a public emailConcrete class to set up variables for the Email Test

2. Make 3 functions for different purposes
	a. SetMsg Email class to get your messages from the email class that returns a null
	b. getHeaders Map class to get the map for your headers from the email class
	c. getContenttype String class to get your content types from the email class
	
EmailTest
-----

Getting started:

1. Get your imports

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

2. Get your private variables
	a. Make a private static final String[] to test your email strings
	private static final String[] TEST_EMAILS = {"ab@bc.com", "a.b@c.org", "abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd"};
	
	b. Make a private variable that gets your Concrete Email class for testing
	private EmailConcrete email;		//Concrete Email class for testing
	
3. @Before
	a. Make a Before case that sets up your emailTest class to EmailConcrete
	public void setUpEmailTest() throws Exception {
		email = new EmailConcrete();
	}
	
4. @After
	a. Make a empty After case that tears down your emailTest class
	public void TearDownEmailTest() throws Exception {}
	
For steps 5-14, these are your @Test Methods you will use. Remember for all functions function throw an exception and make sure they are public voids!!! 

5. testAddBcc
	a. Make a success case Function of the addBcc function that asserts addBcc to getBccAddresses
	public void testAddBcc() throws Exception {
		for (String address : TEST_EMAILS)
			email.addBcc(address);
		
		assertEquals(3, email.getBccAddresses().size());
	}
	b. Coverage: addBcc(String) = 100%

6. testAddCc
	a. Make a success case function of the addCc function that asserts addCc to getCcAddresses
	public void testAddCc() throws Exception {
		for (String address : TEST_EMAILS)
			email.addCc(address);
		
		assertEquals(3, email.getCcAddresses().size());
	}
	b. Coverage: addCC(String) = 100%
	
7. testAddReplyTo
	a. Make a success case function of the addReplyTo function that asserts addReplyTo to ReplyToAddresses
	public void testAddReplyTo() throws Exception {
		for (String address : TEST_EMAILS)
			email.addReplyTo(address);
		
		assertEquals(3, email.getReplyToAddresses().size());
		
	}
	b. Coverage: addReplyTo(String, String) = 100%
	
8. testAddHeader
	a. Make a success case function of the addHeader function that asserts adding a header to getHeaders via name and value
	public void testAddHeader() throws Exception {
		email.addHeader("Name", "Value");
		assertEquals(1, email.getHeaders().size());
	}
	
	Coverage here is not complete, so you must make two additional cases that account for null parameters
	
	b. Make a fail case function of the addHeader function accounting for an IllegalArgumentException error when Name is null that asserts adding a header to getHeaders
	@Test(expected=IllegalArgumentException.class)
	public void testAddHeaderNullName() throws Exception {
		email.addHeader(null, "Value");
		assertEquals(1, email.getHeaders().size());
	}
	c. Make a fail case function of the addHeader function accounting for an IllegalArgumentException error when Value is null that asserts adding a header to getHeaders
	public void testAddHeaderNullValue() throws Exception {
		email.addHeader("Name", null);
		assertEquals(1, email.getHeaders().size());
	}
	
	d. Coverage: addHeader(String, String) = 100%
	
9. testSetFrom
	a. Make a success case function of the setFrom function that asserts setFrom to FromAddress
	public void testSetFrom() throws Exception {
		InternetAddress fromAddressExpected = new InternetAddress("abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd");
		for (String address : TEST_EMAILS) {
			email.setFrom(address);
		}
		assertEquals(email.getFromAddress(), fromAddressExpected);
	}
	b. Coverage: setFrom(String) = 100%
	
10. testGetSentDate
	a. Make a success case function of the GetSentDate function that asserts an expected time to getSentDate
	public void testGetSentDate() throws Exception {
		Date DateExpected = new Date();
		DateExpected.setTime(1440);
		email.setSentDate(DateExpected);
		assertEquals(DateExpected, email.getSentDate());
	}
	b. Coverage: getSentDate() = 100%
	
11. testGetHostName
	a. Make a success case function of the GetHostName function that asserts a Host Name to getHostName with a session
	public void testGetHostName() throws Exception{
		Properties propsExpected = new Properties();
		Session sessionExpected = Session.getInstance(propsExpected);
		email.setMailSession(sessionExpected);
		assertEquals(null, email.getHostName());
	}
	
	Coverage here is not complete, so you must make an additional case that account for no session
	
	b. Make a success case function of the GetHostName function that asserts a Host Name to getHostName without a session
	public void testGetHostNameNullSession() throws Exception{
		email.setHostName("NullSession");
		assertEquals("NullSession", email.getHostName());
	}
	c. Coverage: getHostName() = 88.2%
	
12. testGetMailSession
	a. Make a success case function of the GetMailSession function that asserts a Mail Session to a property of MAIL_SMTP via SMTP Port
	public void testGetMailSession() throws Exception {
		email.setSmtpPort(1111);
		email.setSslSmtpPort(" ");
		email.setHostName("Host");
		email.setSSLOnConnect(true);
		Session sessionExpected = email.getMailSession();;
		assertEquals(sessionExpected.getProperty("MAIL_SMTP"), email.getSmtpPort());
	}
	b. Coverage: getMailSession() = 78.9%
	
13. testGetSocketConnectionTimeout
	a. Make a success case function of the GetSocketConnectionTimeout function that asserts Socket Connection Timer equal to 500
	public void testGetSocketConnectionTimeout() throws Exception {
		email.setSocketConnectionTimeout(500);		
		assertEquals(500, email.getSocketConnectionTimeout());
	}
	b. Coverage: getSocketConnectionTimeout() = 100%
	
14. buildMimeMessage
	a. Make a success case function of the buildMimeMessage function that asserts MimeMessage equal to the subject
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
	
	Coverage here is not complete, so you must make two additional cases. One for attempting to add POP and another for attempting to build an additional Mime Message
	
	b. Make a fail case function of the buildMimemessage function accounting for a Class Exception error when attempting to add POP to Email and doesn't assert anything
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
	c. Make a fail case function of the buildMimemessage accounting for a IllegalStateException error when attempting to build another mime message and doesn't assert anything
	@Test(expected=IllegalStateException.class)
	public void testBuildMimeMessageIllegalStateException() throws Exception {
		testBuildMimeMessage();
		email.buildMimeMessage();
	}
	d. Coverage: buildMimeMessage() = 71.2%
	
	
After that, it's gg (Good Game!)