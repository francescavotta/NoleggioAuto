package polito.it.noleggio.model;

import java.time.LocalTime;

public class Event implements Comparable<Event>{
	
	public enum EventType{//serve ad avere qualcosa di più leggibile
		NUOVO_CLIENTE,//0
		RITORNO_AUTO  //1
		//sono delle costanti che crea java da sola
	}
	private LocalTime time;//istante di tempo in cui accade
	private EventType type; //assume solo i valori presenti in enum
	
	@Override
	public int compareTo(Event other) {
		//ordino per tempo crescente
		return this.time.compareTo(other.time);
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Event(LocalTime time, EventType type) {
		super();
		this.time = time;
		this.type = type;
	}

	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + "]";
	}

}
