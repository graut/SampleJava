package com.agilecrm.stubs;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement(name = "note")
public class Note
{
    @JsonProperty("id")
    private Long id;

    @JsonProperty("created_time")
    private Long created_time;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("description")
    private String description;

    @JsonProperty("contact_ids")
    private List<String> contact_ids;

    @JsonProperty("contacts")
    private List<Contact> contacts;

    @JsonProperty("deal_ids")
    private List<String> deal_ids;

    @JsonProperty("owner_id")
    private String owner_id;

    public Long getId()
    {
	return id;
    }

    public void setId(Long id)
    {
	this.id = id;
    }

    public Long getCreated_time()
    {
	return created_time;
    }

    public void setCreated_time(Long created_time)
    {
	this.created_time = created_time;
    }

    public String getSubject()
    {
	return subject;
    }

    public void setSubject(String subject)
    {
	this.subject = subject;
    }

    public String getDescription()
    {
	return description;
    }

    public void setDescription(String description)
    {
	this.description = description;
    }

    public List<String> getContact_ids()
    {
	return contact_ids;
    }

    public void setContact_ids(List<String> contact_ids)
    {
	this.contact_ids = contact_ids;
    }

    public List<Contact> getContacts()
    {
	return contacts;
    }

    public void setContacts(List<Contact> contacts)
    {
	this.contacts = contacts;
    }

    public List<String> getDeal_ids()
    {
	return deal_ids;
    }

    public void setDeal_ids(List<String> deal_ids)
    {
	this.deal_ids = deal_ids;
    }

    public String getOwner_id()
    {
	return owner_id;
    }

    public void setOwner_id(String owner_id)
    {
	this.owner_id = owner_id;
    }

	@Override
	public String toString() {
		return "Note [id=" + id + ", created_time=" + created_time
				+ ", subject=" + subject + ", description=" + description
				+ ", contact_ids=" + contact_ids + ", contacts=" + contacts
				+ ", deal_ids=" + deal_ids + ", owner_id=" + owner_id + "]";
	}


}
