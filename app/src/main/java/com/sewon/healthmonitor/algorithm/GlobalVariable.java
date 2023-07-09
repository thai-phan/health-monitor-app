package com.sewon.healthmonitor.algorithm;

public class GlobalVariable {
  private static double dRThreshold; //R 값에 대한 Threshold
  private static double dRTime; //R 값에 대한 Time
  private static double dRVal; //R 값에 대한 Peak

  public GlobalVariable() {
    this.dRThreshold = 0.0;
    this.dRTime = 0.0;
    this.dRVal = 0.0;
  }

  public static double getRThreshold() {
    return dRThreshold;
  }

  public static void setRThreshold(double dVal) {
    dRThreshold = dVal;
  }

  public static double getRTime() {
    return dRTime;
  }

  public static void setRTime(double dVal) {
    dRTime = dVal;
  }

  public static double getRVal() {
    return dRVal;
  }

  public static void setRVal(double dVal) {
    dRVal = dVal;
  }
}
