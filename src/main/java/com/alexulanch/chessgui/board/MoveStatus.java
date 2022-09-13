package com.alexulanch.chessgui.board;

public enum MoveStatus {
    DONE {
        @Override
        public boolean isDone() {
            return true;
        }
    };

    public abstract boolean isDone();
}
