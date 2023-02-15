# <p align="center">Currency Converter</p>
Application of a currency converter which uses <a href="http://api.nbp.pl/">NBP API</a> to make a convertion between PLN and any other available currency.

## Table of Content
- [Technologies](#technologies-used)
- [How to use](#how-to-use)
- [Demonstration](#demonstration)
- [Additional](#additional)

## Technologies Used
<br>
<p align="center">
 <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white">
 <img src="https://img.shields.io/badge/spring-%23ED8B00.svg?style=for-the-badge&logo=spring&logoColor=white">
 <img src="https://img.shields.io/badge/javascript-%23ED8B00.svg?style=for-the-badge&logo=javascript&logoColor=white">
 <img src="https://img.shields.io/badge/html5-%23ED8B00.svg?style=for-the-badge&logo=html5&logoColor=white">
 <img src="https://img.shields.io/badge/css3-%23ED8B00.svg?style=for-the-badge&logo=css3&logoColor=white">
 <img src="https://img.shields.io/badge/bootstrap-%23ED8B00.svg?style=for-the-badge&logo=bootstrap&logoColor=white">
 <img src="https://img.shields.io/badge/Thymeleaf-%23ED8B00.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white">
</p>

## How to Use
IDE with configured <a href="https://projectlombok.org/">Lombok</a> and <a href="https://www.oracle.com/java/technologies/java-se-glance.html">JDK</a> version >=11 are required in order to launch this project. <a href="https://www.eclipse.org/downloads/packages/release/kepler/sr1/eclipse-ide-java-ee-developers">Eclipse IDE for Java EE Developers</a> has been used.

Download the whole project as a **.zip** file and extract it to any directory. After that, import this project using Maven into your IDE marking **converter** directory as the deepest one on the path, since it constains **pom.xml** file.

In Eclipse it can be imported via: *File->Import->Maven->Existing Maven Projects*

After it is imported, simply launch the app by running **ConverterApplication.java** as a Java Application. Application as well as Tomcat server (http port 8080, make sure it is not occupied by any other service!!!) should be launched and the following text should appear in the console:

![image](https://user-images.githubusercontent.com/106389146/219123680-f294942a-70d3-43b2-a258-6c93a97eb5a4.png)

In order to use the application's functionalities, open your web browser and in the address bar type: **localhost:8080**.
You should be redirected to **/calculator** path having the following interface:

![image](https://user-images.githubusercontent.com/106389146/219124624-4b5d1b3b-77b9-42b1-91ac-37fe317595eb.png)

## Demonstration

## Additional
