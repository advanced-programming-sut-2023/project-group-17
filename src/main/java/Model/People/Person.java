package Model.People;

import Model.User;

public class Person {
    private User owner;

    public Person(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }
}
