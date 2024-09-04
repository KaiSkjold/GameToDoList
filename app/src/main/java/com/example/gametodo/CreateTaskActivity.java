package com.example.gametodo;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;

import com.example.gametodo.enums.PlaceOfTaskEnum;
import com.example.gametodo.enums.TypeOfTaskEnum;

import java.time.LocalDate;
import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_READ_MEDIA_IMAGES = 2;

    private static final String DATE_PICKER_TAG = "datePicker";
    private static final String DEADLINE_PICKER_TAG = "deadlinePicker";

    private TextView dateTextView;
    private TextView deadlineTextView;
    private ImageView selectedImageView;
    private Button selectPhotoBtn;
    private Button createTaskBtn;
    private Button closeBtn;
    private Button pickDateBtn;
    private Button pickDeadlineBtn;
    private CheckBox checkRepeatable;
    private CheckBox checkPriority;
    private EditText title;
    private EditText description;
    private Spinner spinnerArea;
    private Spinner spinnerType;

    //    variables for creating task
    String todoItemTitle = "";
    String todoItemDescription = "";
    LocalDate todoItemStartDate = null;
    LocalDate todoItemDeadline = null;
    Boolean toDoItemIsPriority = false;
    Boolean toDoItemIsRepeatable = false;
    String toDoItemPlaceOfTask = null;
    String toDoItemTypeOfTask = null;


    // Define the ActivityResultLauncher for selecting photos
    private ActivityResultLauncher<Intent> selectPhotoLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_task_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (allPermissionsGranted()) {
            openGallery();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                    REQUEST_CODE_READ_MEDIA_IMAGES);
        }



        initGui();
    }

    void initGui() {
        // Initialise views
        dateTextView = findViewById(R.id.date_text);
        deadlineTextView = findViewById(R.id.deadline_text);
        selectedImageView = findViewById(R.id.task_image);
        selectPhotoBtn = findViewById(R.id.add_photo);
        createTaskBtn = findViewById(R.id.create_task_and_submit_btn);
        closeBtn = findViewById(R.id.close_btn);
        pickDateBtn = findViewById(R.id.pick_date_btn);
        pickDeadlineBtn = findViewById(R.id.pick_deadline_btn);
        checkRepeatable = findViewById(R.id.check_repeatable);
        checkPriority = findViewById(R.id.check_priority);
        title = findViewById(R.id.task_title);
        description = findViewById(R.id.task_description);
        spinnerArea = findViewById(R.id.spinner_select_area);
        spinnerType = findViewById(R.id.spinner_select_type);

        // Cancel and close buttons
        closeBtn.setOnClickListener(view -> finish());

        // Spinners and spins
        spinnerArea.setAdapter(new ArrayAdapter<PlaceOfTaskEnum>(this, android.R.layout.simple_spinner_dropdown_item, PlaceOfTaskEnum.values()));

        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toDoItemPlaceOfTask = spinnerArea.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Handle the case where no item is selected if necessary
            }
        });

        spinnerType.setAdapter(new ArrayAdapter<TypeOfTaskEnum>(this, android.R.layout.simple_spinner_dropdown_item, TypeOfTaskEnum.values()));

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toDoItemTypeOfTask = spinnerType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Handle the case where no item is selected if necessary
            }
        });

        // checkboxies
        checkRepeatable.setOnClickListener(view -> {
            toDoItemIsRepeatable = checkRepeatable.isChecked();
        });

        checkPriority.setOnClickListener(view -> {
            toDoItemIsPriority = checkPriority.isChecked();
        });

        // pick a date buttons
        pickDateBtn.setOnClickListener(v -> {
            DatePickerFragment newFragment = DatePickerFragment.newInstance(DATE_PICKER_TAG);
            newFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
        });

        // Pick deadline buttons
        pickDeadlineBtn.setOnClickListener(v -> {
            DatePickerFragment newFragment = DatePickerFragment.newInstance(DEADLINE_PICKER_TAG);
            newFragment.show(getSupportFragmentManager(), DEADLINE_PICKER_TAG);
        });

        // Ensure permission is requested correctly
        selectPhotoBtn.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        REQUEST_CODE_READ_MEDIA_IMAGES);
            } else {
                openGallery();
            }
        });


        createTask();
    }


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            selectPhotoLauncher.launch(intent);
        } else {
            // Handle the case where no app can handle the intent
            Toast.makeText(this, "No application can handle this request. Please install a gallery app.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean allPermissionsGranted() {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    // Handle the permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_MEDIA_IMAGES) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open the gallery
                openGallery();
            } else {
                Toast.makeText(this, "Permission denied to read external storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onDateSelected(String tag, int year, int month, int day) {
        String selectedDate = year + "-" + (month + 1) + "-" + day;
        LocalDate dateSave = LocalDate.of(year, month + 1, day);
        if (DATE_PICKER_TAG.equals(tag)) {
            todoItemStartDate = dateSave;
            dateTextView.setText("Selected Date: " + selectedDate);
        } else if (DEADLINE_PICKER_TAG.equals(tag)) {
            todoItemDeadline = dateSave;
            deadlineTextView.setText("Selected Deadline: " + selectedDate);
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        private static final String ARG_TAG = "tag";

        static DatePickerFragment newInstance(String tag) {
            DatePickerFragment fragment = new DatePickerFragment();
            Bundle args = new Bundle();
            args.putString(ARG_TAG, tag);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(requireContext(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String tag = getArguments() != null ? getArguments().getString(ARG_TAG) : "";
            ((CreateTaskActivity) getActivity()).onDateSelected(tag, year, month, day);
        }
    }

    void createTask() {
        createTaskBtn.setOnClickListener(view -> {
            todoItemTitle = String.valueOf(title.getText());
            todoItemDescription = String.valueOf(description.getText());

            // Create the ToDoItem and add it to the taskList
            ToDoItem newItem = new ToDoItem(
                    todoItemTitle,
                    todoItemDescription,
                    todoItemStartDate,
                    todoItemDeadline,
                    toDoItemIsPriority,
                    toDoItemIsRepeatable,
                    PlaceOfTaskEnum.valueOf(toDoItemPlaceOfTask),
                    TypeOfTaskEnum.valueOf(toDoItemTypeOfTask)
            );

            // Pass the new item back to MainActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("newTask", newItem);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

    }


}
