package app.bot.model;

public interface MessageCore {

	public final static Integer CLOUD_K = 94233;
	public final static Integer TREE_K = 42942;
	public MessageModel[] getMessage();
	public void catchId(Integer id);
}
