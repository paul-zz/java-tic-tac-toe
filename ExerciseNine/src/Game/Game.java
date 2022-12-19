package Game;

import java.util.Scanner;

public class Game {
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		String response;
		Chessboard chessboard = new Chessboard();
		while(true)
		{
			System.out.println("Shall we play a game?");
			response = scanner.nextLine();
			if (response.equals("yes"))
			{
				chessboard.clear();
				System.out.println("Choose difficulty:");
				response = scanner.nextLine();
				if (response.equals("easy"))
				{
					chessboard.setHardMode(false);
				}
				else if (response.equals("hard"))
				{
					chessboard.setHardMode(true);
				}
				while(chessboard.getIsGameOver()!=true)
				{
					chessboard.printChessboard();
					System.out.println("Player, make your move:");
					response = scanner.nextLine();
					while(!chessboard.playerMove(response))
					{
						System.out.println("Player, make your move again:");
						response = scanner.nextLine();
					};
					chessboard.computerMove();
					chessboard.checkGameOver();
				}
				chessboard.printChessboard();
				chessboard.outputWinner();
			}
			else if(response.equals("no"))
			{
				break;
			}
		}
		scanner.close();
	}
	
}
