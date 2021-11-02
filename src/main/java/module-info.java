module com.webnobis.mastermind {

	requires java.base;

	requires org.slf4j;

	requires java.xml.bind;

	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;

	exports com.webnobis.mastermind;
	exports com.webnobis.mastermind.service;
	
	opens com.webnobis.mastermind.model to java.xml.bind;
	opens com.webnobis.mastermind.view to javafx.graphics;

}