package Server.model.People;

import Server.model.User;

public class NormalPeople extends Person{
    public NormalPeople(User owner) {
        super(owner, 10);
    }
}