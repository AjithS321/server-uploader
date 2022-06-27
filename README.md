# server-uploader

In this project tried various ways of inserting the records into the db with the Spring boot JPA


## Used ways to achive fasest upload of informmation in to db 

* Used normal JPA to insert the record into DB
* Used Executer to insert the records wth JPA (But it dosn't work that much effectively)
* Used JdbcTemplate updateBatch to do multi insert in db (This works perfectly) 

With the 3rd approch we can achive maximum efficiency where we can achive 1Million records within 15seconds approx.

