package squareup;

import static org.junit.Assert.*;

import org.junit.Test;

public class GeoHashCodecTest {

	@Test
	public void testDecode() {
		GeoHashCodec codec = new GeoHashCodec();
		GeoHash r = null;

		try {
			r = codec.decode(null);
			r = codec.decode("");
		} catch (Exception e) {

		}

		r = codec.decode("ezs42");
		System.out.println(r);
	}

	@Test
	public void testEncode() {
		GeoHashCodec codec = new GeoHashCodec();
		
		GeoHash r = codec.encode(57.64911, 10.40744);
		System.out.println(r);
	}

}
