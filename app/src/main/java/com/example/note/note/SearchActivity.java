package com.example.note.note;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.note.note.adapter.NoteAdapter;
import com.example.note.note.bean.Note;


import org.litepal.crud.DataSupport;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Note> noteList = DataSupport.where("recycled = ?","0").order("time desc").find(Note.class);
    private NoteAdapter adapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = (SearchView) findViewById(R.id.searchView);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter(noteList);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText.trim())) {
                    noteList = DataSupport
                            .where("recycled = ? and title like ? or content like ?","0", "%"+newText.trim()+"%","%"+newText.trim()+"%")
                            .order("time desc")
                            .find(Note.class);
                    adapter = new NoteAdapter(noteList);
                    recyclerView.setAdapter(adapter);
                } else {
                    noteList = DataSupport.where("recycled = ?","0").order("time desc").find(Note.class);
                    adapter = new NoteAdapter(noteList);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });

    }

}
