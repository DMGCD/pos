package TM;

public class employemsgTM {
    public employemsgTM() {
    }

    public employemsgTM(String discription, String empid) {
        this.setDiscription(discription);
        this.setEmpid(empid);
    }

    private String discription;

    public employemsgTM(String discription, String empid, int nid) {
        this.discription = discription;
        this.empid = empid;
        this.nid = nid;
    }

    private String empid;
    private int nid;


    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    @Override
    public String toString() {
        return discription;
    }
}
