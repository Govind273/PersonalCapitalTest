# PersonalCapitalTest
Query the stored data on a multi-node cluster in an AWS Elasticsearch with the help of AWS Lambda functions and API Gateway.

## Project Details

- Test data is provided [here](http://askebsa.dol.gov/FOIA%20Files/2016/Latest/F_5500_2016_Latest.zip), store the data on AWS Elasticsearch
- Query the data effieciently for Sponsor Name, Sponsor State, and Plan Name by creating Lambda Functions in Java
- Incorporate Best AWS Practises

## Steps Included
 1. Dataset is converted to JSON format to facilitate the AWS Elasticsearch format. The data needed to be partitioned into multiple files to meet the upload size limit using the csv2json.java code
 2. Used shell script **uploadToES.sh** to upload the all the son data
 3. AWS Elasticsearch [ENDPOINT](https://search-personal-capital-test-4m773ana7dhcdm4rkvgkmqo2uu.us-east-2.es.amazonaws.com) and Instance Type : **r5.large.elasticsearch**.
 4. ES Health Image is saved in the resources folder.
 5. Incorporated **AWS toolkit** for smother development Experience on AWS.
 6. Cretaed Lambda fucntion **SearchFunction.java** which implements the RequestStreamHandler Interface.
 7. Eposed the search results with this [URI] (https://owj1p1145e.execute-api.us-west-2.amazonaws.com/prod?) followed by the search query parameters. The URL is using the SearchFunction lambda function 
 8. Tests Folder includes the files and the conext of lambda functions. Hence, region and context details will also be tested included such as health of shards or indices,

## Test Queries

  1. Search by Plan Name without space [https://owj1p1145e.execute-api.us-west-2.amazonaws.com/prod?planName=vision]
  2. Search by Plan Name with spaces [https://owj1p1145e.execute-api.us-west-2.amazonaws.com/prod?planName=CHEMSTATIONS 401(K) PLAN]
  3. Search by Sponsor Name [https://owj1p1145e.execute-api.us-west-2.amazonaws.com/prod?sponsorName=UNIVERSITY OF DAYTON]
  4. Search by Sponsor Location with spaces [https://owj1p1145e.execute-api.us-west-2.amazonaws.com/prod?sponsorLocState=NC]

## Future Work
Utilization/Enhancement of one of the resources may cast a toll on some other aspect of Project.

### Performace
1. Usage of Async I/O fraeworks such as **Node** or a Judicial usage of Thread Pools in Java can be highly effiencent since the application is more catereted towards the the data-driven application.  
2. Sharding/Indexing applications business rules can highly improve the query search time over a period of time. 
3. Request Interveptors can be used before after the services or before the indexes to avoid node failures from bulk requests.
4. In a read-heavy architecture having more replicas of the same data could improve the query time and reduce node-failures. 

### User Experience
1. User Experience with queries can be improved with ElasticSearch features such as Autocompletion,Fuzzy Search, or advanced search features.
2. Continios polling of data at Intevarls from data sources such as S3 can immensenly improve for Real time expereince of users if application used in real-time application such as FlighBooking or Stocks.
