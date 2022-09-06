package com.example.part6_18;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class Lab18_4Activity extends AppCompatActivity implements View.OnClickListener{
    Button btn;
    CoordinatorLayout coordinatorLayout;
    private BottomSheetBehavior<View> persistenBottomSheet;
    private BottomSheetDialog modalBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab18_4);

        coordinatorLayout = findViewById(R.id.lab4_coordinator);
        btn = findViewById(R.id.lab4_button);
        btn.setOnClickListener(this);

        initPersistentBottomSheet();
    }

    @Override
    public void onClick(View view) {
        createDialog();
    }

    private void createDialog()
    {
        List<DataVO> list = new ArrayList<>();
        DataVO vo = new DataVO();
        vo.title = "Keep";
        vo.image = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_lab4_1, null);
        list.add(vo);

        vo = new DataVO();
        vo.title = "Inbox";
        vo.image = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_lab4_2, null);
        list.add(vo);

        vo = new DataVO();
        vo.title = "Messanger";
        vo.image = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_lab4_3, null);
        list.add(vo);

        vo = new DataVO();
        vo.title = "Google+";
        vo.image = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_lab4_4, null);
        list.add(vo);

        Lab4RecyclerViewAdapter adapter = new Lab4RecyclerViewAdapter(list);
        View view = getLayoutInflater().inflate(R.layout.lab4_modal_sheet, null);
        RecyclerView recyclerView = view.findViewById(R.id.lab4_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        modalBottomSheet = new BottomSheetDialog(this);
        modalBottomSheet.setContentView(view);
        modalBottomSheet.show();
    }

    private void initPersistentBottomSheet()
    {
        View bottomSheet = coordinatorLayout.findViewById(R.id.lab4_bottom_sheet);
        persistenBottomSheet = BottomSheetBehavior.from(bottomSheet);
    }
}