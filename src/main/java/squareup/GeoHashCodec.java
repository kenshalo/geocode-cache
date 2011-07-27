package squareup;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * A codec for encoding and decoding geographic hashes. Produces GeoHash object
 * that encapsulates the lat, lon, and hash
 * 
 * @author kenshalo
 * 
 */
public class GeoHashCodec {

	final private static int DEFAULT_BITS = 8;

	final private static char[] digits = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm',
			'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	final private static Map<Character, Integer> map = new HashMap<Character, Integer>();
	static {
		int i = 0;
		for (char c : digits) {
			map.put(c, i++);
		}
	}

	public static String base32(long i) {
		char[] buf = new char[65];
		int charPos = 64;
		boolean negative = (i < 0);
		if (!negative)
			i = -i;
		while (i <= -32) {
			buf[charPos--] = digits[(int) (-(i % 32))];
			i /= 32;
		}
		buf[charPos] = digits[(int) (-i)];

		if (negative)
			buf[--charPos] = '-';
		return new String(buf, charPos, (65 - charPos));
	}

	private int numbits = DEFAULT_BITS;

	public GeoHashCodec() {

	}

	public GeoHashCodec(int numbits) {
		this.numbits = numbits;
	}

	public GeoHash decode(String hash) {
		if (hash == null || "".equals(hash)) {
			throw new IllegalArgumentException(
					"Invalid hash value specified.  Must be non-null and non-empty string");
		}

		StringBuilder bldr = new StringBuilder();
		for (char c : hash.toCharArray()) {
		     int i = map.get(c) + 32;
             bldr.append( Integer.toString(i, 2).substring(1) );
		}

		int blen = bldr.length() / 2;
		BitSet latbits = new BitSet(blen);
		BitSet lonbits = new BitSet(blen);

		char[] carr = bldr.toString().toCharArray();
		for (int i = 0, latIdx = 0, lonIdx = 0; i < carr.length; i++) {
			boolean b = carr[i] == '1';
			if (i % 2 == 0) {
				lonbits.set(lonIdx++, b);
			} else {
				latbits.set(latIdx++, b);
			}
		}

		double lat = getDouble(latbits, -90, 90);
		double lon = getDouble(lonbits, -180, 180);
		return new GeoHash(hash, lat, lon);
	}

	private double getDouble(BitSet bits, double floor, double ceiling) {
		double d = 0;
		for (int i = 0; i < bits.length(); i++) {
			d = (floor + ceiling) / 2;
			if (bits.get(i)) {
				floor = d;
			} else {
				ceiling = d;
			}
		}

		return d;
	}

	public GeoHash encode(double lat, double lon) {
		BitSet latbits = getBits(lat, -90, 90);
		BitSet lonbits = getBits(lon, -180, 180);
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < numbits; i++) {
			buffer.append((lonbits.get(i)) ? '1' : '0');
			buffer.append((latbits.get(i)) ? '1' : '0');
		}
		String hash = base32(Long.parseLong(buffer.toString(), 2));
		return new GeoHash(hash, lat, lon);
	}

	private BitSet getBits(double lat, double floor, double ceiling) {
		BitSet buffer = new BitSet(numbits);
		for (int i = 0; i < numbits; i++) {
			double mid = (floor + ceiling) / 2;
			if (lat >= mid) {
				buffer.set(i);
				floor = mid;
			} else {
				ceiling = mid;
			}
		}
		return buffer;
	}
}
