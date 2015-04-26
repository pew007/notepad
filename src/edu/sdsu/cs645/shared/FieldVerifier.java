package edu.sdsu.cs645.shared;

public class FieldVerifier {

  public static boolean isValidPassword(String password) {
    return password != null && password.equals("sp2015");
  }
}
