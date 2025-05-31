
public class Ship {

	private String name;
	private int size;
	private int hitsReceived;
	private boolean isSunk;
	
	public Ship (String name, int size){
		this.name = name;
		this.size = size;
		hitsReceived = 0;
		isSunk = false;
	}
	
	public String getName() {
		return name;
	}
		
	public int getSize() {
		return size;
	}
	
	public boolean isSunk() {
		return isSunk;
	}
	
	public void hitRegister() {
		if(!isSunk) {
			hitsReceived++;
			if(hitsReceived >= size)
				isSunk = true;
		}
	}
	
	public int getHitsReceived() {
		return hitsReceived;
	}
	
	public void reset() {
		hitsReceived = 0;
		isSunk = false;
	}
}

	
