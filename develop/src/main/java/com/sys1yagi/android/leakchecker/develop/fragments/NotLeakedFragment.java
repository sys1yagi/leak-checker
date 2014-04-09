package com.sys1yagi.android.leakchecker.develop.fragments;


import com.sys1yagi.android.leakchecker.LeakChecker;
import com.sys1yagi.android.leakchecker.develop.R;
import com.sys1yagi.android.leakchecker.develop.views.TextViewAppendLogger;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class NotLeakedFragment extends Fragment {

    private final static List<Bitmap> localObjectList
            = new ArrayList<Bitmap>();

    public static NotLeakedFragment newInstance() {
        NotLeakedFragment fragment = new NotLeakedFragment();

        return fragment;
    }

    public NotLeakedFragment() {

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
                localObjectList.add(bitmap);
            }
        });

        root.findViewById(R.id.dump_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeakChecker.dump();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        localObjectList.clear();
    }
}
