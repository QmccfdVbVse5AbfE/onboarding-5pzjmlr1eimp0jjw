package edu.brown.cs.student.main;


import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Iterator;

/**This class compares the values of hashmap (stars distance from given location) and return an sorted iterator
 * The way it works, first you put all the elements in the hashmap into arraylist. Then, using comparator sort the list*/
public class HashMapSort {

  HashMap<star, Double> _myMap;
  ArrayList<Map.Entry<star, Double>> _myArrList;

  public HashMapSort(HashMap<star, Double> myMap){

    _myMap = myMap;  //unsorted data
    _myArrList = new ArrayList<>(); //iterator of this sorted array is returned

  }

  public Iterator<Map.Entry<star, Double>> hashSort(){
    for(Map.Entry<star, Double> en: _myMap.entrySet()){
      _myArrList.add(en); //transfer elements
    }
    Comparator<Map.Entry<star, Double>> doubleComp = new Comparator<Map.Entry<star, Double>>(){
      @Override
      public int compare(Map.Entry<star, Double> ele1, Map.Entry<star, Double> ele2){
        Double val1 = ele1.getValue();  //double comparator is defined here
        Double val2 = ele2.getValue();
        return val1.compareTo(val2);
      }
    };

    Collections.sort(_myArrList, doubleComp);   //sort
    Iterator hmIterator = _myArrList.iterator();
    return hmIterator;
  }
}