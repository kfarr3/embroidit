# Embroid-It

Embroid-It is an open source editing software for use in machine embroidery design. The application was started by a group of undergraduate students at Eastern Washington University as a senior capstone. Written in Java, the application uses the JavaFX API for shape representation, and has basic support for the PES file format.

***

### Development

The project is open source and provided under the BDS 3-Clause License. We encourage developers to use, and modify the project as they see fit.

#### Project Setup

The Embroid-It front end is constructed using the Netbeans IDE, and the JavaFX Scenebuilder 2.0 Visual Layout Tool. Links to these tools and how to get started with them and JavaFX can be found at the links below.

Downloads:
* [JavaFX Scene Builder](http://www.oracle.com/technetwork/java/javase/downloads/javafxscenebuilder-1x-archive-2199384.html)
* [NetBeans](https://netbeans.org/downloads/) (Make sure the version you download includes JavaFX)

Tutorials/Documentation:
* [Oracle JavaFX Documentation](http://docs.oracle.com/javase/8/javase-clienttechnologies.htm)
* [Calculator Tutorial](https://blog.idrsolutions.com/2015/05/how-to-create-a-javafx-gui-using-scene-builder-in-netbeans/)

#### Using the Project Files

There are two parts to the project. The Embroid-It GUI, and the EmbLib library project. You can run the application from the Embroid-It project inside Netbeans, or from the Embroid-It dist folder directly. The application quick start guide can be reached from the help menu. (**Help -> Getting Started**)

#### Linking EmbLib to Embroid-It

Upon initially loading the Embroid-It project it may ask to have the link the to EmbLib library resolved because it doesn't know where it's located. This can be found in the **dist** folder within the EmbLib project directory.

#### EmbLib Documentation

the EmbLib library contains the basic structures necessary for representing a pattern. It also contains collections of algorithms for use in fill stitching and file management. All library methods are explained in the provided [Javadocs](https://github.com/Embroid-It/embroidit/tree/master/EmbLib/dist). Once cloned, double click the index.html file within the Javadoc directory inside of the dist folder.

***

#### Version

Current Version - 1.0

#### Authors

Project sponsor:

*  [Ken Farr](https://github.com/kfarr3)

Contributors:

* [Christopher Park](https://github.com/ParkChristopher) - EmbLib framework library development & Documenation.
* [Trae Rawls](https://github.com/traelrawls)       - JavaFX Scenebuilder event controller & user interaction development.
* [Nate Owens](https://github.com/nateowens)       - Scenebuilder GUI visual design & Documentation 

#### License

Embroid-It is provided under the BSD 3-Clause License. Please see the [License](https://github.com/Embroid-It/embroidit/blob/master/LICENSE) file for more information.

#### Acknowledgements

Thanks goes out to the Embroidermodder open source project and its developers for code used in the development and design of the project format and PES file support.

Be sure to check out their project!

[Embroidermodder](https://github.com/Embroidermodder/Embroidermodder)
