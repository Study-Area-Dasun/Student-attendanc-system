package lk.ijse.dep10.model;

import java.io.Serializable;
import java.sql.Blob;

public class Student implements Serializable {
    String id;
    String name;
    Blob profile_picture;

    public Student(String id, String name, Blob profile_picture) {
        this.id = id;
        this.name = name;
        this.profile_picture = profile_picture;
    }

    public Student() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Blob getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(Blob profile_picture) {
        this.profile_picture = profile_picture;
    }
}
