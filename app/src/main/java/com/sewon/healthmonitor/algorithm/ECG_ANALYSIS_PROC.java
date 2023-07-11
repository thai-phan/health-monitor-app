package com.sewon.healthmonitor.algorithm;


import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ECG_ANALYSIS_PROC {

  //Input: 레이더 센서의 rri 데이터 리스트를 받아서, 주파수 분석을 한다.
  //Output: 수면시간동안의 주파수 분석 데이터를 리턴한다.
  public static ECG_TOPPER ECG_PPG_AnalysisData(double[] rriData) {
    ECG_TOPPER resultNode = new ECG_TOPPER();
    try {

      //TOPPER 클래스를 계산한다.
      //RRI 표준편차 (전체 수면시간)
      resultNode.SDRP = CalcStdDev(rriData, CalcRRIAvg(rriData));
      //RRI의 연속적 차이를 제곱 -> 평균 -> 제곱근
      resultNode.RMSSD = CalcRMSSD(rriData);

      //주파수분석 후 계산하는 항목
      //밴드패스필터를 통과하고 나온 신호로, 주파수 분석을 한다.
      ECG_STRESS stressNode = new ECG_STRESS();
      stressNode = CalcFrequency(rriData);

      resultNode.LF = stressNode.LF;
      resultNode.HF = stressNode.HF;
      resultNode.LF_HF_Ratio = stressNode.LF / stressNode.HF;
      resultNode.StressScore = stressNode.StressScore;

    } catch (Exception e) {
      e.printStackTrace();
    }

    //최종 결과를 담은 리스트를 리턴한다.
    return resultNode;
  }


  public static double CalcRRIAvg(double[] rriData) {
    double sum = 0;
    for (int i = 0; i < rriData.length; i++) {
      sum += rriData[i];
    }
    return sum / rriData.length;
  }

  public static double CalcStdDev(double[] rriData, double meanValue) {
    if (rriData.length < 2) return Double.NaN;

    double sum = 0.0;
    double sd = 0.0;
    double diff;

    for (int i = 0; i < rriData.length; i++) {
      diff = rriData[i] - meanValue;
      sum += diff * diff;
    }
    sd = Math.sqrt(sum / rriData.length);

    return sd;
  }

  public static double CalcRMSSD(double[] rriData) {
    if (rriData.length < 2) return Double.NaN;

    double sum = 0.0;
    double sd = 0.0;
    double diff;

    for (int i = 0; i < rriData.length - 1; i++) {
      diff = rriData[i] - rriData[i + 1];
      sum += diff * diff;
    }
    sd = Math.sqrt(sum / rriData.length);

    return sd;
  }

  private static ECG_STRESS CalcFrequency(double[] inputReal) {
    int iLog2 = (int) (Math.log10(inputReal.length) / Math.log10(2));
    int sampleSize = (int) Math.pow(2, iLog2); //최대 2의 제곱수를 구한다. FFT 계산을 위해서임.

    int hz = 100;
    //주파수 해상도 즉 주파수 변화량은 주파수÷샘플 수÷윈도우사이즈(hz의 40%) Hz로 주파수 도메인에서 한칸의 단위입니다.

    double sampleRate = (double) hz / sampleSize / (hz * 0.4);
    double frequency = 0;

    double[] input = Arrays.copyOfRange(inputReal, 0, sampleSize);
    List<FREQ_DATA> listFreq = new ArrayList<FREQ_DATA>();

    //double[] tempConversion = new double[input.length/2];

    //100 is not a power of 2, consider padding for fix | 2의 거듭제곱의 개수로 해야함
    FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);

    try {

      Complex[] complx = fft.transform(input, TransformType.FORWARD);
      // 대칭되는 값을 제외하기위해 절반만을 사용, 해석하고자 하는 신호의 최고 주파수임.
      for (int i = 0; i < (complx.length / 2); i++) {
        //복소수의 절대값의 제곱의 sqrt
        double dIMABS2 = Math.sqrt(Math.pow(complx[i].getReal(), 2) + Math.pow(complx[i].getImaginary(), 2));
        double dABSPower = dIMABS2 / (sampleSize / 2);

        FREQ_DATA node = new FREQ_DATA();
        // frequency계산
        node.Freq_Domain = i * sampleRate;
        node.Magnitude = dABSPower;

        listFreq.add(node);

        //System.out.println(node.Freq_Domain+"\t"+node.Magnitude);
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e);
    }


    //LF: 0.04~0.15Hz 주파수 범위의 maginute 합
    //HF: 0.15~0.4Hz 주파수 범위의 maginute 합

    int iCntHF = 0, iCntLF = 0;
    double dHF = 0, dLF = 0, dVLF = 0;

    //FFT 구동 후 나온 결과값에서 델타~감마까지의 구간을 누적시킨다.
    for (int j = 0; j < listFreq.size(); j++) {
      double dFreqDomain = listFreq.get(j).Freq_Domain;
      double dMagnitude = listFreq.get(j).Magnitude;

      if (dFreqDomain < 0.04) {
        dVLF += dMagnitude; //누적시킴
      }

      if (dFreqDomain >= 0.04 && dFreqDomain < 0.15) {
        dLF += dMagnitude; //누적시킴
      }

      if (dFreqDomain >= 0.15 && dFreqDomain < 0.4) {
        dHF += dMagnitude;
      }
    }

    ECG_STRESS node = new ECG_STRESS();
    node.TotalPower = dVLF + dLF + dHF;
    node.LF = dLF;
    node.HF = dHF;
    node.VLF = dVLF;

    double dLFScore = CalcLF(node.LF);
    double dHFScore = CalcHF(node.HF);
    double dStressScore = CalcStress(dLFScore, dHFScore);

    node.StressScore = dStressScore;

    return node;
  }

  public static double CalcLF(double dVal) {
    double dResult = 0.0;

    if (dVal <= 6.0) dResult = dVal / 6.0;
    else if (dVal > 6.0 && dVal <= 8.06) dResult = 1.0;
    else dResult = 1.0 - (0.5 * ((dVal - 8.06) / (12.0 - 8.06)));

    return dResult;
  }

  public static double CalcHF(double dVal) {
    double dResult = 0;

    if (dVal <= 4.0) dResult = dVal / 4.0;
    else if (dVal > 4.0 && dVal <= 7.23) dResult = 1.0;
    else dResult = 1.0 - (0.5 * ((dVal - 7.23) / (12.0 - 7.23)));

    return dResult;
  }

  public static double CalcStress(double dLF, double dHF) {
    return (100.0 * ((2.0 / 3.0 * dLF + 1.0 / 3.0 * dHF) * 1));
  }

}
