package event;

abstract class Event {
	Executor e;
	
	Event(Executor e) {
		this.e =e;
	}
	
	abstract void reac();
}
