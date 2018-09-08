package com.titmouse.anton.todo.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.titmouse.anton.todo.R;
import com.titmouse.anton.todo.database.ToDoRealmDaoImpl;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToDoRealmDaoImpl.getInstance().initRealm(this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, new ToDoFragment())
                .commit();

    }
}
