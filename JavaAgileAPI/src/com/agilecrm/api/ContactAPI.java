package com.agilecrm.api;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import com.agilecrm.stubs.Contact;
import com.agilecrm.stubs.ContactCollection;
import com.agilecrm.stubs.ContactField;
import com.agilecrm.stubs.ContactField.FieldName;
import com.agilecrm.stubs.Tag;
import com.agilecrm.utils.StringUtils;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * <code>ContactAPI</code> class contains methods to add, get, update and delete
 * contacts from Agile CRM
 * 
 * @author Ghanshyam
 * @since April 2015
 * @see APIManager
 */
public class ContactAPI
{
    /**
     * Holds a {@link WebResource} object
     */
    private final WebResource resource;

    /**
     * Initializes the field resource with the configured resource from
     * {@link APIManager}
     * 
     * @param resource
     *            {@link WebResource}
     */
    ContactAPI(WebResource resource)
    {
	this.resource = resource;
    }

    /**
     * Adds a contact to Agile CRM with the given {@link Contact} object
     * 
     * @param contact
     *            {@link Contact} to be added
     * @return Added {@link Contact}
     * @throws Exception
     */
    public Contact addContact(Contact contact) throws Exception
    {

	System.out.println("Adding contact -------------------------");

	if (contact == null)
	{
	    throw new Exception("Cannot add a contact without a contact object");
	}

	contact = resource.path("/api/contacts")
		.accept(MediaType.APPLICATION_XML).post(Contact.class, contact);

	return contact;
    }

    /**
     * Adds a contact to Agile CRM with the given parameters
     * 
     * @param firstName
     *            First Name of the {@link Contact}
     * @param lastName
     *            Last Name of the {@link Contact}
     * @param organization
     *            Organization name of the {@link Contact}
     * @param email
     *            Email of the {@link Contact}
     * @param title
     *            Designation of the {@link Contact}
     * @param phone
     *            Phone number of the {@link Contact}
     * @param website
     *            Web URL of the {@link Contact}
     * @return Added {@link Contact}
     * @throws Exception
     */
    public Contact addContact(String firstName, String lastName,
	    String organization, String email, String title, String phone,
	    String website) throws Exception
    {

	System.out.println("Adding contact -------------------------");

	if (StringUtils
		.isNullOrEmpty(new String[] { firstName, lastName, email }))
	{
	    throw new Exception("Contact fileds empty");
	}

	Contact contact = new Contact();

	contact = setContactFieldstoContact(contact, firstName, lastName,
		organization, email, title, phone, website);

	contact = resource.path("/api/contacts")
		.accept(MediaType.APPLICATION_XML).post(Contact.class, contact);

	return contact;
    }

    /**
     * Retrieves all the contacts from agents Agile CRM account
     * 
     * @return {@link List} of {@link Contact}
     * @throws Exception
     */
    public List<Contact> getContacts() throws Exception
    {
	System.out.println("Getting contacts -----------------------");

	List<Contact> contactCollection = resource.path("/api/contacts")
		.accept(MediaType.APPLICATION_XML).get(new GenericType<List<Contact>>() {});

	return contactCollection;
    }

    /**
     * Retrieves the {@link Contact} from Agile CRM based on its Id
     * 
     * @param contactId
     *            {@link String} id of the {@link Contact}
     * @return {@link Contact}
     * @throws Exception
     */
    public Contact getContact(String contactId) throws Exception
    {
	System.out.println("Getting contact by id ----------------");

	if (StringUtils.isNullOrEmpty(new String[] { contactId }))
	    throw new Exception("Cannot get a contact without contact id");

	Contact contact = resource.path("/api/contacts/" + contactId)
		.accept(MediaType.APPLICATION_XML).get(Contact.class);

	return contact;
    }

    /**
     * Retrieves the {@link Contact} from Agile CRM based on its email
     * 
     * @param email
     *            {@link String} email of the {@link Contact}
     * @return {@link Contact}
     * @throws Exception
     */
    public Contact getContactFromEmail(String email) throws Exception
    {
	System.out.println("Getting contact by email ----------------");

	if (StringUtils.isNullOrEmpty(new String[] { email }))
	    throw new Exception("Cannot get a contact without email");

	Contact contact = resource.path("/api/contacts/search/email/" + email)
		.accept(MediaType.APPLICATION_XML).get(Contact.class);

	return contact;
    }

