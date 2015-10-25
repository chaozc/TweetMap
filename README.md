CONTENTS OF THIS FILE
---------------------
* Introduction
* Use Instruction
* HeatMap
* Trend Analysis


=============
Introduction
=============
TweetMap application reads a stream of tweets with geological location from the Twitter Streaming API and store all data into a remote AWS RDS database(Data collected from Oct 17th - Oct 21st, 2015). The server is able to show on a google map all historical tweets stored in the database as well as the real-time tweets. The real time updated tweets are accomplished by Java WebSocket. The whole web application is configured and deployed on AWS Elastic Beanstalk, which enables the resources are allocated properly due to network workload.

The code to extract Tweets and store into database is in TweetStream folder.
The code to show real time tweets on a HeatMap and trend analysis. 
This project is uploaded to github: https://github.com/chaozc/TweetMap

@Authors: Jin Fang(jf2966), Zichen Chao(zc2321)     CS6998 Columbia University
@version: Oct 25, 2015 



===============
Use Instruction
===============
The web server and database server are currently running on AWS beanstalk instance.  Directly access the website via url:  http://tweetmap5env-yictmhdqnq.elasticbeanstalk.com/. Note using https protocol instead of http will cause a websocket SSL_PROTOCAL_ERROR in AWS Elastic Beanstalk.



=========
HeatMap
=========
The first section shows all tweets in the database on a world map. The color gradients on the HeatMap ranges from light to dark when density increases. Click “hide history” to hide all historical data (which were collected over the last week) and only show the real-time streaming tweets. Use the drop down list (including four keywords: ball games, water sports, dance, running) to select a specific keyword, then the HeatMap will only shows the related tweets.  


==============
Trend Analysis
==============
Analysis one is a pie chart that shows the percentage of total numbers of tweets related to four topics from Oct 17th to Oct 21st, 2015. It is not hard to find according to this pie chart, as the weather gets cooler and cooler, the percentage of water sports tweets tends to decrease.   
Analysis two is a curve chart that shows the total number of tweets in 24 hours distribution. All four curves show a big increment during 22:00-24:00 which potentially indicates that people post much more tweets at night than during the day time. 


