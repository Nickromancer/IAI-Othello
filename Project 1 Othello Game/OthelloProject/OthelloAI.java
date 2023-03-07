
public class OthelloAI implements IOthelloAI {
    
    public Position decideMove(GameState s) {
        PosUtil bestMove = maxValue(s, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return bestMove.getPosition();
    }

    public PosUtil maxValue (GameState s, int alpha, int beta) {
        if (s.isFinished()) {
           return new PosUtil(decideWinner(s), null);
        }
        int value = Integer.MIN_VALUE;
        Position move = null;
        
        
        for (Position a : s.legalMoves()) {
            GameState tmpGS = s;
            boolean stuff = tmpGS.insertToken(a);
            PosUtil tmp = minValue(new GameState(tmpGS.getBoard(), tmpGS.getPlayerInTurn()), alpha, beta);
            
            if (tmp.getUtil() > value) {
                value = tmp.getUtil();
                move = a;
                if (value > alpha){
                    alpha = value;
                }
            }                    
            if (value >= beta){
                return new PosUtil(value, move);
            }
          }
    
        return new PosUtil(value,move);
    }

    public PosUtil minValue (GameState s, int alpha, int beta) {
        if (s.isFinished()) {
            return new PosUtil(decideWinner(s), null);
        }
        int value = Integer.MAX_VALUE; 
        Position move = null;
        
        for (Position a : s.legalMoves()) {
            GameState tmpGS = s;
            boolean stuff = tmpGS.insertToken(a);
            PosUtil tmp = maxValue(new GameState(tmpGS.getBoard(), tmpGS.getPlayerInTurn()), alpha, beta);
            if (tmp.getUtil() < value) {
                value = tmp.getUtil();
                move = a;
                if (value < beta) {
                    beta = value;
                }
            if (value <= alpha){
                return new PosUtil(value, move);
            }
            }
        }
        return new PosUtil (value, move);
    } 


    public int decideWinner (GameState s) {
        var tokens = s.countTokens();
        if (tokens[0] > tokens[1]) {
            return -1;
        }
        else if (tokens[1] > tokens[0]) {
            return 1;
        }   
        return 0;
    }
}



