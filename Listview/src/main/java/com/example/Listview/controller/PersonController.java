package com.example.Listview.controller;

import com.example.Listview.model.Person;
import com.example.Listview.repository.PersonRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.text.html.Option;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class PersonController implements Initializable {

    private final PersonRepository repository;

    @FXML
    private ListView<Person> listview;

    @FXML
    private Button btnNew;

    @FXML
    private Text txtFirstName;

    @FXML
    private TextField txtFieldFirst;

    @FXML
    private TextField txtFieldLast;

    @FXML
    private TextArea txtAreaNotes;

    private ObservableList view = FXCollections.observableArrayList();



    @Autowired
    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showList();
        listview.setItems(view);
    }

    public void showList(){
        view.addAll(repository.findAll());
    }

    @FXML
    public void addPerson(){
        String firstName = txtFieldFirst.getText();
        String lastName = txtFieldLast.getText();
        String notes = txtAreaNotes.getText();

        if (validate()){
            txtFirstName.setText("Please Fill The First Name");
        }else {
            Person p = new Person(firstName,lastName,notes);
            repository.save(p);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Success");
            alert.show();
            refreshList();
        }
    }

    private void refreshList(){
        view.clear();
        showList();
    }

    @FXML
    public void deletePerson(){
        Person person = listview.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            repository.delete(person);
            clearText();
        }
        view.clear();
        showList();
    }

    @FXML
    public void updatePerson(){
        Person person = listview.getSelectionModel().getSelectedItem();

        person.setFirstName(txtFieldFirst.getText());
        person.setLastName(txtFieldLast.getText());
        person.setNotes(txtAreaNotes.getText());

        repository.save(person);
    }

    @FXML
    public void handleMouseAction(MouseEvent event){
        Person person = listview.getSelectionModel().getSelectedItem();

        if (person == null){
            unselectModel(event);
        }else {
            txtFieldFirst.setText(person.getFirstName());
            txtFieldLast.setText(person.getLastName());
            txtAreaNotes.setText(person.getNotes());
        }
    }

    @FXML
    public void unselectModel(MouseEvent event){
        listview.getSelectionModel().clearSelection();
        clearText();
    }

    @FXML
    private void clearText(){
        txtFirstName.setText("");
        txtFieldFirst.clear();
        txtFieldLast.clear();
        txtAreaNotes.clear();
    }

    private boolean validate(){
        return txtFieldFirst.getText().isEmpty();
    }

    @FXML
    private void clearL(){
    }
}
