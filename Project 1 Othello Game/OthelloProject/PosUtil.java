

/*Custom class made to be able to return a pair of a Position and a util value, used in our AI*/
public class PosUtil{
	double util;
	Position position;		
		
	public PosUtil(double util, Position position){
		this.util = util;
		this.position = position;
	}

    public double getUtil (){
        return this.util;
    }

    public Position getPosition (){
        return this.position;
    }
}