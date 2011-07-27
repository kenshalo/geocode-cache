package squareup;
import java.util.HashMap;
import java.util.Map;

public class GeoResponseCache {
	
	private static Map<GeoHash, GeoResponse> responses = new HashMap<GeoHash, GeoResponse>();
	private GeoHashCodec codec = null;
	private GeoCodingService geoCodingService = null;

	private GeoResponseCache() {
		codec = new GeoHashCodec();
	}

	public GeoResponse get(double lat, double lon) {
		GeoHash gh = codec.encode(lat, lon);
		GeoResponse response = responses.get(gh);

		// not found in cache use the service
		if (response == null) {
			response = geoCodingService.getGeoCode(lat, lon);
			// add the response
			responses.put(gh, response);
		}
		
		return response;
	}
	
	public GeoCodingService getGeoCodingService() {
		return geoCodingService;
	}
	
	public void setGeoCodingService(GeoCodingService geoCodingService) {
		this.geoCodingService = geoCodingService;
	}
}
