package sewon.algorithm;

import sewon.algorithm.ECG_PROC.ECG_ANALYSIS_PROC;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JAVA_ECG_ANALYSIS {

  public static void main(String[] args) throws FileNotFoundException {
    List<ECGObject> listECG = new ArrayList<ECGObject>();
    String COMMA_DELIMITER = ",";
    List<List<String>> records = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader("sewon.csv"))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split(COMMA_DELIMITER);
        records.add(Arrays.asList(values));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    //List<ECG_ES_Object> listECG = new ArrayList<ECG_ES_Object>(); //ES서버에 전송하기 위한 데이터 구조

    try {


      //br = new BufferedReader(new InputStreamReader(new FileInputStream(sFileName), "UTF-8"));
      //Charset.forName("UTF-8");

      String line = "";
      String temp = "";

      while ((line = br.readLine()) != null) { //EOF가 아닐때 까지
        //if (!line.replace("\"", "").trim().equals(",")){
        //	temp = line.substring(0, 4).trim();
        //	int iLen = temp.length();
        //	if(temp.toUpperCase().equals("TIME"))
        //		break;
        //}
        String[] strTemp = line.replace("\"", "").trim().split(",");
        temp = strTemp[0].trim();
        int iLen = temp.length();
        if (!strTemp[0].trim().equals("TIME"))
          break;
      }

      int iCnt = 0, count = 0;
      String strSubject = "";
      String strType = "";

      while ((line = br.readLine()) != null) //EOF가 아닐때 까지
      {
        String[] strTemp = line.replace("\"", "").trim().split(",");
        //시간데이터가 있다면.
        if (strTemp.length == 0) {
          break;
        }
        if (!strTemp[0].equals("")) {
          //ES 서버에 전송하기 위한 데이터 구조
          //ECG_ES_Object node = new ECG_ES_Object();
          //node.strTime = strTemp[0];

          ECGObject node = new ECGObject();
          node.dTime = Double.parseDouble(strTemp[0]);
          node.dHRVal = Double.parseDouble(strTemp[1]);

          listECG.add(node);
        }
      }
//            Double[] newArr = new Double[listECG.size()];
//						listECG.toArray(new ECGObject[0]);
//            List<ECG_RESULT> listECGResult = ECG_ANALYSIS_PROC.ECG_PPG_AnalysisData();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      // BufferedReader FileReader를 닫아준다.
      if (br != null) try {
        br.close();
      } catch (IOException e) {
      }
      if (fr != null) try {
        fr.close();
      } catch (IOException e) {
      }
    }

  }

}

