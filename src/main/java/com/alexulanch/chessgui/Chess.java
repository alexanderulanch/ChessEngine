package com.alexulanch.chessgui;

import com.alexulanch.chessgui.board.Board;

import javax.swing.*;
import java.awt.*;
//import java.awt.*;

public class Chess {
    public static void main(String[] args) {
        Board board = Board.createStandardBoard();

        System.out.println(board);
    }
}
