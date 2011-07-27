package squareup;
/**
 * Encapsulates a hash, lat, and lon. The equals and hashCode methods for this
 * class only use the value of the hash to determine equality. Thus, if this key
 * is used in a hash map lat and lon will not be examined. The values are there
 * for convenience as a caller may desire the lat, lon associated with a given
 * hash and vise-versa.  This class is immutable.
 * 
 * @author kenshalo
 * 
 */
public class GeoHash {

	private String hash = null;
	
	private double lat = 0d;

	private double lon = 0d;

	public GeoHash() {
		
	}

	public GeoHash(String hash, double lat, double lon) {
		this.hash = hash;
		this.lat = lat;
		this.lon = lon;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeoHash other = (GeoHash) obj;
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
			return false;
		return true;
	}

	public String getHash() {
		return hash;
	}
	public double getLat() {
		return lat;
	}
	
	public double getLon() {
		return lon;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "GeoHashKey [hash=" + hash + ", lat=" + lat + ", lon=" + lon
				+ "]";
	}
}