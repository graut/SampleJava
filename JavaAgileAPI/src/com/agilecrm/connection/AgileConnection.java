package com.agilecrm.connection;

import com.agilecrm.api.APIManager;

/**
 * <code>AgileConnection</code> contains method to create and test connection
 * with Agile CRM
 * 
 * @author Ghanshyam
 * @since April 2015
 * @see APIManager
 * 
 */
public class AgileConnection
{

    /**
     * Creates a connection to the Agile CRM and configures the resource in
     * {@link APIManager} class
     * @param apiKey2 
     * @param userName2 
     * @param baseURL2 
     * 
     * @return Instance of {@link APIManager}
     * @throws Exception
     */
    public static APIManager getConnection(String baseURL2, String userName2, String apiKey2) throws Exception
    {
    String test="https://"+baseURL2+".agilecrm.com/dev";
	String baseUrl = test;
	String userName = userName2;
	String apiKey = apiKey2;

	// Configures a resource with AGILE CRM credentials
	APIManager apiManager = new APIManager(baseUrl, userName, apiKey);

	return apiManager;
    }

  }
