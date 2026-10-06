package com.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.openqa.grid.web.Hub;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.generics.CustomReporter;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.Attachment;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.FileAttachment;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.models.Recipient;
import com.microsoft.graph.models.UserSendMailParameterSet;
import com.microsoft.graph.requests.AttachmentCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;

import okhttp3.Request;


public class TestLauncher {
	private static Fillo fillo = new Fillo();
	private static Connection connection = null;
	private static Recordset recordset = null;
	private static String ReferencrQuery;
	private static Properties objProperties = null;
	private static Hub objHub = null;
	private static String CLIENT_ID;
	private static String TENANT_ID;
	private static String CLIENT_SECRET;
	private static String USER_EMAIL;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		//moduleUpdate();

		startDBConnection();
		TestNG testNGReference = new TestNG();
		testNGReference.addListener(new CustomReporter());
		List<XmlSuite> suites = new ArrayList<>();
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
				List<XmlClass> classes = new ArrayList<>();
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
		if (objHub != null) {
			objHub.stop();
		}

		System.out.println("End Of the process");

		//changeColor();
		JarRunner();
		if("Yes".equals(getProperty("EmailFlag"))){
		multipleMailSend(getProperty("mailTo"));
		}
	}

	public static String changeColor() {
		File dir = new File(getProperty("emailReportPath"));
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
		TreeSet<String> classPath = new TreeSet<>();
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

		for (String element : splittoMail) {
			sendEmailViaGraphAPI(element);
		}

	}

	private static void sendEmailViaGraphAPI(String toEmailID) {
		String filePath= changeColor();
		String filename = filePath+"\\"+"emailable-report.html";
		try {
		CLIENT_ID=getProperty("CLIENT_ID");
		TENANT_ID=getProperty("TENANT_ID");
		CLIENT_SECRET=getProperty("CLIENT_SECRET");
		USER_EMAIL=getProperty("USER_EMAIL");

		 // Authenticate using Client Secret Credentials
        ClientSecretCredential credential = new ClientSecretCredentialBuilder()
                .clientId(CLIENT_ID)
                .tenantId(TENANT_ID)
                .clientSecret(CLIENT_SECRET)
                .build();

        List<String> scopes = Collections.singletonList("https://graph.microsoft.com/.default");
        TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(scopes, credential);

        GraphServiceClient<Request> graphClient = GraphServiceClient
                .builder()
                .authenticationProvider(authProvider)
                .buildClient();

        // Create the attachment


			byte[] 	fileBytes = Files.readAllBytes(Paths.get(filename));
			FileAttachment attachment = new FileAttachment();
	        attachment.oDataType = "#microsoft.graph.fileAttachment";
	        attachment.name = "emailable-report.html";
	        attachment.contentBytes = fileBytes;
	        attachment.contentType = "text/plain";

	        // Create the email message
	        Message message = new Message();
	        message.subject = getProperty("EmailSubject");
	        ItemBody body = new ItemBody();
	        body.contentType = BodyType.TEXT;
	        body.content = getProperty("MessageBody");
	        message.body = body;

	        EmailAddress recipientAddress = new EmailAddress();
	        recipientAddress.address = toEmailID;
	        Recipient recipient = new Recipient();
	        recipient.emailAddress = recipientAddress;
	        message.toRecipients = Collections.singletonList(recipient);

	        // Attach the file correctly
	        List<Attachment> attachmentList = new ArrayList<>();
	        attachmentList.add(attachment);
	        message.attachments = new AttachmentCollectionPage(attachmentList, null);

	        // Send the email correctly
	        graphClient.users(USER_EMAIL)
	        .sendMail(UserSendMailParameterSet.newBuilder()
	                .withMessage(message)
	                .withSaveToSentItems(true)
	                .build())
	        .buildRequest()
	        .post();

	        System.out.println("Email sent successfully!");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public static void JarRunner() {
		try {
		// Path to the JAR file
		String jarPath = System.getProperty("user.dir")+"//"+"Failure_Pattern.jar";

		// Build the command
		ProcessBuilder pb = new ProcessBuilder("java", "-jar", jarPath);
		pb.redirectErrorStream(true); // Merge stderr with stdout

		// Start the process
		Process process = pb.start();

		// Read the output from the JAR process
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
		System.out.println(line);
		}

		// Wait for the process to complete
		int exitCode = process.waitFor();
		System.out.println("✅ JAR finished with exit code: " + exitCode);

		} catch (Exception e) {
		e.printStackTrace();
		}

		}

	public static void moduleUpdate() {
		String excelPath = System.getProperty("user.dir")+ "\\"+getProperty("testSuitePath")+"";
		String driverSheet = "DriverSheet"; // exact sheet name
		String scenarioSheet = "TestScenarios"; // exact sheet name
		String driverColumn = "ModuleName"; // column in driver sheet
		String scenarioColumn = "Module"; // column in scenario sheet
		String flagColumn = "ExecuteTestScenario";// flag column in scenario sheet

		Fillo fillo = new Fillo();
		Connection conn = null;
		try {
		conn = fillo.getConnection(excelPath);

		// 1) Reset all flags to 'No' once
		String resetQuery = "UPDATE " + scenarioSheet + " SET " + flagColumn + "='No'";
		System.out.println("Executing: " + resetQuery);
		conn.executeUpdate(resetQuery);

		// 2) Read unique modules from driver sheet (trimmed)
		String selectDriver = "SELECT " + driverColumn + " FROM " + driverSheet + " where Flag='Yes'";
		Recordset rs = conn.executeQuery(selectDriver);

		Set<String> modules = new HashSet<>();
		while (rs.next()) {
		String m = rs.getField(driverColumn);
		if (m != null) {
		m = m.trim();
		if (!m.isEmpty()) {
		modules.add(m);
		}
		}
		}
		rs.close();

		// 3) Update per module (one UPDATE per module). This avoids Fillo parser issues with big OR lists
		if (modules.isEmpty()) {
		System.out.println("No modules found in driver sheet. Nothing to update.");
		} else {
		for (String module : modules) {
		// Escape any single quotes in module name for SQL safety
		String safeModule = module.replace("'", "''");

		// Build and execute update per module
		String q = "UPDATE " + scenarioSheet + " SET " + flagColumn + "='Yes' WHERE "
		+ scenarioColumn + "='" + safeModule + "'";
		System.out.println("Executing: " + q);
		conn.executeUpdate(q);
		}
		}

		System.out.println("Completed module updates");

		} catch (FilloException fe) {
		System.err.println("Fillo error: " + fe.getMessage());
		fe.printStackTrace();
		} finally {
		if (conn != null) {
			conn.close();
		}
		}
	}

}


