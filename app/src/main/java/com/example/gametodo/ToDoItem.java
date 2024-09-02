package com.example.gametodo;


import java.util.Date;
import java.util.List;
import java.util.UUID;

enum TypeOfTask {CLEANING,COOKING,GARDENING,PLANNING}
enum PlaceOfTask {KITCHEN, BATHROOM, LIVINGROOM, BEDROOM, HALLWAY, OUTSIDE}

public class ToDoItem {

    // ID of task
    UUID id = java.util.UUID.randomUUID();

    // Main Task
    String title;
    String description;

    // Dates and time, Maybe LocalDateTime
    Date startDate;
    Date completionDate;
    Date deadline;

    // Priority and importance of task
    Boolean isPriority;
    Boolean isRepeatable;

    // Types and places of tasks
    TypeOfTask typeOfTask;
    PlaceOfTask placeOfTask;

    // Coins awarded for a task
    int minCoinsAwarded;
    int maxCoinsAwarded;

    // Contains both filename and uri
    String photo;

    // If tasks area blocked by other tasks
    List<ToDoItem> toDoItemListBlockers;
}
