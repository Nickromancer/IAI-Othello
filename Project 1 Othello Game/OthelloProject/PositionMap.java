import java.util.HashMap;

import javax.sound.sampled.BooleanControl;

public class PositionMap {

    private HashMap<Position, Double> valueMap;
    
    /*Creates a PositionMap with increased/decreased values of certain token placements 
    based on the size of the gameboard */
    public PositionMap (int size) {
        valueMap = new HashMap<>();
        for (int i=0; i<size;i++){
            for (int j=0; j<size; j++) {
                double value = 0.0;
                Position position = new Position(i, j);
                if (isCorner(position, size)){
                    value = 0.04;
                }
                else if (isEdge(position, size)){
                    value = 0.02;
                }
                else if (isNearCorner(position, size)){
                    value = -0.03;
                }
                else if (isNearEdge(position, size)) {
                    value = -0.01;
                }

                valueMap.put(position, value);
            }
        }
    }

    //Checks if a given position is a corner.
    public boolean isCorner(Position position, int size){
        if (position.col == size-1 && position.row == size-1 || position.col == size-1 && position.row == 0
        || position.col == 0 && position.row == size-1 || position.col == 0 && position.row == 0 ){
            return true;
        }
        return false;
    }
    
    //Checks if a given position is an edge.
    public boolean isEdge (Position position, int size) {
        if (position.col > 1 && position.col < size-2 && position.row ==0
        || position.col > 1 && position.col < size-2 && position.row ==size-1 
        || position.row > 1 && position.row < size-2 && position.col ==0 
        || position.row > 1 && position.row < size-2 && position.col ==size-1){
            return true;
        }
        return false;
    }

    //Checks if a given position is near a corner
    public boolean isNearCorner (Position position, int size) {
        if (position.col == 1 && position.row == 0 
        || position.col == size-2 && position.row == 0
        || position.col == 1 && position.row == size-2
        || position.col == size-2 && position.row == size-2
        || position.col == 0 && position.row == 1
        || position.col == 0 && position.row == size-2
        || position.col == 1 && position.row == 1
        || position.col == 1 && position.row == size-1
        || position.col == size-2 && position.row == 1
        || position.col == size-2 && position.row == size-1
        || position.col == size-1 && position.row == 1
        || position.col == size-1 && position.row == size-1) {
            return true;
        }
        return false;
    }

    //Checks if a given position is near an edge
    public boolean isNearEdge (Position position, int size) {
        if (position.col > 1 && position.col < size-2 && position.row == 1
        || position.col > 1 && position.col < size-2 && position.row ==size-2
        || position.row > 1 && position.row < size-2 && position.col == 1
        || position.row > 1 && position.row < size-2 && position.col ==size-2){
            return true;
        }
        return false;
    }

    public HashMap<Position, Double> getValueMap() {
        return valueMap;
    }

    
}
