package com.sys1yagi.android.leakchecker.sample.fragments;


import com.sys1yagi.android.leakchecker.LeakChecker;
import com.sys1yagi.android.leakchecker.sample.R;
import com.sys1yagi.android.leakchecker.sample.views.TextViewAppendLogger;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LeakedFragment extends Fragment {

    private final static List<Bitmap> leakObjectList = new ArrayList<Bitmap>();

    public static LeakedFragment newInstance() {
        LeakedFragment fragment = new LeakedFragment();

        return fragment;
    }

    public LeakedFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leak_check, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();

        TextView fragmentHash = (TextView) root.findViewById(R.id.fragment_hash_text);
        fragmentHash.setText("fragment hash : " + this.hashCode());

        TextView logText = (TextView) root.findViewById(R.id.log_text);
        TextViewAppendLogger appendLogger =
                new TextViewAppendLogger(logText);
        LeakChecker.setLogger(appendLogger);
        logText.setMovementMethod(ScrollingMovementMethod.getInstance());

        root.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory
                        .decodeResource(getResources(), R.drawable.ic_launcher);
                LeakChecker.addLeakChecker(bitmap);
                leakObjectList.add(bitmap);
            }
        });

        root.findViewById(R.id.dump_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeakChecker.dump();
            }
        });
    }
}
