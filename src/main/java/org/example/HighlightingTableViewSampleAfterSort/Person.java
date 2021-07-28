package org.example.HighlightingTableViewSampleAfterSort;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty email;

    Person(String fName, String lName, String email) {
        this.firstName = new SimpleStringProperty(this, "firstName", fName);
        this.lastName = new SimpleStringProperty(this, "lastName", lName);
        this.email = new SimpleStringProperty(this, "email", email);
    }

    public final String getFirstName() {
        return firstName.get();
    }

    public final void setFirstName(String fName) {
        firstName.set(fName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public final String getLastName() {
        return lastName.get();
    }

    public final void setLastName(String fName) {
        lastName.set(fName);
    }

    public final StringProperty lastNameProperty() {
        return lastName;
    }

    public final String getEmail() {
        return email.get();
    }

    public final void setEmail(String fName) {
        email.set(fName);
    }

    public final StringProperty emailProperty() {
        return email;
    }

    @Override
    public String toString() {
        String result = "Person{" +
                "firstName=" + getFirstName() +
                ", lastName=" + getLastName() +
                ", email=" + getEmail() +
                '}';

        return result;
    }
}
