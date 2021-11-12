package dbaction;
import java.lang.Boolean;
import java.util.Base64;  



public class TokenCheck {
	public Boolean tokenAuthentication(String user,String token){
		Base64.Decoder simpleDecoder = Base64.getDecoder();
		String decodedString = new String(simpleDecoder.decode(token.getBytes()));
		Boolean result = decodedString.contains(user);
		return result;
	}
}