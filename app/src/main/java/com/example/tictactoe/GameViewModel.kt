package com.example.tictactoe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    var state by mutableStateOf(GameState())

    val boardItems: MutableMap<Int, BoardCellValue> = mutableMapOf(
        1 to BoardCellValue.NONE,
        2 to BoardCellValue.NONE,
        3 to BoardCellValue.NONE,
        4 to BoardCellValue.NONE,
        5 to BoardCellValue.NONE,
        6 to BoardCellValue.NONE,
        7 to BoardCellValue.NONE,
        8 to BoardCellValue.NONE,
        9 to BoardCellValue.NONE,
    )

    fun onAction(actions: UserActions){
        when(actions){
            is UserActions.BoardTapped -> addValueToBoard(actions.cellNo)
            is UserActions.PlayAgainButtonClicked -> gameReset()
        }
    }

    private fun gameReset() {
        boardItems.forEach { (cellNo, _)  ->
            boardItems[cellNo] = BoardCellValue.NONE
        }
        state = state.copy(
            hintText = "Player 'X' turn",
            currentTurn = BoardCellValue.CROSS,
            victoryType = VictoryType.NONE,
            hasWon = false
        )
    }

    private fun addValueToBoard(cellNo: Int) {
        if(boardItems[cellNo] != BoardCellValue.NONE) return
        if(state.currentTurn == BoardCellValue.CIRCLE){
            boardItems[cellNo] = BoardCellValue.CIRCLE
            if(checkForVictory(BoardCellValue.CIRCLE)){
                state = state.copy(
                    hintText = "Player 'O' Won",
                    playerCircleCount = state.playerCircleCount + 1,
                    currentTurn = BoardCellValue.NONE,
                    hasWon = true
                )
            }
            else if(hasBoardFull()){
                state = state.copy(
                    hintText = "Game Draw",
                    drawCount = state.drawCount + 1
                )
            }
            else{
                state = state.copy(
                    hintText = "Player 'X' turn",
                    currentTurn = BoardCellValue.CROSS
                )
            }
        }
        else if(state.currentTurn == BoardCellValue.CROSS){
            boardItems[cellNo] = BoardCellValue.CROSS
            if(checkForVictory(BoardCellValue.CROSS)){
                state = state.copy(
                    hintText = "Player 'X' Won",
                    playerCrossCount = state.playerCrossCount + 1,
                    currentTurn = BoardCellValue.NONE,
                    hasWon = true
                )
            }
            else if (hasBoardFull()){
                state = state.copy(
                    hintText = "Game Draw",
                    drawCount = state.drawCount + 1
                )
            }
            else{
                state = state.copy(
                    hintText = "Player 'O' turn",
                    currentTurn = BoardCellValue.CIRCLE
                )
            }
        }
    }

    private fun checkForVictory(cellValue: BoardCellValue): Boolean {
        when{
            boardItems[1] == cellValue && boardItems[2] == cellValue && boardItems[3] == cellValue -> {
                state=state.copy(
                    victoryType = VictoryType.HORIZONTAL1
                )
                return true
            }
            boardItems[4] == cellValue && boardItems[5] == cellValue && boardItems[6] == cellValue -> {
                state=state.copy(
                    victoryType = VictoryType.HORIZONTAL2
                )
                return true
            }
            boardItems[7] == cellValue && boardItems[8] == cellValue && boardItems[9] == cellValue -> {
                state=state.copy(
                    victoryType = VictoryType.HORIZONTAL3
                )
                return true
            }
            boardItems[1] == cellValue && boardItems[4] == cellValue && boardItems[7] == cellValue -> {
                state=state.copy(
                    victoryType = VictoryType.VERTICAL1
                )
                return true
            }
            boardItems[2] == cellValue && boardItems[5] == cellValue && boardItems[8] == cellValue -> {
                state=state.copy(
                    victoryType = VictoryType.VERTICAL2
                )
                return true
            }
            boardItems[3] == cellValue && boardItems[6] == cellValue && boardItems[9] == cellValue -> {
                state=state.copy(
                    victoryType = VictoryType.VERTICAL3
                )
                return true
            }
            boardItems[1] == cellValue && boardItems[5] == cellValue && boardItems[9] == cellValue -> {
                state=state.copy(
                    victoryType = VictoryType.DIAGONAL1
                )
                return true
            }
            boardItems[3] == cellValue && boardItems[5] == cellValue && boardItems[7] == cellValue -> {
                state=state.copy(
                    victoryType = VictoryType.DIAGONAL2
                )
                return true
            }
            else -> return false
        }
    }

    private fun hasBoardFull(): Boolean {
        return !boardItems.containsValue(BoardCellValue.NONE)
    }
}