package appcloud.common.model;

/**
 * @ClassName: NetSegment
 * @Description: 一个集群内分成多种网段
 * @author wenchaoz361
 * @date 2013-4-11 下午12:40:00
 */
public enum NetSegment {
    PRIVATE, PUBLIC;
    
    public static Integer toInteger(NetSegment netSegment) {
        switch(netSegment) {
            case PRIVATE:
                return 0;
            case PUBLIC:
                return 1;
            default:
                return -1;
        }
    }
    
    @Override
    public String toString() {
    	switch(this) {
	    	case PRIVATE:
	    		return "private";
	    	case PUBLIC:
	    		return "public";
	    	default:
	    		return null;
    	}
    }
}
