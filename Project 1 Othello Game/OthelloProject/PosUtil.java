

public class PosUtil{
	int util;
	Position position;		
		
	public PosUtil(int util, Position position){
		this.util = util;
		this.position = position;
	}

    public int getUtil (){
        return this.util;
    }

    public Position getPosition (){
        return this.position;
    }
}