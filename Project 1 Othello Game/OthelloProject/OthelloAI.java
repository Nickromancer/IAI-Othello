
public class OthelloAI implements IOthelloAI {

    //int depthLimit = 5;
    
    
    public Position decideMove(GameState s) {
        int counter = 0;
        PosUtil bestMove = maxValue(s,counter, Integer.MIN_VALUE, Integer.MAX_VALUE);
        System.out.println(bestMove.getPosition().toString());
        return bestMove.getPosition();
    }

    public PosUtil maxValue (GameState s,int counter, int alpha, int beta) {
        counter++;
        System.out.println("maxValue: " + counter);
        if (s.isFinished()) {
           return new PosUtil(decideWinner(s), null);
        }
        int value = Integer.MIN_VALUE;
        Position move = null;
        if(s.legalMoves().isEmpty()){
            System.out.println("no more moves");
            s.changePlayer();
            PosUtil plz = minValue( s, counter, alpha, beta);
            return plz;
        }
        for (Position a : s.legalMoves()) {
            System.out.println(a);
            GameState tmpGS = new GameState(s.getBoard(), s.getPlayerInTurn());
            boolean stuff = tmpGS.insertToken(a);
            if (!stuff) {
                System.out.println("shits wrong");
            }
            PosUtil tmp = minValue(tmpGS, counter, alpha, beta);
            
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

    public PosUtil minValue (GameState s, int counter, int alpha, int beta) {
        counter++;
        System.out.println("minValue: " + counter);
        if (s.isFinished()) {
            return new PosUtil(decideWinner(s), null);
        }
        int value = Integer.MAX_VALUE; 
        Position move = null;
        
        if(s.legalMoves().isEmpty()){
            System.out.println("no more moves");
            PosUtil plz = maxValue( s, counter, alpha, beta);
            return plz;
        }
        for (Position a : s.legalMoves()) {
            System.out.println(a);
            GameState tmpGS = new GameState(s.getBoard(), s.getPlayerInTurn());
            boolean stuff = tmpGS.insertToken(a);
            PosUtil tmp = maxValue(tmpGS, counter, alpha, beta);
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



