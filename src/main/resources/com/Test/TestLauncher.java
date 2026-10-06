package com.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.openqa.grid.web.Hub;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.generics.CustomReporter;

public class TestLauncher {
	private static Fillo fillo = new Fillo();
	private static Connection connection = null;
	private static Recordset recordset = null;
	private static String ReferencrQuery;
	private static Properties objProperties = null;
	private static Hub objHub = null;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		startDBConnection();
		TestNG testNGReference = new TestNG();
		testNGReference.addListener(new CustomReporter());
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		XmlSuite suite = new XmlSuite();
		suite.setName(objProperties.getProperty("suiteName"));
		suite.setThreadCount(Integer.parseInt(objProperties.getProperty("ThreadCount")));
		String para=objProperties.getProperty("ParallelTestSuites");
		if (para.equals("Yes")) {
			suite.setParallel(ParallelMode.TESTS);
		}
		else {
			
			suite.setParallel(ParallelMode.FALSE);
		}
		
		
		setThreadCountAndStartHub(suite);
		String strQueryclassPathList = "Select * from TestScenarios where ExecuteTestScenario='Yes'";
		TreeSet<String> classPathList = fireQueryForSpecificField(strQueryclassPathList, "TestClassPath");
		for (String string : classPathList) {
			String strQueryTestClassName = strQueryclassPathList + " and TestClassPath='" + string + "'";
			TreeSet<String> testClassName = fireQueryForSpecificField(strQueryTestClassName, "TestClassName");
			for (String string2 : testClassName) {
				XmlTest test = new XmlTest(suite);
				test.setName(string2);
				List<XmlClass> classes = new ArrayList<XmlClass>();
				test.setName(string2);				
				XmlClass testClass = new XmlClass(string + '.' + string2);
				ReferencrQuery = strQueryTestClassName + " and TestClassName='" + string2 + "'";
				TreeSet<String> methodName = fireQueryForSpecificField(ReferencrQuery, "Reference");
				List<XmlInclude> includedMethods = new ArrayList<>();
				for (String string3 : methodName) {
					includedMethods.add(new XmlInclude(string3));
				}
				testClass.setIncludedMethods(includedMethods);
				classes.add(testClass);
				test.setXmlClasses(classes);
			}
			suites.add(suite);
		}
		recordset.close();
		connection.close();
		testNGReference.setXmlSuites(suites);
		try {
			testNGReference.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (objHub != null)
			objHub.stop();

		System.out.println("End Of the process");

		//changeColor();
		
		if("Yes".equals(getProperty("EmailFlag"))){
		multipleMailSend(getProperty("mailTo"));
		}
	}

	public static String changeColor() {
		File dir = new File("D:\\TestAutomation\\Report");
		File max = null;
		for (File file : dir.listFiles()) {
			if (file.isDirectory() && (max == null || max.lastModified() < file.lastModified())) {
				max = file;
			}

		}

		File fileToBeModified = new File(max.toString() + "\\testng.css");

		String oldContent = "";

		BufferedReader reader = null;

		FileWriter writer = null;
		String oldString = objProperties.getProperty("TestngoldString");// "#DD0000";
		String newString = objProperties.getProperty("TestngnewString");// "#DD0000";

		try {
			reader = new BufferedReader(new FileReader(fileToBeModified));

			// Reading all the lines of input text file into oldContent

			String line = reader.readLine();

			while (line != null) {
				oldContent = oldContent + line + System.lineSeparator();

				line = reader.readLine();
			}

			// Replacing oldString with newString in the oldContent

			String newContent = oldContent.replaceAll(oldString, newString);

			// Rewriting the input text file with newContent

			writer = new FileWriter(fileToBeModified);

			writer.write(newContent);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// Closing the resources

				reader.close();

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println(max);
		return max.toString();
	}

	public static void sendEmailableReport() {

	}

	public static TreeSet<String> fireQueryForSpecificField(String strQuery, String FieldName) {
		TreeSet<String> classPath = new TreeSet<String>();
		try {
			recordset = connection.executeQuery(strQuery);
			while (recordset.next()) {
				classPath.add(recordset.getField(FieldName));
			}
		} catch (FilloException e) {
			e.printStackTrace();
		}
		return classPath;
	}

	private static void startDBConnection() {
		try {
			connection = fillo.getConnection(getProperty("testSuitePath"));
		} catch (FilloException e) {
			e.printStackTrace();
		}
	}

	private static String getProperty(String testSuiteName) {
		if (objProperties == null) {
			try {
				objProperties = new Properties();
				objProperties.load(
						new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties"));
			} catch (Exception var2) {
				var2.printStackTrace();
			}
		}
		return objProperties.getProperty(testSuiteName);
	}

	private static void setThreadCountAndStartHub(XmlSuite suite) {
		if (objProperties.getProperty("ExecutionMode").trim().equalsIgnoreCase("remote")) {
			try {
				Recordset retue = connection
						.executeQuery("Select * from TestScenarios where ExecuteTestScenario='Yes'");
				suite.setThreadCount(retue.getCount());
			} catch (FilloException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private static void multipleMailSend(String toMail) {
		String [] splittoMail = toMail.split(",");
		
		for(int i=0;i<splittoMail.length;i++) {
			
			sendEmail(splittoMail[0]);
		}
		
	}
	private static void sendEmail(String recipient) {
		
	      String to = recipient;

	      
	      String from =getProperty("mailFrom");

	      final String username = getProperty("UserName");//change accordingly
	      final String password = getProperty("Password");//change accordingly

	     
	      String host = getProperty("Host");;

	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "465");
	     // props.put("mail.debug", "true");
	      props.put("mail.smtp.ssl.enable","true");

	      
	      Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(username, password);
	            }
	         });

	      try {
	         
	         Message message = new MimeMessage(session);
	         
	         message.setFrom(new InternetAddress(from));
	        
	         message.setRecipients(Message.RecipientType.TO,
	            InternetAddress.parse(to));
	         
	         message.setSubject(getProperty("EmailSubject"));
	        
	         BodyPart messageBodyPart = new MimeBodyPart();
	         
	         messageBodyPart.setText(getProperty("MessageBody"));
	         
	         Multipart multipart = new MimeMultipart();
	         
	         multipart.addBodyPart(messageBodyPart);

	         // Part two is attachment
	         messageBodyPart = new MimeBodyPart();
	         String filePath= changeColor();
	         String filename = filePath+"\\"+"emailable-report.html";
	         DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         //messageBodyPart.getFileName();
	         messageBodyPart.setFileName(filename);
	         messageBodyPart.setFileName(new File(filename).getName());        
				
	         
	         multipart.addBodyPart(messageBodyPart);
	         
	         message.setContent(multipart);
	         
	         Transport.send(message);

	         System.out.println("Sent message successfully....");
	  
	      } catch (MessagingException e) {
	         throw new RuntimeException(e);
	      }
	   }
	}
	