    /**
     * Updates the {@link Contact} based on its id and given parameters
     * 
     * @param contactId
     *            {@link String} id of the {@link Contact}
     * @param contactFields
     *            {@link Map} which contact fields to be updated
     * @param customFields
     *            {@link Map} which contains custom fields to be added to
     *            {@link Contact}
     * @return Updated {@link Contact}
     * @throws Exception
     */
    public Contact updateContact(String contactId,
	    Map<FieldName, String> contactFields,
	    Map<String, String> customFields) throws Exception
    {
	System.out.println("updating contact -----------------------");

	if (StringUtils.isNullOrEmpty(new String[] { contactId }))
	    throw new Exception("Cannot update a contact without contact id");

	if (StringUtils.isNullOrEmpty(contactFields)
		&& StringUtils.isNullOrEmpty(customFields))
	{
	    throw new Exception(
		    "Specify contact field or custom field to update a contact");
	}

	Contact contact = getContact(contactId);
	contact = setContactFieldstoContact(contact, contactFields);
	contact = setCustomFieldstoContact(contact, customFields);
	System.out.println(new JSONObject(new ObjectMapper()
		.writeValueAsString(contact)));

	contact = resource.path("/api/contacts").put(Contact.class, contact);

	return contact;
    }

    /**
     * Updates the {@link Contact} with the given {@link Contact} object
     * 
     * @param contact
     *            {@link Contact} with the changes
     * @return Updated {@link Contact}
     * @throws Exception
     */
    public Contact updateContact(Contact contact) throws Exception
    {
	System.out.println("updating contact -----------------------");

	System.out.println(new JSONObject(new ObjectMapper()
		.writeValueAsString(contact)));
	if (contact == null)
	{
	    throw new Exception(
		    "Cannot update a contact without a contact object");
	}

	contact = resource.path("/api/contacts").put(Contact.class, contact);

	return contact;
    }

    /**
     * Deletes a {@link Contact} in the Agile CRM based on its id
     * 
     * @param contactId
     *            {@link String} id of the existing contact
     * @throws Exception
     */
    public void deleteContact(String contactId) throws Exception
    {
	System.out.println("deleting contact by id -----------------");

	if (StringUtils.isNullOrEmpty(new String[] { contactId }))
	    throw new Exception("Cannot delete a contact without contact id");

	resource.path("/api/contacts/" + contactId)
		.accept(MediaType.TEXT_PLAIN).delete();
    }

