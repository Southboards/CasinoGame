package com.example.casino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.casino.viewmodel.CasinoViewModel;

public class MainActivity extends AppCompatActivity {
    private CasinoViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userViewModel = new ViewModelProvider(this).get(CasinoViewModel.class);
        getLifecycle().addObserver(new LifecycleObserver() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
    }

    public CasinoViewModel getUserViewModel() {
        return userViewModel;
    }
}