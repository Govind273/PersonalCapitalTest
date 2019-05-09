package com.amazonaws.lambda.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.fasterxml.jackson.databind.JsonNode;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

/**
 * This class parses http request with single query string to a request understood by AWS Elasticsearch Service.
 */
public class ElasticSearch implements RequestStreamHandler {
    JSONParser parser = new JSONParser();
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

    	    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    	    JSONObject responseJson = new JSONObject();
    	 
    	    final ObjectMapper om = new ObjectMapper();
    	    JsonNode json= om.readTree(inputStream);
    	    
    	    JsonNode query=json.path("query");
    	    
    	    String responseCode = "200";
    	    String esUrl = "https://search-personal-capital-test-4m773ana7dhcdm4rkvgkmqo2uu.us-east-2.es.amazonaws.com/_search?q=";
    	        	    
    	    if(!query.isMissingNode()) {
    	    	if(!query.path("planName").isMissingNode()) {
    	    		esUrl += "PLAN_NAME:"+URLEncoder.encode(query.path("planName").asText(), "UTF-8");
;
    	    	}
    	    	else if(!query.path("sponsorName").isMissingNode()) {
    	    		esUrl += "SPONSOR_DFE_NAME:"+URLEncoder.encode(query.path("sponsorName").asText(), "UTF-8");    	  
    	    	}
    	    	else if(!query.path("sponsorLocState").isMissingNode()) {
    	    		esUrl += "SPONS_DFE_LOC_US_STATE:"+URLEncoder.encode(query.path("sponsorLocState").asText(), "UTF-8");
    	    	}
    	    }
    	  
    	    try {
                StringBuffer response = new StringBuffer();
                URL tmp = new URL(esUrl);
                HttpURLConnection con = (HttpURLConnection) tmp.openConnection();
                con.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                while((inputLine = in.readLine()) != null){
                    response.append(inputLine);
                }

                JSONParser parser = new JSONParser();
                JSONObject responseBody = (JSONObject) parser.parse(response.toString());

                
                responseJson.put("statusCode", responseCode);
                responseJson.put("body", responseBody);
    	        
    	    } catch (ParseException pex) {
    	        responseJson.put("statusCode", 400);
    	        responseJson.put("exception", pex);
    	    }
    	    OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
    	    writer.write(responseJson.toString());
    	    writer.close();         	        

    }
}