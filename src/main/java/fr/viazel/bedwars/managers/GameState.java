package fr.viazel.bedwars.managers;

public enum GameState {
    LOBBY(false),
    GAME(false),
    END(false);

    private boolean state;
    private static GameState currentState;

    GameState(boolean state) {
        this.state = state;
    }

    public static void setState(GameState gameState) {
        currentState = gameState;
    }

    public static Boolean isState(GameState gameState){
        if (gameState == currentState){
            return true;
        }
        return false;
    }
}
