package com.example.gametodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<ToDoItem> {
    private List<ToDoItem> taskList;
    private Context context;

    public TaskAdapter(@NonNull Context context, int resource, @NonNull List<ToDoItem> taskList) {
        super(context, resource, taskList);

        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View taskListItem = convertView;

        if (taskListItem == null)
            taskListItem = LayoutInflater.from(context).inflate(R.layout.todoitem_list, parent, false);


        ToDoItem currentTask = taskList.get(position);

        TextView title = taskListItem.findViewById(R.id.task_title);
        title.setText(currentTask.title);

        TextView description = taskListItem.findViewById(R.id.task_description);
        description.setText(currentTask.description);

        TextView taskDate = taskListItem.findViewById(R.id.task_date);
        taskDate.setText(String.valueOf(currentTask.startDate));

        TextView deadline = taskListItem.findViewById(R.id.task_deadline);
        deadline.setText(String.valueOf(currentTask.deadline));

        TextView taskArea = taskListItem.findViewById(R.id.task_area);
        taskArea.setText(String.valueOf(currentTask.placeOfTask));

        TextView taskType = taskListItem.findViewById(R.id.task_type);
        taskType.setText(String.valueOf(currentTask.typeOfTask));

        TextView taskRepeatable = taskListItem.findViewById(R.id.task_repeatable);
        taskRepeatable.setText(String.valueOf(currentTask.isRepeatable));

        TextView taskPriority = taskListItem.findViewById(R.id.task_priority);
        taskPriority.setText(String.valueOf(currentTask.isPriority));


        return taskListItem;
    }
}
