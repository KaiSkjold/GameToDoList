package com.example.gametodo;


import com.example.gametodo.enums.PlaceOfTaskEnum;
import com.example.gametodo.enums.TypeOfTaskEnum;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class ToDoItem implements Serializable {

    public ToDoItem(String title,
                    String description,
                    LocalDate startDate,
                    LocalDate deadline,
                    Boolean isPriority,
                    Boolean isRepeatable,
                    PlaceOfTaskEnum placeOfTask,
                    TypeOfTaskEnum typeOfTask) {
        this.description = description;
        this.isPriority = isPriority;
        this.isRepeatable = isRepeatable;
        this.title = title;
        this.startDate = startDate;
        this.deadline = deadline;
        this.placeOfTask = placeOfTask;
        this.typeOfTask = typeOfTask;
    }

    // ID of task
    //  UUID id = java.util.UUID.randomUUID();
    int id;

    // Main Task
    String title;
    String description;

    // Dates and time, Maybe LocalDateTime
    LocalDate startDate;
    LocalDate completionDate;
    LocalDate deadline;

    // Priority and importance of task
    Boolean isPriority;
    Boolean isRepeatable;

    // Types and places of tasks
    TypeOfTaskEnum typeOfTask;
    PlaceOfTaskEnum placeOfTask;


    // Coins awarded for a task
    int coinsAwarded;

    // Contains both filename and uri
    String photo;

    // If tasks area blocked by other tasks
    List<ToDoItem> toDoItemListBlockers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getPriority() {
        return isPriority;
    }

    public void setPriority(Boolean priority) {
        isPriority = priority;
    }

    public Boolean getRepeatable() {
        return isRepeatable;
    }

    public void setRepeatable(Boolean repeatable) {
        isRepeatable = repeatable;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public PlaceOfTaskEnum getPlaceOfTask() {
        return placeOfTask;
    }

    public void setPlaceOfTask(PlaceOfTaskEnum placeOfTask) {
        this.placeOfTask = placeOfTask;
    }

    public TypeOfTaskEnum typeOfTask() {
        return typeOfTask;
    }

    public void setTypeOfTask(TypeOfTaskEnum typeOfTask) {
        this.typeOfTask = typeOfTask;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ToDoItem> getToDoItemListBlockers() {
        return toDoItemListBlockers;
    }

    public void setToDoItemListBlockers(List<ToDoItem> toDoItemListBlockers) {
        this.toDoItemListBlockers = toDoItemListBlockers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public int getCoinsAwarded() {
        return coinsAwarded;
    }

    public void setCoinsAwarded(int coinsAwarded) {
        this.coinsAwarded = coinsAwarded;
    }
}
