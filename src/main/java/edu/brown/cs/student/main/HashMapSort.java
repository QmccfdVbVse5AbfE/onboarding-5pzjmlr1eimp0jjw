package edu.brown.cs.student.main;


import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Iterator;

public class HashMapSort {

  HashMap<star, Double> _myMap;
  ArrayList<Map.Entry<star, Double>> _myArrList;
  HashMap<star, Double> _mySortedHashMap;

  public HashMapSort(HashMap<star, Double> myMap){

    _myMap = myMap;
    _myArrList = new ArrayList<>();
    _mySortedHashMap = new HashMap<>();

  }

  public Iterator<Map.Entry<star, Double>> hashSort(){
    for(Map.Entry<star, Double> en: _myMap.entrySet()){
      _myArrList.add(en);
    }
    Comparator<Map.Entry<star, Double>> doubleComp = new Comparator<Map.Entry<star, Double>>(){
      @Override
      public int compare(Map.Entry<star, Double> ele1, Map.Entry<star, Double> ele2){
        Double val1 = ele1.getValue();
        Double val2 = ele2.getValue();
        return val1.compareTo(val2);
      }
    };

    Collections.sort(_myArrList, doubleComp);
    Iterator hmIterator = _myArrList.iterator();
    return hmIterator;
  }
}