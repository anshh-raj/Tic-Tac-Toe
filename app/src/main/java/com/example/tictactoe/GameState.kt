package com.example.tictactoe

data class GameState(
    val playerCircleCount: Int = 0,
    val playerCrossCount: Int = 0,
    val drawCount: Int = 0,
    val hintText: String = "Player 'X' turn",
    val currentTurn: BoardCellValue = BoardCellValue.CROSS,
    val victoryType: VictoryType = VictoryType.NONE,
    val hasWon: Boolean = false,
)

sealed class BoardCellValue{
    data object CIRCLE:BoardCellValue()
    data object CROSS:BoardCellValue()
    data object NONE:BoardCellValue()
}

sealed class VictoryType{
    data object HORIZONTAL1:VictoryType()
    data object HORIZONTAL2:VictoryType()
    data object HORIZONTAL3:VictoryType()
    data object VERTICAL1:VictoryType()
    data object VERTICAL2:VictoryType()
    data object VERTICAL3:VictoryType()
    data object DIAGONAL1:VictoryType()
    data object DIAGONAL2:VictoryType()
    data object NONE:VictoryType()
}