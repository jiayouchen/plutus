package crd.greece.plutus.gateway.common;

/**
 * @author chenjiayou022
 */
public class TokenContext {
  private static final ThreadLocal<String> USER_HOLDER = new ThreadLocal<>();
  
  public static void setToken(String token){
    USER_HOLDER.set(token);
  }
  
  public static void remove() {
    USER_HOLDER.remove();
  }
  
  public static String getToken() {
    return USER_HOLDER.get();
  }

}
