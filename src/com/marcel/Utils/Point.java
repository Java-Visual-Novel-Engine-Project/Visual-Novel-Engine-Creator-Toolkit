package com.marcel.Utils;

public class Point {

  public int x, y;

  public Point(int x, int y) {

    this.x = x;
    this.y = y;

  }

  @Override
  public String toString()
  {
    return "(" + x + ", " + y + ")";
  }

}
