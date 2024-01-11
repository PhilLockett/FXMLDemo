# FXMLDemo
'FXMLDemo' is a JavaFX application 'framework' that uses Maven, FXML and CSS.

## Overview
This project has been set up as a Maven project that uses JavaFX, FXML and 
CSS to render the GUI. Maven can be run from the command line as shown below.
Maven resolves dependencies and builds the application independently of an IDE.

The intention of this application is to provide a 'framework' from which other
applications can be derived. It includes a number of different controls as 
examples that can be cloned and modified as needed.

## Dependencies
'FXMLDemo' is dependent on the following:

  * Java 15.0.1
  * Apache Maven 3.6.3

The code is structured as a standard Maven project which requires Maven and a 
JDK to be installed. A quick web search will help, alternatively
[Oracle](https://www.java.com/en/download/) and 
[Apache](https://maven.apache.org/install.html) should guide you through the
install.

Also [OpenJFX](https://openjfx.io/openjfx-docs/) can help set up your 
favourite IDE to be JavaFX compatible, however, Maven does not require this.

## Cloning and Running
The following commands clone and execute the code:

	git clone https://github.com/PhilLockett/FXMLDemo.git
	cd FXMLDemo/
	mvn clean javafx:run

## Customization
The GUI layout can be modified as desired by editing the 'primary.fxml' file. 
The SceneBuilder application makes editing the layout easier than modifiying 
'primary.fxml' directly.

FXML also uses cascading style sheets for the presentation. To change the 
colours and fonts used, edit the 'application.css' file.

## Points of interest
This code has the following points of interest:

  * FXMLDemo is a Maven project that uses JavaFX.
  * FXMLDemo provides multiple examples of different types of JavaFX controls.
  * FXMLDemo is styled with CSS.
  * FXMLDemo is structured as an MVC project (FXML being the Video component).
  * Multi stage initialization minimizes the need for null checks. 
  * Data persistence is provided by the Serializable DataStore object.
  * The GUI is implemented in FXML using SceneBuilder.
