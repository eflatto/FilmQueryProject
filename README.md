# FilmQueryProject

## Description
<p>
This project allows users to query a database of films and actors using various search criteria, including film ID and keywords.
</p>


## Classes
<ul>
<li>DatabaseAccessorObject Class:</li>
<p>
 This class provides an implementation of a Database Access Object (DAO) class for querying a MySQL database using JDBC. It includes methods for retrieving (SELECT) films  and actors data from the sdvid database.<br>
</p>

<ul>
<span><b>The class includes methods for: </b><span>
<li> querying films by film id
<li> querying actors by actor id
<li> querying actors by film id
<li> querying films by actor id
<li> querying films by keywords
</ul>
<br>
<li>DatabaseAccessor Interface:</li>
<p>
 This Interface provides the abstract methods for accessing and retrieving information from the database.
</p>
<ul>
<span><b>The class includes abstract methods for: </b><span>
<li> querying films by film id
<li> querying actors by actor id
<li> querying actors by film id
<li> querying films by actor id
<li> querying films by keywords
</ul>
<br>
<li>Film Query Class:</li>
<p>
This class allows the user to query the film database. It connects to the database through an instance of DatabaseAccessorObject, which provides the methods for accessing the database.<br>
</p>

<ol>
<span><b>The user interface has three options: </b><span>
<li>Look up a film by its ID</li>
<li>Look up a film by a search keyword</li>
<li>Exit the application</li>
</ol>

<br>
<li>Actor Class:</li>
<p>
The Actor class represents an actor in a movie. It has three fields: id, firstName, and lastName, and corresponding getter and setter methods for each field. This class represents the actor table in the sdvid database and each attribute maps to the column in that actor table<br>
</p>

<li>Film Class:</li>
<p>
The Film class represents a movie. It has 12 fields each mapping to the attributes in the film table of the sdvid database. The class includes getters and setters to retrieve this information and use it to make film objects in the DAO class <br>
</p>

</ul>


## Technologies Used
<ul>
<li>Java</li>
<li>SQL</li>
<li>Maven</li>
</ul> 

## Lessons Learned
<ul>
<li>I got a better understanding of the DAO design pattern, separation of concerns  and how to use the DAO class. Which is like a mediator between my application and the database. </li>
<li>Learned how to use prepared statements over statements in order to prevent an SQL injection attack</li>
<li>I learned more about the Java Database Connectivity (JDBC) and how to establish a connection to a database using a JDBC driver</li>
<li>I learned how to use Maven to manage my dependencies instead of downloading and managing each library. I used Maven to create a pom.xml file and declare the dependency to automatically download the needed dependency for connecting with JDBC and MYSQL</li>
</ul>
