package com.sewon.topperhealth.service.algorithm.ecg;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ECGTest {

  public static void main(String[] args) {
    String COMMA_DELIMITER = ",";
    List<Double> listDouble = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader("rri.csv"))) {
      String line;
      while ((line = br.readLine()) != null) {
        listDouble.add(Double.parseDouble(line));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    double[] array = listDouble.stream().mapToDouble(i -> i).toArray();
    ECGTopper listECGResult = ECGAnalysisProc.ECG_PPG_AnalysisData(array);
    System.out.println("asdfasdf");
  }
}

