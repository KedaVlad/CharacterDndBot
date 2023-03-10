package app.bot.model.act;
 
import java.util.ArrayList;
import java.util.List;

import app.bot.model.act.actions.BaseAction;
import lombok.Data;

@Data
public abstract class ActiveAct implements Act{
 
	private static final long serialVersionUID = 1L;
	private String name;
	private List<Integer> actCircle;
	
	public abstract boolean hasReply(String string);
	public abstract boolean hasCloud();
	public abstract boolean hasMediator();
	public abstract boolean hasAction();
	public abstract BaseAction getAction(); 
	
	public void toCircle(Integer act) 
	{
		this.actCircle.add(act);
	}
	
	public List<Integer> end()
	{
		List<Integer> end = new ArrayList<>();
		end.addAll(actCircle);
		actCircle.clear();
		return end;
	}
}
