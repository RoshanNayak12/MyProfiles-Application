package com.example.myprofiles;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProfileDisplay extends AppCompatActivity {

    private ProfileDisplayViewModel viewModel;
    private RecyclerListView recyclerAdapter;
    private RecyclerView recyclerView;
    private Repository repository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.black)));
        viewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(ProfileDisplayViewModel.class);
        recyclerAdapter = new RecyclerListView(this);
        LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.profiles_list);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView); //set the callback to the recyclerView.
        repository = new Repository(this);
        ProfileDatabase.threadPool.execute(new Runnable() {
            @Override
            public void run() {
                viewModel.setAllProfileDetails(repository.getAllProfileDetails());
            }
        });
        viewModel.allProfileDetails.observe(this, new Observer<List<ProfileDatabaseEntity>>() {
            @Override
            public void onChanged(List<ProfileDatabaseEntity> profileDatabaseEntities) {
                recyclerAdapter.setProfileData(profileDatabaseEntities);
                recyclerView.setAdapter(recyclerAdapter);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all : {
                ProfileDatabase.threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        repository.deleteAllProfiles();
                        viewModel.setAllProfileDetails(repository.getAllProfileDetails());
                    }
                });
            }
        }
        return true;
    }

    //write the callback that gets called after the user touches the view and gets his fingers off from it.
    //0 says we do not want any functionality to drag and RIGHT says delete on right swipe.
    public ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            switch (direction) {
                case ItemTouchHelper.RIGHT : {
                    Log.d("cannot", "Reached the on swipe method");
                    Integer position = viewHolder.getAdapterPosition();
                    List<ProfileDatabaseEntity> profileData = recyclerAdapter.getProfileData();
                    ProfileDatabaseEntity values = profileData.get(position);
                    repository.deleteProfile(values.getId());
                    //viewModel.setAllProfileDetails(repository.getAllProfileDetails());
                    profileData.remove(position);
                    //recyclerAdapter.notifyDataSetChanged();
                    Log.d("cannot", "Reached the before dataset changed method.");
                }
            }
        }
    };
}
