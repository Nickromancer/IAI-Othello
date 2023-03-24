
import java.util.HashMap;
public class ActualIdiotsOthelloAI implements IOthelloAI {

    int depthLimit = 5;
    PositionMap valueMap;
    
    
    public Position decideMove(GameState s) {
        var currentplayer = s.getPlayerInTurn();    
        if (valueMap == null){
            valueMap = new PositionMap(s.getBoard().length);
        }
        int counter = 0;
        PosUtil bestMove = maxValue(s,counter, Double.MIN_VALUE, Double.MAX_VALUE, currentplayer);
        return bestMove.getPosition();
    }

    /*Calculates the maxValue for the given gamestate, recursively calls minValue until depthlimit is reached.
    Returns a PosUtil. our custom class which consists of a touple of a Position and the Utility.
    The method takes the current player as a parameter to pass along to the eval function 
    to correctly calculate the value of a move.*/
    public PosUtil maxValue (GameState s,int counter, double alpha, double beta, int player) {
        counter++;
        if (counter > depthLimit || s.isFinished()) {
           return new PosUtil(Eval(s, player), null);
        }
        double value = Integer.MIN_VALUE;
        Position move = null;
        /* Changes the current player so the AI doesn't get stuck, in case it has no legal moves available */
        if(s.legalMoves().isEmpty()){
            s.changePlayer();
            PosUtil plz = minValue( s, counter, alpha, beta, player);
            return plz;
        }
        /*Checks all the possible moves for the current gamestate 
        and finds out which move is the best to make */
        for (Position a : s.legalMoves()) {
            //The gamestate that would be created if a given move was made
            GameState tmpGS = new GameState(s.getBoard(), s.getPlayerInTurn());
             //Placeholder boolean, is simply there to makes sure tokens are placed on the board after the move is made
            boolean stuff = tmpGS.insertToken(a);
            PosUtil tmp = minValue(tmpGS, counter, alpha, beta, player);
            
            //Updates the current value if better one is found
            if (tmp.getUtil() > value) {
                value = tmp.getUtil();
                move = a;
                
            }
            //Updates alpha/beta values for pruning
            if (value > alpha){
                alpha = value;
            }                  
            if (value >= beta){
                return new PosUtil(value, move);
            }
          }
    
        return new PosUtil(value,move);
    }

    /*Calculates the minValue for the given gamestate, recursively calls maxValue until depthlimit is reached.
    Returns a PosUtil. our custom class which consists of a touple of a Position and the Utility.
    The method takes the current player as a parameter to pass along to the eval function 
    to correctly calculate the value of a move.*/
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

    /* Our Eval function 
     Our Eval function calculates the current value of a gamestate for the given player.
     The calculation is done by a mix of looking at where the tokens are placed, and how many tokens each player has.
     Every token placed in corners and on edges gives bonus value and every token placed near edges or corners gives minus value
     Furthermore the value score increases for every token placed, and decreases for every token placed by the opponent.
     For exact definition of which spots gives bonus/minus points look at the PositionMap.java file.
    */

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
        }
        
        return value;
      
    }
}



