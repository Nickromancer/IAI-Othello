
import java.util.HashMap;
public class OthelloAI implements IOthelloAI {

    int depthLimit = 5;
    PositionMap valueMap;
    
    
    public Position decideMove(GameState s) {
        var currentplayer = s.getPlayerInTurn();    
        if (valueMap == null){
            valueMap = new PositionMap(s.getBoard().length);
        }
        int counter = 0;
        PosUtil bestMove = maxValue(s,counter, Double.MIN_VALUE, Double.MAX_VALUE, currentplayer);
        System.out.println("Final move value: " + bestMove.getUtil());
        return bestMove.getPosition();
    }

    public PosUtil maxValue (GameState s,int counter, double alpha, double beta, int player) {
        counter++;
        if (counter > depthLimit || s.isFinished()) {
           return new PosUtil(Eval(s, player), null);
        }
        double value = Integer.MIN_VALUE;
        Position move = null;
        if(s.legalMoves().isEmpty()){
            s.changePlayer();
            PosUtil plz = minValue( s, counter, alpha, beta, player);
            return plz;
        }
        for (Position a : s.legalMoves()) {
            System.out.println(a);
            GameState tmpGS = new GameState(s.getBoard(), s.getPlayerInTurn());
            boolean stuff = tmpGS.insertToken(a);
            PosUtil tmp = minValue(tmpGS, counter, alpha, beta, player);
            
            if (tmp.getUtil() > value) {
                value = tmp.getUtil();
                move = a;
                
            }
            if (value > alpha){
                alpha = value;
            }                  
            if (value >= beta){
                return new PosUtil(value, move);
            }
          }
    
        return new PosUtil(value,move);
    }

    public PosUtil minValue (GameState s, int counter, double alpha, double beta, int player) {
        counter++;
        if (counter > depthLimit || s.isFinished()) {
            return new PosUtil(Eval(s, player), null);
        }
        double value = Integer.MAX_VALUE; 
        Position move = null;
        if(s.legalMoves().isEmpty()){
            s.changePlayer();
            PosUtil plz = minValue( s, counter, alpha, beta, player);
            return plz;
        }
        for (Position a : s.legalMoves()) {
            System.out.println(a);
            GameState tmpGS = new GameState(s.getBoard(), s.getPlayerInTurn());
            boolean stuff = tmpGS.insertToken(a);
            PosUtil tmp = maxValue(tmpGS, counter, alpha, beta, player);
            if (tmp.getUtil() < value) {
                value = tmp.getUtil();
                move = a;
            }
            if (value < beta) {
                beta = value;
            }
            if (value <= alpha){
                return new PosUtil(value, move);
            }
            }
        return new PosUtil (value, move);
    }

    // Our Eval function
    public double Eval (GameState s, int player) {

        int[][] currentBoard = s.getBoard();

        double value = 0.0;

        if (player == 2) {
        for (Position p : valueMap.getValueMap().keySet()) {
            if (currentBoard[p.col][p.row]==2){
                    value+= valueMap.getValueMap().get(p);
            }
            else if (currentBoard[p.col][p.row]==1) {
                value -= valueMap.getValueMap().get(p);
            }
        }

        var tokens = s.countTokens();
        value += tokens[1]*0.03;
        value -= tokens[0]*0.03;

        System.out.println("VALUE: " + value);
        } 
        else {
            for (Position p : valueMap.getValueMap().keySet()) {
                if (currentBoard[p.col][p.row]==2){
                        value-= valueMap.getValueMap().get(p);
                }
                else if (currentBoard[p.col][p.row]==1) {
                    value += valueMap.getValueMap().get(p);
                }
            }
    
            var tokens = s.countTokens();
            value -= tokens[1]*0.03;
            value += tokens[0]*0.03; 
            System.out.println("VALUE: " + value);
        }
        
        return value;
        /*var tokens = s.countTokens();
        int amountOfTokens = tokens[0] + tokens[1];  
        double minBonus = 1;
        double maxBonus = 1;
        
        int[][] checker = s.getBoard();

        //HashMap<Position, Integer> positionValue = new HashMap<Position, Integer>();

        //positionValue.put(new Position(0,0), 4);
        double tempBonus = 0.0;

        for (Position p : valueMap.getValueMap().keySet()) {
            if (checker[p.col][p.row]==2){
                    tempBonus+= valueMap.getValueMap().get(p);
                }
            }
        maxBonus = maxBonus + tempBonus;

        System.out.println("Bonus: " + maxBonus);
    
        if (s.isFinished()){
            if (tokens[0] > tokens[1]) {
                return -1;
            }
            else if (tokens[1] > tokens[0]) {
                return 1;
            }   
            return 0;
            }
            else if (tokens[0] > tokens[1]) {
                return -((tokens[0]/amountOfTokens) * minBonus);
            }
            else {
                return (tokens[1]/amountOfTokens) * maxBonus;
        } */
    }
}



