package org.example.todoapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.ArrayList;

enum Priority {
    High,
    Medium,
    Low
}

class Task {
    private final String taskName;
    private final Priority taskPriority;
    private boolean taskCompleted;
    private final String taskDescription;
    public Task(String taskName, Priority taskPriority, String taskDescription) {
        this.taskName = taskName;
        this.taskPriority = taskPriority;
        this.taskCompleted = false;
        this.taskDescription = taskDescription;
    }
    public String getName() {
        return taskName;
    }
    public Priority getPriority() {
        return taskPriority;
    }
    public String getTaskDescription(){
        return taskDescription;
    }

    public boolean isCompleted() {
        return taskCompleted;
    }

    public void setCompleted(boolean completed) {
        this.taskCompleted = completed;
    }

    @Override
    public String toString() {
        return "(" + (taskCompleted ? "✔" : " ") + ") " + taskName + " - Priority: " + taskPriority + " - Description: " + taskDescription + "\n";
    }
}

public class todoController implements Initializable {
    public TextField taskText;
    @FXML
    ComboBox <Priority> priorityText;
    public TextField descriptionText;
    public TextField updateNumber;
    public TextField updateTaskText;
    public TextField updateDescriptionText;
    @FXML
    ComboBox <Priority> updatePriorityText;
    public TextField removeNumber;
    public TextField markNumber;
    public Button taskButton;
    public Button removeButton;
    public Button markButton;
    public Button updateButton;

    public TextArea listOfTasks;
    public TextArea errorHandle;
    private ArrayList<Task> tasks;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set Initial Values for Printing in Prompt
        taskText.setPromptText("Enter the Task Name");
        priorityText.getItems().addAll(Priority.values());
        updatePriorityText.getItems().addAll(Priority.values());
        descriptionText.setPromptText("Enter the Description of Task");
        removeNumber.setPromptText("Enter a Line Number");
        markNumber.setPromptText("Enter a Line Number");
        updateNumber.setPromptText("Enter a Line Number");
        updateTaskText.setPromptText("Enter the Updated Task");
        updateDescriptionText.setPromptText("Enter the Description of Task");
        taskButton.setOnAction(ae -> {
            try{
                addNewTaskButton(ae);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        removeButton.setOnAction(ae -> {
            try{
                removeTaskButton(ae);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        markButton.setOnAction(ae -> {
            try{
                markAsCompleteButton(ae);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        updateButton.setOnAction(ae -> {
            try {
                updateDataButton(ae);
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        });

    }

    //Constructor for Todo List

    public todoController(){
        tasks = new ArrayList<>();
    }

    //Mark as Complete Button
    @FXML
    private void markAsCompleteButton(ActionEvent ae) throws IOException{
        if(!(Integer.parseInt(markNumber.getText()) <= tasks.size())){
            errorHandle.setText("Enter the Mark Number in Range of Task Length");
        }
        else {
            int index = Integer.parseInt(markNumber.getText());
            Task task = tasks.get(index);
            task.setCompleted(true);
            listOfTasks.setText(tasks.toString().replace("[","").replace("]","").replace(",",""));
            markNumber.clear();
            errorHandle.clear();
        }
    }

    //Remove Task Button
    @FXML
    private void removeTaskButton(ActionEvent ae) throws IOException{
        if (!(Integer.parseInt(removeNumber.getText()) <= tasks.size())){
            errorHandle.setText("Enter the Remove Number in Range of Task Length");
        }
        else{
            int index = Integer.parseInt(removeNumber.getText());
            tasks.remove(index);
            listOfTasks.setText(tasks.toString().replace("[","").replace("]","").replace(",",""));
            removeNumber.clear();
            errorHandle.clear();
        }
    }

    //Adding New task Button
    @FXML
    private void addNewTaskButton(ActionEvent ae) throws IOException {
        if(Objects.equals(taskText.getText(),null) || Objects.equals(priorityText.getValue(),null) || Objects.equals(descriptionText.getText(),null)){
            errorHandle.setText("Please Enter the Details in Add Task Correctly");
        }
        else{
            Task newTask = new Task(taskText.getText(), priorityText.getValue(), descriptionText.getText());
            tasks.add(newTask);
            listOfTasks.setText(tasks.toString().replace("[","").replace("]","").replace(",",""));
            taskText.clear();
            descriptionText.clear();
            errorHandle.clear();
        }
    }

    //Updating the Task Button
    @FXML
    private void updateDataButton(ActionEvent ae) throws IOException{
        if(!(Integer.parseInt(updateNumber.getText()) <= tasks.size()) || Objects.equals(updateTaskText.getText(),null) || Objects.equals(updatePriorityText.getValue(),null) || Objects.equals(updateDescriptionText.getText(),null)){
            errorHandle.setText("Enter the Details in Update Properly");
        }
        else {
            Task updatedTaskList = new Task(updateTaskText.getText(), updatePriorityText.getValue(), updateDescriptionText.getText());
            listOfTasks.setText(String.valueOf(tasks.set(Integer.parseInt(updateNumber.getText()), updatedTaskList)));
            listOfTasks.setText(tasks.toString().replace("[","").replace("]","").replace(",",""));
            updateTaskText.clear();
            updateDescriptionText.clear();
            errorHandle.clear();
        }
    }
}