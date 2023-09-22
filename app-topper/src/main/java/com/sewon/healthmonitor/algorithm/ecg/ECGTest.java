package com.sewon.healthmonitor.algorithm.ecg;


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
    //List<ECG_ES_Object> listECG = new ArrayList<ECG_ES_Object>(); //ES서버에 전송하기 위한 데이터 구조

    double[] array = listDouble.stream().mapToDouble(i -> i).toArray();

    ECGTopper listECGResult = ECGAnalysisProc.ECG_PPG_AnalysisData(array);

    System.out.println("asdfasdf");

//
//    try {
//
//
//      //br = new BufferedReader(new InputStreamReader(new FileInputStream(sFileName), "UTF-8"));
//      //Charset.forName("UTF-8");
//
//      String line = "";
//      String temp = "";
//
//      while ((line = br.readLine()) != null) { //EOF가 아닐때 까지
//        //if (!line.replace("\"", "").trim().equals(",")){
//        //	temp = line.substring(0, 4).trim();
//        //	int iLen = temp.length();
//        //	if(temp.toUpperCase().equals("TIME"))
//        //		break;
//        //}
//        String[] strTemp = line.replace("\"", "").trim().split(",");
//        temp = strTemp[0].trim();
//        int iLen = temp.length();
//        if (!strTemp[0].trim().equals("TIME")) break;
//      }
//
//      int iCnt = 0, count = 0;
//      String strSubject = "";
//      String strType = "";
//
//      while ((line = br.readLine()) != null) //EOF가 아닐때 까지
//      {
//        String[] strTemp = line.replace("\"", "").trim().split(",");
//        //시간데이터가 있다면.
//        if (strTemp.length == 0) {
//          break;
//        }
//        if (!strTemp[0].equals("")) {
//          //ES 서버에 전송하기 위한 데이터 구조
//          //ECG_ES_Object node = new ECG_ES_Object();
//          //node.strTime = strTemp[0];
//
//          ECGObject node = new ECGObject();
//          node.dTime = Double.parseDouble(strTemp[0]);
//          node.dHRVal = Double.parseDouble(strTemp[1]);
//
//          listECG.add(node);
//        }
//      }
////            Double[] newArr = new Double[listECG.size()];
////						listECG.toArray(new ECGObject[0]);
//    } catch (IOException e) {
//      e.printStackTrace();
//    } finally {
//      // BufferedReader FileReader를 닫아준다.
//      if (br != null) try {
//        br.close();
//      } catch (IOException e) {
//      }
//      if (fr != null) try {
//        fr.close();
//      } catch (IOException e) {
//      }
//    }

  }

}