    /**
     * Deletes list of contacts in the Agile CRM based on their id's
     * 
     * @param contactIds
     *            {@link List} of {@link String} contact id's to be deleted
     * @throws Exception
     */
    public void deleteBulkContacts(List<String> contactIds) throws Exception
    {
    	System.out.println("Bulk delete contacts -----------------------"+contactIds);

		if (StringUtils.isNullOrEmpty(contactIds))
			throw new Exception("Specify contact ids to delete them");

		Form form = new Form();
		form.add("ids", contactIds);
		

		resource.path("/api/bulk/update").queryParam("action_type", "DELETE")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);

    }

    /**
     * Adds tags to the existing contacts in the Agile CRM
     * 
     * @param tags
     *            {@link List} of {@link String} tags to be added
     * @param contactIds
     *            {@link List} of {@link String} contact id's to which tags are
     *            added
     * @throws Exception
     */
    public void addTagsToContacts(List<String> tags, List<String> contactIds)
	    throws Exception
    {
	System.out
		.println("Adding tags to contacts based on contact id's ----");

	if (StringUtils.isNullOrEmpty(tags)
		|| StringUtils.isNullOrEmpty(contactIds))
	{
	    throw new Exception("Tags or contact ids are not provided");
	}

	Form form = new Form();
	form.add("tags", tags);
	form.add("contact_ids", contactIds);

	resource.path("/api/bulk/update").queryParam("action_type", "ADD_TAG")
		.post(ClientResponse.class, form);

    }

    /**
     * Adds tag to the existing contact with email specified in the Agile CRM
     * 
     * @param tag
     *            {@link Tag} to be added to {@link Contact}
     * @param email
     *            {@link String} email of the {@link Contact}
     * @throws Exception
     */
    public void addTagsToEmail(Tag tag, String email) throws Exception
    {
	System.out
		.println("Adding tags to contact based on email ------------------");

	Form form = new Form();

	form.add("tags", tag.getTags());
	form.add("email", email);

	resource.path("/api/contacts/email/tags/add")
		.accept(MediaType.APPLICATION_XML)
		.post(ClientResponse.class, form);

    }

    /**
     * Sets the given parameters(contact fields) to the given contact by
     * iterating through the map.
     * 
     * This method is used internally while adding and updating contact
     * 
     * @param contact
     *            {@link Contact}
     * @param contactFields
     *            {@link Map} of contact fields to be set to contact
     * @return {@link Contact} after setting fields
     */
    private Contact setContactFieldstoContact(Contact contact,
	    Map<FieldName, String> contactFields)
    {
	@SuppressWarnings("rawtypes")
	Iterator fields = contactFields.entrySet().iterator();

	while (fields.hasNext())
	{
	    @SuppressWarnings("unchecked")
	    Entry<FieldName, String> thisPair = (Entry<FieldName, String>) fields
		    .next();
	    FieldName contactFieldName = thisPair.getKey();
	    String contactFieldValue = thisPair.getValue();
	    contact.setContactField(contactFieldName, contactFieldValue);
	}

	return contact;
    }

    /**
     * Sets the given parameters(custom fields) to the given contact by
     * iterating through the map.
     * 
     * This method is used internally while adding and updating contact
     * 
     * @param contact
     *            {@link Contact}
     * @param customFields
     *            {@link Map} of custom fields to be set to contact
     * @return {@link Contact} after setting fields
     */
    private Contact setCustomFieldstoContact(Contact contact,
	    Map<String, String> customFields)
    {
	@SuppressWarnings("rawtypes")
	Iterator fields = customFields.entrySet().iterator();

	while (fields.hasNext())
	{
	    @SuppressWarnings("unchecked")
	    Entry<String, String> thisPair = (Entry<String, String>) fields
		    .next();
	    String customFieldName = thisPair.getKey();
	    String customFieldValue = thisPair.getValue();
	    contact.setCustomField(customFieldName, customFieldValue);
	}

	return contact;
    }

    /**
     * Sets the given parameters(contact fields) to the given contact.
     * 
     * This method is used internally while adding and updating contact
     * 
     * @param contact
     *            {@link Contact}
     * @param firstName
     *            First Name of the {@link Contact}
     * @param lastName
     *            Last Name of the {@link Contact}
     * @param organization
     *            Organization name of the {@link Contact}
     * @param email
     *            Email of the {@link Contact}
     * @param title
     *            Designation of the {@link Contact}
     * @param phone
     *            Phone number of the {@link Contact}
     * @param website
     *            Web URL of the {@link Contact}
     * @return {@link Contact} after setting fields
     */
    private Contact setContactFieldstoContact(Contact contact,
	    String firstName, String lastName, String organization,
	    String email, String title, String phone, String website)
    {

	contact.setContactField(FieldName.FIRST_NAME, firstName);
	contact.setContactField(FieldName.LAST_NAME, lastName);
	contact.setContactField(FieldName.ORGANIZATION, organization);
	contact.setContactField(FieldName.EMAIL, email);
	contact.setContactField(FieldName.TITLE, title);
	contact.setContactField(FieldName.PHONE, phone);
	contact.setContactField(FieldName.WEBSITE, website);

	return contact;
    }

	/**
	 * Adds the given parameters (property name and property value) to the given contact,
	 * if the property already exists, it is updated.
	 *
	 * This method is used internally while adding and updating contact property
	 *
	 * @param name
	 *            name of the property to be added
	 * @param value
	 *            value of the property to be added
	 * @param email
	 *            email of the contact
	 * @return
	 */
	public void addProperty(String name, String value, String email)
	{
		ContactField contactField = new ContactField();
		contactField.setName(name);
		contactField.setValue(value);

		resource.path("api/contacts/add/property").queryParam("email", email).post(Contact.class, contactField);
	}
	
	public List<Contact> getContactsByPageCursor(String page,String cursor) {
		System.out
				.println("Getting contacts By page and cursor-----------------------");
		MultivaluedMap queryParams = new MultivaluedMapImpl();
		queryParams.add("page_size", page);
		queryParams.add("cursor", cursor);
		
		List<Contact> contactCollection = resource.path("/api/contacts")
			.queryParams(queryParams)
			.accept(MediaType.APPLICATION_XML).get(new GenericType<List<Contact>>() {});

		return contactCollection;
	}
	
	/**
	 * Delete tag to the existing contact with email specified in the Agile CRM
	 * 
	 * @param tag1
	 *            {@link Tag} to be added to {@link Contact}
	 * @param email
	 *            {@link String} email of the {@link Contact}
	 * @throws Exception
	 */
	public void deleteTagsToEmail(Tag tag1, String email) throws Exception {
		System.out.println("Deleting tags to contact based on email ------------------");

		Form form = new Form();

		form.add("tags", tag1.getTags());
		form.add("email", email);

		ClientResponse clientResponse=resource.path("/api/contacts/email/tags/delete")
				.accept(MediaType.APPLICATION_XML)
				.post(ClientResponse.class, form);
		
        System.out.println("Response code --"+clientResponse.getStatus());
	}
	
	/**
	 * Add score to the existing contact with email specified in the Agile CRM
	 * 
	 * @param score
	 *            {@link Tag} to be added to {@link Contact}
	 * @param email
	 *            {@link String} email of the {@link Contact}
	 * @throws Exception
	 */
	public void addScoreToEmail(String score, String email) {

		Form form = new Form();

		form.add("score", score);
		form.add("email", email);

		ClientResponse clientResponse=resource.path("/api/contacts/add-score")
				.accept(MediaType.APPLICATION_XML)
				.post(ClientResponse.class, form);
		
		System.out.println(" Response code = "+clientResponse.getStatus());
	}
}