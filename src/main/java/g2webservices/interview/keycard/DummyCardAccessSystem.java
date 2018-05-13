package g2webservices.interview.keycard;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.codec.digest.DigestUtils;

import g2webservices.interview.models.keycard.KeyCardRequest;

public class DummyCardAccessSystem implements KeyCardAccessSystem {

	private final List<String> keys = Arrays.asList(DigestUtils.md5Hex("ABC"), DigestUtils.md5Hex("ACC"),
			DigestUtils.md5Hex("CCA")); // mocked allowed keys for dummy card access system

	@Override
	public String getAccessKey() {
		// in a real world case , we need to
		// interact with reader keycard system here 
		// and get the token of access card that is being used
		System.out.println("Please Enter ACCESS KEY");
		Scanner sc = new Scanner(System.in);
		final String input = sc.nextLine();
		sc.close();
		return DigestUtils.md5Hex(input); 
	}

	@Override
	public boolean validate() {
		final KeyCardRequest request = prepareRequest();
		return isValid(request);
	}

	@Override
	public KeyCardRequest prepareRequest() {
		final String key = getAccessKey();
		return KeyCardRequest.of(System.currentTimeMillis(), key);
	}

	@Override
	public boolean isValid(KeyCardRequest request) {
		// in a real world scenario this should encrypt keys and validate
		// against external db or API. 
		// Communication need to be done at this point
		final boolean isValid = keys.contains(request.getKey());
		System.out.println("Key Validation Result " + isValid );
		return isValid;
	}

}
