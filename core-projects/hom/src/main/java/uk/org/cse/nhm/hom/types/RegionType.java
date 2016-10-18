package uk.org.cse.nhm.hom.types;

/**
 * RegionType.
 *
 * @author richardt
 * @version $Id: RegionType.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public enum RegionType {
    WesternScotland("W. Scotland", 55.8, Country.Scotland),
    EasternScotland("E. Scotland", 56.4, Country.Scotland),
    NorthEast("North East",55.5, Country.England),
    YorkshireAndHumber("Yorkshire and The Humber",54.5, Country.England),
    NorthWest("NorthWest",54.1, Country.England),
    EastMidlands("East Midlands",53.4, Country.England),
    WestMidlands("",52.7, Country.England),
    SouthWest("",51.0, Country.England),
    EastOfEngland("",52.3, Country.England),
    SouthEast("",51.3, Country.England),
    London("",51.5, Country.England),
    NorthernScotland("",57.2, Country.Scotland),
	Wales("Wales", 52.5, Country.Wales),
	/* Latitude taken from SAP Table U4: Representative latitude and height above mean sea level */
    NorthernIreland("Northern Ireland", 54.6, Country.NorthernIreland);

    private final String friendlyName;
    private final double latitude, latrads;
	private final Country country;

    private RegionType(final String friendlyName, final double latitude, final Country country) {
        this.friendlyName = friendlyName;
        this.latitude = latitude;
		this.country = country;
        this.latrads = latitude * Math.PI / 180;
    }

    @Override
	public String toString() {
        return super.toString();
    }
    
    public double getLatitudeDegrees() {
		return latitude;
	}
    
    public double getLatitudeRadians() {
    	return latrads;
    }
    
    public Country getCountry() {
		return country;
	}

	public enum Country {
    	Scotland,
    	England,
    	Wales,
    	NorthernIreland
    }
}