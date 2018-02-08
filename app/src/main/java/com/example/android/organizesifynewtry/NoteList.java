package com.example.android.organizesifynewtry;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;

import com.example.android.organizesifynewtry.NoteEdit;
import com.example.android.organizesifynewtry.NotesDbAdapter;
import com.example.android.organizesifynewtry.R;

public class NoteList extends AppCompatActivity {

	private static final int ACTIVITY_CREATE=0;
	private static final int ACTIVITY_EDIT=1;

	private static final int DELETE_ID = Menu.FIRST;
	private int mNoteNumber = 1;

	private NotesDbAdapter mDbHelper;
	public ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notelist);
		mDbHelper = new NotesDbAdapter (this);
		mDbHelper.open();
		fillData();
		 lv = (ListView) findViewById(R.id.list);
		registerForContextMenu(lv);
		Button addnote = (Button)findViewById(R.id.addnotebutton);
		addnote.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createNote();
			}
		});

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//ContactsContract.CommonDataKinds.Note note = posts.get(position);

				Intent i = new Intent(getApplicationContext(), NoteEdit.class);
				i.putExtra(NotesDbAdapter.KEY_ROWID, id);
				startActivityForResult(i, ACTIVITY_EDIT);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

			case R.id.notepad:
				Toast.makeText(this, "NotePad", Toast.LENGTH_LONG).show();
				startActivity(new Intent(this, NoteList.class));
				finish();
				return true;
			case R.id.about:
				Toast.makeText(this, "About", Toast.LENGTH_LONG).show();
				startActivity(new Intent(this, About.class));
				finish();
				return true;
			case R.id.home:
				Toast.makeText(this, "Home", Toast.LENGTH_LONG).show();
				startActivity(new Intent(this, MainActivity.class));
				finish();
				return true;
			case R.id.toDoList:
				Toast.makeText(this, "TodoList", Toast.LENGTH_LONG).show();
				startActivity(new Intent(this, ToDoList.class));
				finish();
				return true;
			case R.id.alarm:
				Toast.makeText(this, "Alarm", Toast.LENGTH_LONG).show();
				startActivity(new Intent(this, Alarms.class));
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void createNote() {
		Intent i = new Intent(this, NoteEdit.class);
		startActivityForResult(i, ACTIVITY_CREATE);
	}

/*	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, NoteEdit.class);
		i.putExtra(NotesDbAdapter.KEY_ROWID, id);
		startActivityForResult(i, ACTIVITY_EDIT);
	}*/




	private void fillData() {
		// Get all of the notes from the database and create the item list
		Cursor notesCursor = mDbHelper.fetchAllNotes();
		startManagingCursor(notesCursor);


		String[] from = new String[] { NotesDbAdapter.KEY_TITLE ,NotesDbAdapter.KEY_DATE};
		int[] to = new int[] { R.id.text1 ,R.id.date_row};

		// Now create an array adapter and set it to display using our row
		SimpleCursorAdapter notes =
				new SimpleCursorAdapter(this, R.layout.notes_row, notesCursor, from, to);
		 lv = (ListView) findViewById(R.id.list);
		lv.setAdapter(notes);
//		setListAdapter(notes);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
									ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, R.string.menu_delete);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case DELETE_ID:
				AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
				mDbHelper.deleteNote(info.id);
				fillData();
				return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		fillData();
	}




}
