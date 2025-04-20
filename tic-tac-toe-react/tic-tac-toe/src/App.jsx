import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

const App = () => {
  const [board, setBoard] = useState([]);
  const [player, setPlayer] = useState('X');
  const [winner, setWinner] = useState(null);

  // Fetch initial game state on load
  useEffect(() => {
    axios.get('http://localhost:8080/api/game/board')
      .then(response => {
        console.log("Board from backend:", response.data);
        setBoard(response.data);
      });

    axios.get('http://localhost:8080/api/game/current-player')
      .then(response => setPlayer(response.data));

    axios.get('http://localhost:8080/api/game/winner')
      .then(response => setWinner(response.data));
  }, []);

  // Handle player move
  const handleClick = (row, col) => {
    if (board[row][col] !== ' ' || winner) return;

    axios.post(`http://localhost:8080/api/game/move?row=${row}&col=${col}`)
      .then(response => {
        if (response.data) {
          // Refresh board and player info
          axios.get('http://localhost:8080/api/game/board')
            .then(res => {
              console.log("Updated board after move:", res.data);
              setBoard(res.data);
            });

          axios.get('http://localhost:8080/api/game/current-player')
            .then(res => setPlayer(res.data));

          axios.get('http://localhost:8080/api/game/winner')
            .then(res => setWinner(res.data));
        }
      })
      .catch(err => {
        console.error("Error making move:", err);
      });
  };

  // Restart game
  const resetGame = () => {
    axios.post('http://localhost:8080/api/game/reset')
      .then(() => {
        setBoard([]);
        setPlayer('X');
        setWinner(null);

        // Refetch initial board
        axios.get('http://localhost:8080/api/game/board')
          .then(response => {
            console.log("Board after reset:", response.data);
            setBoard(response.data);
          });
      })
      .catch(err => {
        console.error("Error resetting game:", err);
      });
  };

  console.log("Board state in render:", board);

  return (
    <div className="container">
      <h1>Tic Tac Toe</h1>
      <h2>{winner ? `Player ${winner === "Draw" ? "X and O" : winner} ${winner === "Draw" ? "Tie!" : "Wins!"}` : `Current Player: ${player}`}</h2>

      <div className="board">
        {board.map((row, rowIndex) =>
          row.map((cell, colIndex) => (
            <div
              className={`box ${winner && cell === ' ' ? 'disabled' : ''} ${winner && cell === 'X' ? 'highlight-x' : ''} ${winner && cell === 'O' ? 'highlight-o' : ''}`}
              key={`${rowIndex}-${colIndex}`}
              onClick={() => handleClick(rowIndex, colIndex)}
            >
              {cell.trim() === "" ? "-" : cell}
            </div>
          ))
        )}
      </div>

      <button className="reset-button" onClick={resetGame}>Restart Game</button>
    </div>
  );
};

export default App;