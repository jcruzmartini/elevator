package g2webservices.interview.keycard;

import java.util.Scanner;

import org.apache.commons.codec.digest.DigestUtils;

public class ManualUserKeyReader implements KeyCardReader {

	@Override
	public String read() {
		System.out.println("Please Enter ACCESS KEY");
		Scanner sc = new Scanner(System.in);
		final String input = sc.nextLine();
		sc.close();
		return DigestUtils.md5Hex(input); 
	}

}
