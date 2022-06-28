package wellfernandes.com.github.bean;

import java.util.Date;

public class TemperatureBEAN {

	// temperature attributes
	private int temperatureId;
	private float temperatureValue;
	private Date temperatureDate;
	
	public TemperatureBEAN() {
		
	}

	public TemperatureBEAN(int temperatureId, float temperatureValue, Date temperatureDate) {
		super();
		this.temperatureId = temperatureId;
		this.temperatureValue = temperatureValue;
		this.temperatureDate = temperatureDate;
	}

	public int gettemperatureId() {
		return temperatureId;
	}

	public void settemperatureId(int temperatureId) {
		this.temperatureId = temperatureId;
	}

	public float gettemperatureValue() {
		return temperatureValue;
	}

	public void settemperatureValue(float temperatureValue) {
		this.temperatureValue = temperatureValue;
	}

	public Date gettemperatureDate() {
		return temperatureDate;
	}

	public void settemperatureDate(Date temperatureDate) {
		this.temperatureDate = temperatureDate;
	}
}
