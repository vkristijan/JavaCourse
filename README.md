# JavaCourse
Homeworks from the Introduction to Java programming language course.

## Homework 1 - Introduction to Java
Solved six simple problems using Java.

## Homework 2 - Introduction to OOP - Classes
Created a class that is used as a collection interface and a linked list implementation. 
Created a stack from that linked list using the **Adapter design pattern**.

## Homework 3 - Lexer and Parser
* Created a simple lexer with unit test as an example.
* Created a **lexer and parser for a script language** called SmartScript

## Homework 4 - Introduction to OOP - Inheritance and Polymorphism
* Created another implementation of the String class.
    * Using an unmodifiable char array to store the characters.
    * Creating **substrings in O(1)** by using the same character array.
    * Using **Rabin-Karp algorithm** to implement the find method in O(n).
* Created a simple raster drawing app that can draw circles, quadriterals and triangles.

## Homework 5 - Simple Hash Table
Implemented a simple hash table collection. The hash table uses linked lists in case of multiple values having the same hash code.
Implemented the Iterator design pattern.

## Homework 6 - Collections
* Implemented an IntegerStorage collection that uses the **Observer design pattern**.
* Created a **generic** collection that can return the median of the elements in it.
* Created an iterable prime number collection that has a givne number of elements and is able to iterate througt them, without storing them in memory.
* Created a multistack collection that acts as a map and allows to store multiple values with the same key.

## Homework 7 - Files
* Created a console application that can:
    * calculate the **SHA checksum** of a file and check if it is the same as expectd.
    * **encrypt** a file **using AES** crypto-algorithm
    * **decrypt** a file **using AES** crypto-algorithm
* Part 2 of the homework is available once encrypted with part 1. 
* Created a command prompt console application that supports the following:
    * multiline commands
    * cat - prints out the given file
    * charsets - lists out names of supported charsets
    * copy - copies the source file to the destination path
    * exit - exits from the shell
    * help - writes down all the commands with their description, provides aditional help for every comand.
    * hexdump - prints a hexdump of a given file
    * ls - prints the content of the current folder with all the file attributes
    * mkdir - creates a folder
    * tree - prints a hierarchical view of everything in the current folder and all it's subfolders.

## Homework 8 - CPU and Assembly simulation
Created a simulation of a CPU writen in Java that is able to execute an assembly language created just for this simulation.
Created a few assembly programms like a recursive fibonacci calculation to test everything. 
Using **mockito for unit tests**.

## Homework 9 - Parallelization
* Created a program that draws fractals using Newton-Raphson iteration.
* Created a ray-tracer for rendering of 3D scenes.

## Homework 10 - Swing
* Created a custom layout manager.
* Created a calculator app that supports stack operations.
* Created an app that can create and display bar charts.

## Homework 11 - Simple text editor
Created a simpe Notepad++ clone with the following features:

* create new document
* open document
* save document
* save document as (warn user if the document already exists)
* close document (warn user if it is not saved)
* cut/copy/paste text
* statistical information
* display current date and time
* change language
* display multiple documents in diferent tabs

All features are available from:

* menus
* dockable toolbars
* keyboard shortcuts

The application is translated into 3 languages: English, Croatian and German. 
The **internationalization** is done in such a way to enable the user to change the language while using the application, and still enabling the garbage collector to remove unused objects.

## Homework 12 - Webserver
Created a webserver that is able to:

* handle HTTP requests
* store cookies
* execute SmartScript scripts (a script language created in homework 3)
* display html pages
* display pictures

 Used the **Visitor design pattern** and **Composite design pattern** in order to create the engine to execute SmartScript scripts.

## Homework 13 - Java Server pages
Created a few simple web pages with the following options:

* Change the background color
* Calculate the values of sin(x) and cos(x) of all the angles x in a given interval.
* Show a short story with random text color.
* Create a report for a survey about OS usages with dynamically created charts.
* Calculate powers of a given number and download the result as a Microsoft Office Excel document.

## Homework 14 - Databases
Created a database and an web application for surveys. 
After voting for something in the survey, the app displays the results in a table, displays a pie chart with the result data and allows the user to download the votes as a Microsoft Office Excel document.

## Homework 15 - Java Persistence API
Created a web application for a blog using **hibernate** with second level cashing.
The app allows:

* Creating a new user
* Writing a blog post
* Reading a blog post from another user
* Editing a blog post
* Commenting a blog post

## Homework 16.1 - Search engine
Created a **search engine** and a console application that enables the user to use it.
The search engine searches through documents that are in a folder that is given as an argument while starting the application.
The console application allows teh user to search for a given phrase and see the most relevant results. He is also able to ead the whole document in the console.

## Homework 16.2 - Paint
Created a paint application with the following options:

* Choosing the background and foreground color
* Drawing shapes like lines and circles
* Displaying a list of all the objects
* Saving the picture as a .jpeg image 
* Saving the picture in a format that can be opened and edited again
* Opening pictures
* Editing objects

## Homework 17 - Android
Created a simple calculator app that supports sending error reports using e-mail.

## Homewprk 18 - Frontend technologies
Created a single page application for a picture gallery. 
All pictures are categorized and a single picture can be in multiple categories. 
Used technologies:

* JavaScript
* AJAX
* jQuery
* CSS
