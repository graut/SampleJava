package com.agilecrm.tester;

import java.util.ArrayList;
import java.util.List;

import com.agilecrm.api.APIManager;
import com.agilecrm.api.DealAPI;
import com.agilecrm.stubs.Deal;

/**
 * <code>TestDeal</Code> class contains main method to test all the methods in
 * <code>DealAPI</code> class
 * 
 * @author Ghanshyam
 * @since April 2015
 * @see DealAPI
 * 
 */
public class DealTesterAPI
{

    public static void main(String[] args)
    {
	try
	{
	    // Create a connection to Agile CRM
	    APIManager apiManager = AgileConnection.getConnection();

	    // Get the Deal API with configured resource
	    DealAPI dealApi = apiManager.getDealAPI();

	    // List of contact id's to which deals are related
	    //List<String> contactIds = new ArrayList<String>();
	    //contactIds.add("5707794741198848");
		//contactIds.add("5707922315149312"); // Adding Deal to two contact ids

	    // Adding deal
		/*Deal deal1 = new Deal();
		deal1.setMilestone("New");		// Milestone name should be exactly like in website
		deal1.setName("Test Deal1");	// Example https://{domain}.agilecrm.com/#milestones
		deal1.setProbability(50);
		deal1.setExpected_value(Double.valueOf("600"));  // Epoch time in second
		deal1.setContact_ids(contactIds);

		deal1 = dealApi.addDeal(deal1);

		System.out.println("Added deal... " + deal1);

	    // Another method to relating deal to contacts
	    Deal deal2 = new Deal();

	    deal2 = dealApi.addDealToContacts("Test Deal2", 76, Double.valueOf("500"), "lost",
		    contactIds);

	    System.out.println("Added deal... " + deal2);

	    // Get all deals
	    List<Deal> deals1 = dealApi.getDeals();

	    System.out.println("All deals..." + deals1);
*/	    
	    // Get deal by id
		
		List<String> noteIds = new ArrayList<String>();
		noteIds.add("5753792934969344");
		//noteIds.add("5633407786680320");
		
	    Deal deal3 = dealApi.getDealByDealId("5663944903491584");

	    System.out.println("Got deal..." + deal3);
	    
	    


	    /*if(dealApi.getDealByDealId1("5693840124018688").size()>0){
	    	System.out.println("U hoooooooooooooo");
	    	System.out.println(dealApi.getDealByDealId1("5693840124018688"));
	    	deal3.setNotes(dealApi.getDealByDealId1("5693840124018688"));
	    }*/
	    	

	    // Update deal
	    deal3.setNotes(noteIds);
	    deal3.setMilestone("Prospect");
	    deal3.setExpected_value(Double.valueOf("70"));
	    deal3.setProbability(50);

	    System.out.println("Deal before updating"+deal3);
	    Deal updatedDeal = dealApi.updateDeal(deal3);

	    System.out.println("Updated deal..." + updatedDeal);

	  /*  // Delete deal
	    dealApi.deleteDealByDealId("5135472867147776");
	    System.out.println("Dela is deleted successfully");

	    // Bulk delete deals
	    List<String> dealIds = new ArrayList<String>();
	    dealIds.add("5170658178760704");
	    dealIds.add("5174751953682432");
	    dealIds.add("5634036831617024");

	    dealApi.deleteDeals(dealIds);
	    System.out.println("Three deal deleted successfully");
*/	}
	catch (Exception e)
	{
	    System.out.println(e.getMessage());
	    e.printStackTrace();
	}

    }

}