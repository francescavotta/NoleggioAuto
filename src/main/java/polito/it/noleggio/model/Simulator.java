package polito.it.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.PriorityQueue;

import polito.it.noleggio.model.Event.EventType;

public class Simulator {

	//Eventi
	private PriorityQueue<Event> queue;
	
	//Parametri di simulazione, non cambiano
	private int NC; //numbers of cars
	private Duration T_IN; //intervallo di tempo fra i clienti
	private LocalTime oraApertura = LocalTime.of(8,0);
	private LocalTime oraChiusura = LocalTime.of(20, 0);
	
	//Stato del sistema
	private int nAuto; //auto attualmente presenti
	
	//Misure di uscita
	private int nClienti;
	private int nClientiInsoddisfatti;
	
	//Impostazione parametri iniziali
	
	public void setNumCars(int NC) {
		this.NC= NC;
	}

	public void setClientFrequency(Duration d) {
		this.T_IN = d;
	}
	
	public void run() {
		this.queue = new PriorityQueue<Event>();
		
		//Stato iniziale
		this.nAuto = NC;
		this.nClienti = 0;
		this.nClientiInsoddisfatti = 0;
		
		//Eventi iniziali
		LocalTime ora = this.oraApertura;
		while(ora.isBefore(this.oraChiusura)) {
			this.queue.add(new Event(ora, EventType.NUOVO_CLIENTE));
			ora = ora.plus(T_IN);
		}
		
		//Ciclo di simulazione
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			System.out.println(e.toString());
			processEvent(e);
		}
		
	}
	
	private void processEvent(Event e) {
		switch(e.getType()) {
		case NUOVO_CLIENTE:
			this.nClienti ++;
			if(this.nAuto>0) {
				//noleggia
				this.nAuto --;
				
				//quando ritorna l'auto?
				double num = Math.random() * 3; // [0,3)
				//int num = (int) (Math.random()*3);
				
				if(num < 1.0) {
					this.queue.add(new Event(e.getTime().plus(Duration.of(1, ChronoUnit.HOURS)), EventType.RITORNO_AUTO));
				}else if(num< 2) {
					this.queue.add(new Event(e.getTime().plus(Duration.of(2, ChronoUnit.HOURS)), EventType.RITORNO_AUTO));
				}else {
					this.queue.add(new Event(e.getTime().plus(Duration.of(3, ChronoUnit.HOURS)), EventType.RITORNO_AUTO));
				}
			}else {
				//insoddisfatto
				this.nClientiInsoddisfatti ++;
			}
			break;
		case  RITORNO_AUTO:
			this.nAuto ++;
			break;
		}
	}

	public int getTotClients() {
		return this.nClienti;
	}

	public int getDissatisfied() {
		return this.nClientiInsoddisfatti;
	}
}
