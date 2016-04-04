package com.example.android;

/**
 * Created by vijayitagumber on 09-03-2016.
 */
import java.util.List;

//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

//@Document(collection = "student")
public class Student {
   // @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String firstName;
    private String lastName;

    private String email;
    private String rollno;
    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    private List<String> subjects;

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getFirstName())
                .append(", ")
                .append(this.getLastName())
                .append(", ")
                .append(this.getEmail())
                .append(", ")
                .append(this.getRollno());
        //.append(", ")
        //.append(this.getSubjects().toString());

        return builder.toString();
    }

}
