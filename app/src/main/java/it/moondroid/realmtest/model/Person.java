package it.moondroid.realmtest.model;

import io.realm.RealmObject;

/**
 * Created by marco.granatiero on 26/01/2015.
 */

// Your model just have to extend RealmObject.
// This will inherit an annotation which produces proxy getters and setters for all fields.
public class Person extends RealmObject {

    // All fields are by default persisted.
    private String name;
    private int age;

    // The standard getters and setters your IDE generates are fine.
    // Realm will overload them and code inside them is ignored.
    // So if you prefer you can also just have empty abstract methods.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
