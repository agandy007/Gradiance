
public class User {

	public User(String identifier) {
		// TODO Auto-generated constructor stub
		
			 try {
				 
				Class.forName(identifier).newInstance();
				
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}

}
