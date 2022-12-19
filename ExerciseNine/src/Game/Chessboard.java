package Game;

import java.util.ArrayList;
import java.util.Random;

public class Chessboard {
	// The chessboard class
	// The array to store player or computer's move.
	// 0: Unused, 1:X, 2:O
	private short[][] board = new short[3][3];
	private ArrayList<String> availablePlaces = new ArrayList<String>();
	private Random random = new Random();
	private boolean isHardMode = false;
	private boolean isGameOver = false;
	
	public Chessboard()
	{
		clear();
	}
	
	public void setHardMode(boolean isHardMode)
	{
		this.isHardMode = isHardMode;
	}
	
	public void clear()
	{
		isGameOver = false;
		availablePlaces.clear();
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
				board[i][j] = 0;
				availablePlaces.add(locationToPlace(i, j));
			}
		}
	}
	
	public boolean placeChess(String place, char chess)
	{
		boolean success = true;
		int place_idx = availablePlaces.indexOf(place);
		if(place_idx==-1)
		{
			success = false;
		}
		else
		{
			short[] location = placeToLocation(place);
			short chess_int=0;
			chess_int = chessToInt(chess);
			board[location[0]][location[1]] = chess_int;
			availablePlaces.remove(place_idx);
		}
		return success;
	}
	
	public boolean playerMove(String place)
	{
		boolean success;
		success = placeChess(place, 'X');
		return success;
	}
	
	
	
	public void computerMove()
	{
		if(isHardMode==false)
		{
			// Easy mode AI
			if(availablePlaces.size()!=0)
			{
				int place_idx = random.nextInt(0, availablePlaces.size());
				String place = availablePlaces.get(place_idx);
				placeChess(place, 'O');
			}
		}
		else
		{
			// Hard mode AI
			String twoPlacePlayer = checkForTwo('X');
			String twoPlaceComputer = checkForTwo('O');
			if(twoPlaceComputer != null)
			{
				// Then try to win itself
				placeChess(twoPlaceComputer, 'O');
			}
			else if(twoPlacePlayer != null)
			{
				// First try to defend the player
				placeChess(twoPlacePlayer, 'O');
			}
			else
			{
				// Then randomly place
				if(availablePlaces.size()!=0)
				{
					int place_idx = random.nextInt(0, availablePlaces.size());
					String place = availablePlaces.get(place_idx);
					placeChess(place, 'O');
				}
			}
		}
	}
	
	public int checkWinner()
	{
		// Returns an integer indicator shows who is the winner
		// 0: Nobody, 1: Player, 2: Computer
		// Check rows and cols
		for(int i=0;i<3;i++)
		{
			if(board[i][0]!=0 && board[i][0]==board[i][1] && board[i][1]==board[i][2])
			{
				return board[i][0];
			}
			if(board[0][i]!=0 && board[0][i]==board[1][i] && board[1][i]==board[2][i])
			{
				return board[0][i];
			}
		}
		// Check two diagonals
		if(board[0][0]!=0 && board[0][0]==board[1][1] && board[1][1]==board[2][2])
		{
			return board[0][0];
		}
		if(board[0][2]!=0 && board[0][2]==board[1][1] && board[1][1]==board[2][0])
		{
			return board[0][2];
		}
		return 0;
	}
	
	public String checkForTwo(char chess)
	{
		short chess_int = chessToInt(chess);
		// Check for rows
		for(int i=0;i<3;i++)
		{
			if(board[i][0]==chess_int && board[i][0]==board[i][1] && board[i][2]==0)
			{
				return locationToPlace(i, 2);
			}
			if(board[i][0]==chess_int && board[i][0]==board[i][2] && board[i][1]==0)
			{
				return locationToPlace(i, 1);
			}
			if(board[i][1]==chess_int && board[i][1]==board[i][2] && board[i][0]==0)
			{
				return locationToPlace(i, 0);
			}
		}
		// Check for cols
		for(int i=0;i<3;i++)
		{
			if(board[0][i]==chess_int && board[0][i]==board[1][i] && board[2][i]==0)
			{
				return locationToPlace(2, i);
			}
			if(board[0][i]==chess_int && board[0][i]==board[2][i] && board[1][i]==0)
			{
				return locationToPlace(1, i);
			}
			if(board[1][i]==chess_int && board[1][i]==board[2][i] && board[0][i]==0)
			{
				return locationToPlace(0, i);
			}
		}
		// Check for two diagonals
		if(board[0][0]==chess_int && board[0][0]==board[1][1] && board[2][2]==0)
		{
			return locationToPlace(2, 2);
		}
		if(board[0][0]==chess_int && board[0][0]==board[2][2] && board[1][1]==0)
		{
			return locationToPlace(1, 1);
		}
		if(board[1][1]==chess_int && board[2][2]==board[1][1] && board[0][0]==0)
		{
			return locationToPlace(0, 0);
		}
		if(board[2][0]==chess_int && board[2][0]==board[1][1] && board[0][2]==0)
		{
			return locationToPlace(0, 2);
		}
		if(board[0][2]==chess_int && board[0][2]==board[2][0] && board[1][1]==0)
		{
			return locationToPlace(1, 1);
		}
		if(board[1][1]==chess_int && board[0][2]==board[1][1] && board[2][0]==0)
		{
			return locationToPlace(2, 0);
		}
		return null;
	}
	
	public void outputWinner()
	{
		int winner = checkWinner();
		switch(winner)
		{
		case 0:
			System.out.println("A STRANGE GAME.");
			System.out.println("THE ONLY WINNING MOVE IS NOT TO PLAY.");
			break;
		case 1:
			System.out.println("Congratulations, you win!");
			break;
		case 2:
			System.out.println("Sorry, you lose!");
			break;
		}
	}
	
	public void checkGameOver()
	{
		// Check if the game is already over.
		if(checkWinner()!=0)
		{
			// If anyone wins, the game ends.
			isGameOver = true;
		}
		else
		{
			// If there are no available places, the game ends
			if(availablePlaces.size()==0)
			{
				isGameOver = true;
			}
		}
	}
	
	
	public boolean getIsGameOver()
	{
		return isGameOver;
	}
	
	public short[] placeToLocation(String place)
	{
		// Convert from place string to location [row_index, col_index] array
		// For example, 'A1' to [0, 0]
		short[] location = new short[2];
		char[] charArray = place.toCharArray();
		char col_idx_char = charArray[0];
		char row_idx_char = charArray[1];
		switch(row_idx_char)
		{
		case '1':
			location[0]=0;
			break;
		case '2':
			location[0]=1;
			break;
		case '3':
			location[0]=2;
			break;
		}
		switch(col_idx_char)
		{
		case 'A':
			location[1]=0;
			break;
		case 'B':
			location[1]=1;
			break;
		case 'C':
			location[1]=2;
			break;
		}
		return location;
	}
	
	public short chessToInt(char chess)
	{
		short chess_int = 0;
		switch(chess)
		{
		case 'X':
			chess_int=1;
			break;
		case 'O':
			chess_int=2;
			break;
		}
		return chess_int;
	}
	
	public String locationToPlace(int row_idx, int col_idx)
	{
		// Convert from location array to string
		char[] charArray = new char[2];
		switch(row_idx)
		{
		case 0:
			charArray[1]='1';
			break;
		case 1:
			charArray[1]='2';
			break;
		case 2:
			charArray[1]='3';
			break;
		}
		switch(col_idx)
		{
		case 0:
			charArray[0]='A';
			break;
		case 1:
			charArray[0]='B';
			break;
		case 2:
			charArray[0]='C';
			break;
		}
		return new String(charArray);
	}
	
	public void printChessboard()
	{
		System.out.println("  A B C");
		for(int i=0; i<3; i++)
		{
			System.out.print(i+1);
			System.out.print(" ");
			for(int j=0; j<3; j++)
			{
				switch(board[i][j])
				{
				case 0:
					if(i!=2) System.out.print("_");
					else System.out.print(" ");
					break;
				case 1:
					System.out.print("X");
					break;
				case 2:
					System.out.print("O");
					break;
				}
				if(j!=2) System.out.print("|");
				else System.out.println("");
			}
		}
	}
}
