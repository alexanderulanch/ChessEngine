package com.alexulanch.chessgui;

public enum Alliance {
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }
    },
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }
    };

    public abstract int getDirection();
}
