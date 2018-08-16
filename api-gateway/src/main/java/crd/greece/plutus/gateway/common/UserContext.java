package crd.greece.plutus.gateway.common;

import crd.greece.plutus.user.client.dto.UserDTO;

/**
 * @author chenjiayou022
 */
public class UserContext {
  private static final ThreadLocal<UserDTO> USER_HOLDER = new ThreadLocal<>();
  
  public static void setUser(UserDTO user){
    USER_HOLDER.set(user);
  }
  
  public static void remove() {
    USER_HOLDER.remove();
  }
  
  public static UserDTO getUser() {
    return USER_HOLDER.get();
  }

}
