package event;

import event.MessageQueue.IListener;
import newChannel.Channel;
import newChannel.ImplChannel;

public class EventClose extends Event{
	
	IListener lis;
	ImplChannel chann;
	
	public EventClose(Executor e, IListener lis, ImplChannel chann) {
		super(e);
		this.lis = lis; 
		this.chann = chann;
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	void reac() {
		chann.disconnect();
		IListener listener = MapListener.getInstance().Suppr(((ImplChannel)chann).getMyChannel());
		MapListener.getInstance().Suppr(chann);
		
		lis.closed();
		if(listener != null) {
			listener.closed();
		}
	}


}
