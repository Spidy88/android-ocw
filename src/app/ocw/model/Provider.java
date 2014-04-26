package app.ocw.model;

/**
 * Model for an OCW Provider
 * 
 * @author Nick Ferraro
 *
 */
public class Provider {
	private final int id;
	private final String name;
	private final String externalId;
	
	public Provider(int id, String name, String externalId) {
		this.id = id;
		this.name = (name == null ? "no-name" : name);
		this.externalId = externalId;
	}
	
	public int getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public String getExternalId() {
		return this.externalId;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
