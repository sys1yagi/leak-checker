package com.sys1yagi.android.leakchecker.develop.fragments;

import com.sys1yagi.android.leakchecker.MemoryUsage;
import com.sys1yagi.android.leakchecker.develop.R;
import com.sys1yagi.android.leakchecker.develop.views.TextViewMonitorLogger;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MemoryUsageFragment extends Fragment {

    public static MemoryUsageFragment newInstance() {
        MemoryUsageFragment fragment = new MemoryUsageFragment();
        return fragment;
    }

    private GridView imageGrid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MemoryUsage.startPrintMemoryUsage();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MemoryUsage.stopPrintMemoryUsage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_memory_usage, null, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageGrid = (GridView) getView().findViewById(R.id.image_grid);
        final ArrayAdapter<Bitmap> adapter = new ArrayAdapter<Bitmap>(getActivity(), -1) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    ImageView imageView = new ImageView(getContext());
                    convertView = imageView;
                }

                Bitmap bitmap = getItem(position);
                ((ImageView) convertView).setImageBitmap(bitmap);

                return convertView;
            }
        };
        imageGrid.setAdapter(adapter);

        getView().findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap src = BitmapFactory
                        .decodeResource(getResources(), R.drawable.ic_launcher);
                Bitmap bitmap = Bitmap.createBitmap(src);
                adapter.add(bitmap);
                adapter.notifyDataSetChanged();
            }
        });

        TextViewMonitorLogger logger = new TextViewMonitorLogger(
                (TextView) getView().findViewById(R.id.usage_text));
        MemoryUsage.setLogger(logger);

    }
}
