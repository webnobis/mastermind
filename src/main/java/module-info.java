module com.webnobis.mastermind.Mastermind {
	
	requires java.base;
	
	requires org.slf4j;
	
	requires java.xml.bind;
	
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.swing;
	
	exports com.webnobis.mastermind.service;
	
	opens com.webnobis.mastermind.model to java.xml.bind;
	
}