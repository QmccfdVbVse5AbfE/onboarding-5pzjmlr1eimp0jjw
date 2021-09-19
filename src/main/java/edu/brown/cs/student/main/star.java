package edu.brown.cs.student.main;

public class star{

  int _id;
  double _myX;
  double _myY;
  double _myZ;
  String _name;
  double _dis;

  public star(int id, String name, double X, double Y, double Z){
    _myX = X;
    _myY = Y;
    _myZ = Z;
    _id = id;
    _name = name;
  }
  public double getX(){
    return _myX;
  }
  public double getY(){
    return _myY;
  }
  public double getZ(){
    return _myZ;
  }
  public int getID(){
    return _id;
  }
  public String getName(){
    return _name;
  }
  public void setDis(Double dis){
    _dis = dis;
  }
  public double getDis(){
    return _dis;
  }
}