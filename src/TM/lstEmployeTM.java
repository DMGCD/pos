package TM;

public class lstEmployeTM {
    private  String empid;
    private  String name;



    private  String phone;

    public lstEmployeTM() {
    }
    public String getEmpid() {
        return empid;
    }

    public lstEmployeTM(String empid, String name, String phone) {
        this.empid = empid;
        this.name = name;
        this.phone = phone;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return name;
    }
}
