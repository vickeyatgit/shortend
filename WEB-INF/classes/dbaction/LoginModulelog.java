package dbaction;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.callback.Callback;
import dbaction.*;
import java.util.*;


public class LoginModulelog implements LoginModule {
    private CallbackHandler callbackHandler = null;
    private Boolean check = false;
	private Subject subject;
	UserPrincipal userPrincipal = null;
	RolePrincipal rolePrincipal = null;
	private String login;
	List<String> userGroups;
	Dbclass ddb = new Dbclass();
	

    @Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options) {
		
		System.out.println("hello i am in login module");
		this.callbackHandler = callbackHandler;
		this.subject = subject;
		

	}

	@Override
	public boolean login() throws LoginException {
		System.out.println("entering in login");
		Callback[] callbackArray = new Callback[2];
		callbackArray[0] = new NameCallback("User Name: ");
		callbackArray[1] = new PasswordCallback("Password: ",false);
		
		try {
			callbackHandler.handle(callbackArray);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedCallbackException e){
			e.printStackTrace();
		}
		String name = ((NameCallback) callbackArray[0]).getName();
		String password = new String(((PasswordCallback) callbackArray[1]).getPassword());
		Boolean userCheck = ddb.login(name, password);
		System.out.println("in LOGIN MODULE "+userCheck);

		
		//admin check
		if(userCheck){
			login = name;
			userGroups = new ArrayList<String>();
			userGroups.add("admin");
			check=userCheck;
		} else {
			//librarian check
			Boolean libCheck = ddb.loginLibrarian(name, password);
			if (libCheck) {
				login = name;
				userGroups = new ArrayList<String>();
				userGroups.add("librarian");
				check = libCheck;
			} else {
				check = false;
			}
		}
		return check;
	}

	@Override
	public boolean commit() throws LoginException {
		System.out.println("entering in commit");
		System.out.println("entering in commit"+login);
		userPrincipal = new UserPrincipal(login);
		subject.getPrincipals().add(userPrincipal);
		if (userGroups != null && userGroups.size() > 0) {
			for (String groupName : userGroups) {
				System.out.println("entering in commit"+groupName);
				rolePrincipal = new RolePrincipal(groupName);
				subject.getPrincipals().add(rolePrincipal);
			}
		}
		return check;
	}

	@Override
	public boolean abort() throws LoginException {
		// TODO Auto-generated method stub
		System.out.println("entering in abort");
		return false;
	}

	@Override
	public boolean logout() throws LoginException {
		System.out.println("entering in logout");
		subject.getPrincipals().remove(userPrincipal);
		subject.getPrincipals().remove(rolePrincipal);
		return true;
	}
}