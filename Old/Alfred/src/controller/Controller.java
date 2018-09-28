package controller;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class Controller
{
	public void start()
	{
		String webappDirLocation = "src/webapp/";
		Tomcat tomcat = new Tomcat();
		
		String webPort = System.getenv("PORT");
		if(webPort == null || webPort.isEmpty()) 
		{
			webPort = "8080";
		}
		
		tomcat.setPort(Integer.valueOf(webPort));
		
		try
		{
			tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
		}
		catch (ServletException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());
        
        try
		{
			tomcat.start();
		}
		catch (LifecycleException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        tomcat.getServer().await();
		
	}
}
