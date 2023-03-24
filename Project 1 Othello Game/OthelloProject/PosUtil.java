

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