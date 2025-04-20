package com.tic_tac_toe.tic_tac_toe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/board")
    public List<List<String>> getBoard() {
        // Convert the char[][] board to List<List<String>> for better JSON serialization
        char[][] board = gameService.getBoard();
        List<List<String>> boardList = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < board[i].length; j++) {
                row.add(String.valueOf(board[i][j]));
            }
            boardList.add(row);
        }

        return boardList;
    }

    @GetMapping("/current-player")
    public String getCurrentPlayer() {
        return gameService.getCurrentPlayer();
    }

    @GetMapping("/winner")
    public String getWinner() {
        return gameService.getWinner();
    }

    @PostMapping("/move")
    public boolean makeMove(@RequestParam int row, @RequestParam int col) {
        return gameService.makeMove(row, col);
    }

    @PostMapping("/reset")
    public void resetGame() {
        gameService.resetGame();
    }


}