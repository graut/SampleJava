package com.agilecrm.tester;

import java.util.ArrayList;
import java.util.List;

import com.agilecrm.api.APIManager;
import com.agilecrm.api.NoteAPI;
import com.agilecrm.stubs.Note;

public class NoteTesterAPI {
	public static void main(String[] args) {
		try {
			// Create a connection to Agile CRM
			APIManager apiManager = AgileConnection.getConnection();

			// Get the Note API with configured resource
			NoteAPI noteApi = apiManager.getNoteAPI();

			// 3.1 Create a note and relate that to contacts
			// List of contact id's to which notes are added
			List<String> contactIds = new ArrayList<String>();
			contactIds.add("5635498630447104");
			contactIds.add("5666380049285120");
			contactIds.add("5142634557341696");

			// Adding note
			Note note1 = new Note();

			note1.setSubject("Testing Note1");
			note1.setDescription("Testing to add note1");
			note1 = noteApi.addNoteToContactIds(note1, contactIds);
			System.out.println("Added note..." + note1);

			// Another method to add note
			Note note2 = new Note();
			note2 = noteApi.addNoteToContactIds("Testing Note2",
					"Testing to add note2", contactIds);
			System.out.println("Added note..." + note2);

			// Gets notes related to specific contact
			List<Note> notes = noteApi.getNotesByContactId(contactIds.get(0));
			System.out.println("All notes of contact.. " + notes);

			// 3.4 Delete a specific note from specific contact
			// Delete note of a contact by note id
			String noteId = String.valueOf(notes.get(0).getId());
			noteApi.deleteNoteByContactId(contactIds.get(0), noteId);
			System.out.println("Deleted note.. " + noteId + " "
					+ contactIds.get(0));

		} catch (Exception e1) {

			e1.printStackTrace();
		}
	}

}
