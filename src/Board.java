import java.util.ArrayList;
import java.util.List;

public class Board {

	private int  boardSize;
	
	private Ship[][] shipLocations;
	private boolean[][] shotsFired;
	boolean isHorizontal = true;
	private List<Ship> shipsOnBoard;
	
	public Board(int boardSize) {
		this.shipLocations = new Ship[this.boardSize][this.boardSize];
		this.shotsFired = new boolean[this.boardSize][this.boardSize]; 
        this.shipsOnBoard = new ArrayList<>();
        this.boardSize =boardSize;
	}
	
	  public boolean isValidCoordinate(int row, int col) {
	        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
	    }

	    public boolean hasShipAt(int row, int col) {
	        if (isValidCoordinate(row, col)) {
	            return shipLocations[row][col] != null;
	        }
	        return false;
	    }

	    public boolean isShotAt(int row, int col) {
	        if (isValidCoordinate(row, col)) {
	            return shotsFired[row][col];
	        }
	        return false; 
	    }

	    
	    public Ship getShipAt(int row, int col) {
	        if (isValidCoordinate(row, col)) {
	            return shipLocations[row][col];
	        }
	        return null;
	    }


	    public boolean placeShip(Ship ship, int row, int col, boolean isHorizontal) {
	        if (ship == null) {
	           // System.out.println("Σφάλμα: Το πλοίο είναι null."); *Μονο για debuging
	            return false;
	        }
	        int shipLength = ship.getSize();

	       
	        if (isHorizontal) {
	            if (col + shipLength > boardSize || !isValidCoordinate(row, col) || !isValidCoordinate(row, col + shipLength -1)) {
	               //System.err.println("Σφάλμα τοποθέτησης "+ ship.getName() +": Εκτός ορίων (οριζόντια). Θέση: ("+row+","+col+"), Μέγεθος: "+shipLength); *Μονο για debuging
	                return false;
	            }
	        } else { 
	            if (row + shipLength > boardSize || !isValidCoordinate(row, col) || !isValidCoordinate(row + shipLength -1, col)) {
	               //System.err.println("Σφάλμα τοποθέτησης "+ ship.getName() +": Εκτός ορίων (κάθετα). Θέση: ("+row+","+col+"), Μέγεθος: "+shipLength); *Μονο για debuging
	                return false;
	            }
	        }

	       
	        for (int i = 0; i < shipLength; i++) {
	        	int currentRow;
	        	int currentCol;

	        	if (isHorizontal) { 
	        	    currentRow = row;       
	        	    currentCol = col + i;   
	        	} else { 
	        	    currentRow = row + i;  
	        	    currentCol = col;       
	        	}
	        	
	            if (shipLocations[currentRow][currentCol] != null) { 
	               //System.err.println("Σφάλμα τοποθέτησης "+ ship.getName() +": Επικάλυψη σε " + currentRow + "," + currentCol); *Μονο για debuging
	                return false;
	            }
	        }

	       
	        for (int i = 0; i < shipLength; i++) {
	            if (isHorizontal) {
	                shipLocations[row][col + i] = ship;
	            } else {
	                shipLocations[row + i][col] = ship;
	            }
	        }
	        shipsOnBoard.add(ship);
	        return true;
	    }

	
	    public String receiveAttack(int row, int col) {
	        if (!isValidCoordinate(row, col)) {
	           // System.err.println("Άκυρες συντεταγμένες πυροβολισμού: " + row + "," + col); *Μονο για debuging
	            return "INVALID_COORDINATE";
	        }

	        Ship targetShip = shipLocations[row][col]; 

	        if (shotsFired[row][col]) { 
	            return (targetShip != null) ? "ALREADY_SHOT_HIT" : "ALREADY_SHOT_MISS";
	        }

	        shotsFired[row][col] = true; 

	        if (targetShip != null) { 
	            targetShip.hitRegister();
	            if (targetShip.isSunk()) {
	                //System.out.println("Το πλοίο " + targetShip.getName() + " βυθίστηκε!");  *Μονο για debuging
	                return "SUNK";
	            }
	            return "HIT";
	        } else { 
	            return "MISS";
	        }
	    }

	    public boolean areAllShipsSunk() {
	        if (shipsOnBoard.isEmpty()) return false; 
	        
	        for (int i = 0; i < shipsOnBoard.size(); i++) {
	        	Ship ship = shipsOnBoard.get(i); 
	            if (!ship.isSunk()) return false; 
	        }
	        
	        return true;
	    }
}
