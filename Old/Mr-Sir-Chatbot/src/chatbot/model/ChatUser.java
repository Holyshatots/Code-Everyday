package chatbot.model;

public class ChatUser
{
	private String name;
	private int age;
	
	public ChatUser()
	{
		this.name = "";
		this.age = 0;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}
}
