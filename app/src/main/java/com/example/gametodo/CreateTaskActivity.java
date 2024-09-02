package com.example.gametodo;


import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class CreateTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_task);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_task_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initGui();
    }
    void initGui(){
        Button createTaskBtn = findViewById(R.id.create_task_btn);
        createTaskBtn.setOnClickListener(view -> {
            // TODO send data
        });

        Button closeBtn = findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(view -> {
            finish();
        });

        Button cancelBtn = findViewById(R.id.cancel_task_btn);
        cancelBtn.setOnClickListener(view -> {
            finish();
        });
    }

//    public static class closeTaskMsg extends DialogFragment {
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            // Use the Builder class for convenient dialog construction.
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setMessage("Do you want to close create task?")
//                    .setPositiveButton("Close create task", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            finish();
//                        }
//
//                    })
//                    .setNegativeButton("Return to task", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                        }
//                    });
//            // Create the AlertDialog object and return it.
//            return builder.create();
//        }
//    }

}