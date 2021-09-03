package com.pankajranag.rana_gaming.Slider.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pankajranag.rana_gaming.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link third_f#newInstance} factory method to
 * create an instance of this fragment.
 */
public class third_f extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third_f, container, false);
    }
}