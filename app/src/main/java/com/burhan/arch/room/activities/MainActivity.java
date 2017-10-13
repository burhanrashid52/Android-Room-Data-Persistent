package com.burhan.arch.room.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.burhan.arch.room.R;
import com.burhan.arch.room.adapter.UserAdapter;
import com.burhan.arch.room.fragments.AddUserDialogFragment;
import com.burhan.arch.room.models.User;
import com.burhan.arch.room.models.UserModel;

import java.util.List;

public class MainActivity extends AppCompatLifecycleActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView rvUsers;
    private TextView txtNoData;
    private FloatingActionButton fabAdd;
    private UserAdapter userAdapter;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        init();
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter();
        rvUsers.setAdapter(userAdapter);
        userModel = ViewModelProviders.of(this).get(UserModel.class);

        /*userModel.getAllUser().observe(MainActivity.this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> userList) {
                userAdapter.setUserList(userList);
                updateUI();
            }
        });*/

        userModel.getAllUserPagination().observe(this, new Observer<PagedList<User>>() {
            @Override
            public void onChanged(@Nullable PagedList<User> users) {
                Log.e(TAG, "onChanged: " + users.size());
                userAdapter.setList(users);
                updateUI();
            }
        });

        userAdapter.setOnItemClickListener(new UserAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                AddUserDialogFragment.show(view.getContext(), userAdapter.getUserItem(position).getUid());
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUserDialogFragment.show(MainActivity.this);
            }
        });
        updateUI();
    }

    private void init() {
        rvUsers = findViewById(R.id.rvUser);
        txtNoData = findViewById(R.id.txtNodata);
        fabAdd = findViewById(R.id.fabAdd);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_dummy) {
            userModel.insertDummyUsers();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {

        int itemCount = userAdapter.getItemCount();
        txtNoData.setVisibility(itemCount == 0 ? View.VISIBLE : View.GONE);
        rvUsers.setVisibility(itemCount == 0 ? View.GONE : View.VISIBLE);
        rvUsers.scrollToPosition(itemCount > 0 ? itemCount - 1 : 0);
    }
}
