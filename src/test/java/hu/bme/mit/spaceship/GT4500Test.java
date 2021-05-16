package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import org.apache.commons.digester.SetPropertyRule;

public class GT4500Test {

  private TorpedoStore p;
  private TorpedoStore s;
  private GT4500 ship;
  
  @BeforeEach
  public void init(){
    p = mock(TorpedoStore.class);
    when(p.fire(1)).thenReturn(true);
    s =  mock(TorpedoStore.class);
    when(s.fire(1)).thenReturn(true);
    this.ship = new GT4500(p,s);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(p,times(1)).fire(1);
    verify(s,times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(p, times(1)).fire(1);
    verify(s, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Failure(){
    // Arrange
    when (p.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(p, times(1)).fire(1);
    assertEquals(false, result);
  }

 @Test
  public void fireTorpedo_All_Fail(){
    // Arrange
    when (p.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(p, times(1)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void primary_Single_Success(){
    // Arrange
    when(p.fire(1)).thenReturn(true);
    when(s.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(p,times(1)).fire(1);
    verify(s,times(0)).fire(1);
    assertEquals(true, result);
  }

 @Test
  public void secondary_Single_Success(){

    // Arrange
    when(p.fire(1)).thenReturn(false);
    when(s.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(p,times(1)).fire(1);
    verify(s,times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_BothFail(){
    when(p.isEmpty()).thenReturn(false);
    when(s.isEmpty()).thenReturn(false);
    when(p.fire(1)).thenReturn(false);
    when(s.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(p, times(1)).isEmpty();
    verify(s, times(1)).isEmpty();
    verify(p, times(1)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_firstEmptyTorpedoFail(){
    // Arrange

    when(p.fire(1)).thenReturn(true);
    when(s.isEmpty()).thenReturn(false);
    // Act
    boolean first = ship.fireTorpedo(FiringMode.SINGLE);
    boolean sec = ship.fireTorpedo(FiringMode.SINGLE);
    // Assert
    assertEquals(true, first);
    assertEquals(true, sec);
    verify(p, times(1)).fire(1);
    verify(p, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_secondEmptyFail() {
    // Arrange
    when(p.isEmpty()).thenReturn(true);
    when(p.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    
    // Assert
    verify(p, times(1)).isEmpty();
    verify(p, times(0)).fire(1);

    verify(p, times(1)).isEmpty();
    verify(p, times(0)).fire(1); 
    assertEquals(true, result);   
  }

  @Test
  public void fireTorpedo_firstEmptyFail() {
    // Arrange
    when(s.isEmpty()).thenReturn(true);
    when(p.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    
    // Assert
    
    verify(s, times(1)).isEmpty();
    verify(s, times(0)).fire(1);

    verify(p, times(1)).isEmpty();
    verify(p, times(0)).fire(1); 
    assertEquals(false, result);   
  }

  @Test
  public void fireTorpedo_() {
    // Arrange
    ship.wasPrimaryFiredLast= true;
    when(s.isEmpty()).thenReturn(true);
    when(p.isEmpty()).thenReturn(false);
    when(p.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    
    // Assert
    verify(s, times(1)).isEmpty();

    verify(p, times(1)).isEmpty();
    verify(p, times(1)).fire(1); 
    assertEquals(true, result);   
  }






}
