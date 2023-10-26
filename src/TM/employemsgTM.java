package TM;

public class employemsgTM {
    public employemsgTM() {
    }

    private String discription;

    public employemsgTM(String discription) {
        this.discription = discription;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    @Override
    public String toString() {
        return discription;
    }
}
