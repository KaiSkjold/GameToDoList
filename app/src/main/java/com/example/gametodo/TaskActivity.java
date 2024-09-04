package com.example.gametodo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TaskActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    String url = "http://192.168.0.104:8989/todoitem";

    private static final int REQUEST_CODE_CREATE_TASK = 1;
    ListView taskList;
    TaskAdapter myAdapter;
    List<ToDoItem> tasks = ToDoItemData.addTasks();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_task);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_task), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        requestQueue = Volley.newRequestQueue(this);
//        getToDoItems();

        initGui();
    }

    void initGui(){
        taskList = findViewById(R.id.task_list);
        myAdapter = new TaskAdapter(
                this, 0, tasks);
        taskList.setAdapter(myAdapter);

        Button homeBtn = findViewById(R.id.home_btn);
        homeBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });

        com.google.android.material.floatingactionbutton.FloatingActionButton createBtn = findViewById(R.id.create_btn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateTaskActivity.class);
                startActivity(intent);
            }
        });

        Button shopBtn = findViewById(R.id.shop_btn);
        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
                startActivity(intent);
            }
        });
    }

    // Get the created task from the create task activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CREATE_TASK && resultCode == RESULT_OK) {

            ToDoItem newTask = (ToDoItem) data.getSerializableExtra("newTask");
            if (newTask != null) {
                createToDoItem(newTask);
                tasks.add(newTask);
                myAdapter.notifyDataSetChanged();
            }
        }
    }

    void getToDoItems() {
        StringRequest stringRequest
                = new StringRequest(
                Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Type listType = new TypeToken<List<ToDoItem>>() {}.getType();
                        List<ToDoItem> toDoItemList = new Gson().fromJson(response, listType);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("Volley", "onErrorResponse", error);
                    }
                });

        requestQueue.add(stringRequest);
    }

    void createToDoItem(ToDoItem item){
//        ToDoItem todoItem = new ToDoItem();
        ToDoItem toDoItem = item;

        Map<String,String> params = new HashMap<String, String>();
        params.put("toDoItemObject", new Gson().toJson(toDoItem));

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        requestQueue.add(request);
    }

}