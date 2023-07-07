package sewon.algorithm;

public class ECGObject implements Comparable<ECGObject> {
    //시간
    public double dTime;
    public double dBRVal;
    public double dHRVal;

    public ECGObject() {
        this.dTime = 0.0;
        this.dBRVal = 0.0;
        this.dHRVal = 0.0;
    }

    public ECGObject(double time, double BRvalue, double HRvalue) {
        this.dTime = time;
        this.dBRVal = BRvalue;
        this.dHRVal = HRvalue;
    }

    public double getTime() {
        return dTime;
    }

    public void setTime(double time) {
        this.dTime = time;
    }

    public double getBRVal() {
        return dBRVal;
    }

    public double getHRVal() {
        return dHRVal;
    }

    public void setBRVal(double val) {
        this.dBRVal = val;
    }

    public void setHRVal(double val) {
        this.dHRVal = val;
    }

    @Override
    public int compareTo(ECGObject obj) {
        if (dTime == obj.getTime())
            return 1;
        else
            return 0;
    }
}
