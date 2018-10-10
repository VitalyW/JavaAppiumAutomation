import org.junit.Test;

import static org.junit.Assert.*;

public class MainTestClass extends MainClass {

  @Test
  public void testGetLocalNumber() {
    assertEquals("getLocalNumber from the parent class returns " + this.getLocalNumber() + " instead of 14",
            14, this.getLocalNumber());
  }

  @Test
  public void testGetClassNumber() {
    assertFalse("getClassNumber from the parent class returns " + this.getClassNumber() + " that is above 45",
            this.getClassNumber() > 45);
  }

  @Test
  public void testGetClassString() {
    assertTrue("This sentence doesn't contain words: hello or Hello",
            ifSentenceContainsWords(this.getClassString()));
  }

  private boolean ifSentenceContainsWords(String sentence) {
    String[] splitSentence = sentence.trim().split(",");
    for (String st : splitSentence) {
      if (st.equals("Hello") || st.equals("hello")) {
        return true;
      }
    }
    return false;
  }

}
