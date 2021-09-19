package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathBotTest {

  @Test
  public void testAddition() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.add(10.5, 3);
    assertEquals(13.5, output, 0.01);
  }

  @Test
  public void testLargerNumbers() {
    MathBot matherator9001 = new MathBot();
    double output = matherator9001.add(100000, 200303);
    assertEquals(300303, output, 0.01);
  }

  @Test
  public void testSubtraction() {
    MathBot matherator9002 = new MathBot();
    double output = matherator9002.subtract(18, 17);
    assertEquals(-1, output, 0.01);
  }

  @Test
  public void testSmallAddition(){
    MathBot matherator9003 = new MathBot();
    double output = matherator9003.add(0.5, 0.2);
    assertEquals(0.7, output, 0.01);
  }

  @Test
  public void testMixLargeAddition(){
    MathBot matherator9004 = new MathBot();
    double output = matherator9004.add(0.5, 9000.0);
    assertEquals(9000.5, output, 0.01);
  }

  @Test
  public void testNegAddition(){
    MathBot matherator9005 = new MathBot();
    double output = matherator9005.add(-0.5, -0.2);
    assertEquals(-0.7, output, 0.01);
  }

  @Test
  public void testNegPosAddition(){
    MathBot matherator9006 = new MathBot();
    double output = matherator9006.add(-0.1, 0.2);
    assertEquals(0.1, output, 0.01);
  }

  @Test
  public void testMixLargeSubtraction(){
    MathBot matherator9007 = new MathBot();
    double output = matherator9007.subtract(0.5, 9000.0);
    assertEquals(8999.5, output, 0.01);
  }

  @Test
  public void testNegSubtraction(){
    MathBot matherator9005 = new MathBot();
    double output = matherator9005.subtract(-0.5, -0.2);
    assertEquals(0.3, output, 0.01);
  }

  @Test
  public void testNegPosSubtraction(){
    MathBot matherator9006 = new MathBot();
    double output = matherator9006.subtract(-0.1, 0.2);
    assertEquals(0.3, output, 0.01);
  }

}
