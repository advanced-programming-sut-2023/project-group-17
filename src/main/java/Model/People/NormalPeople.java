package Model.People;

import Model.User;

public class NormalPeople extends Person{
    private int endurance;
    public NormalPeople(User owner, int endurance) {
        super(owner);
        this.endurance = endurance;
    }

    public int getEndurance() {
        return endurance;
    }

    public void changeEndurance(int amount) {
        this.endurance += amount;
    }
}