import java.util.Map;
import java.util.HashMap;
import java.math.*;

public class GeoResponseCache {

	private Map<Point, GeoResponse> cache = new HashMap<Point, GeoResponse>();
	private GeoCodingService gService = new GeoCodingServiceStub();
	
	public GeoResponseCache() {
		
	}
	
	public GeoResponse getGeoCode(float lat, float lon) {
		/**
		 * Does not handle negative lat, lon
		 */
		int p1 = (int) Math.ceil(lat);
		int p2 = (int) Math.ceil(lon);
		
		Point k = new Point(p1, p2);
		if (cache.containsKey(k)) {
			return cache.get(k);
		} else {
			GeoResponse resp = gService.getGeoCode(lat, lon);
			cache.put(k, resp);
			return resp;
		}
	}
	
}
