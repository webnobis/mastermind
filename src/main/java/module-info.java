module com.webnobis.mastermind.Mastermind {

	requires java.base;

	requires org.slf4j;

	requires java.xml.bind;

	requires javafx.base;
	requires javafx.controls;
	requires transitive javafx.graphics;

	exports com.webnobis.mastermind;
	exports com.webnobis.mastermind.service;

	opens com.webnobis.mastermind;
	opens com.webnobis.mastermind.model;
	opens com.webnobis.mastermind.service;
	opens com.webnobis.mastermind.view;

}