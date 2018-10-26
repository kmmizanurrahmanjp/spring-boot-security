package xyz.mizan.springbootsecurity.boot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {
	
	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;

	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	public static String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		} 
		
		/**
		 * We can use any one of two also
		 * 
		 * User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 * String userName = user.getUsername();
		 * 
		 * or 
		 * 
		 * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 * String userName = auth.getName();
		 * 
		 */
		return userName;
	}
	
	/**
	 * This method returns true if users is already authenticated [logged-in], else false.
	 */
	public boolean isCurrentAuthenticationAnonymous() {
	    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    return authenticationTrustResolver.isAnonymous(authentication);
	}
    
    //Generate password for database
   /* public static void main(String args[]) {
    	BCryptPasswordEncoder obj = new BCryptPasswordEncoder();
    	String pass = obj.encode("123");
    	System.out.println(pass);
    	
    }*/
}
