# Asterisk API
A test API for learning Asterisk and api4java library.

Task:
1.Create an Admin Panel where users can upload a promt, choose a name for their campaign and a content provider.
2.A user must be able to modify, delete, filter and search by name any campaign.
3.Whenever a called is received by Asterisk, a random prompt from those uploaded must be played.
4.If the full prompt was played, the number of listens must will increase.

Compile:
In order to compile this project you must have a mysql server, an Asterisk server, maven, quarkus...

Compile Back End:
mvn clean compile quarkus:dev
