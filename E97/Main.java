public class Main
{
	public static void main(String[] args) {
	    int m = 5;
	    int n = 5;
	    Rooms rooms = new Rooms(m);
		for (int i = 0; i < n; ++i){
		     new Thread(new Threads(rooms,m)).start();
		}
	}
}