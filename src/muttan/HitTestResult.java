package muttan;

/**
 * Created by mutta on 2017/05/04.
 */
public class HitTestResult {
    private boolean leftUpper;
    private boolean leftBottom;
    private boolean rightUpper;
    private boolean rightBottom;
    public HitTestResult(boolean leftUpper, boolean leftBottom, boolean rightUpper, boolean rightBottom){
        this.leftUpper = leftUpper;
        this.leftBottom = leftBottom;
        this.rightUpper = rightUpper;
        this.rightBottom = rightBottom;
    }
    public boolean upper(){
        return (leftUpper && !leftBottom) || (rightUpper && !rightBottom);
    }
    public boolean bottom(){
        return (leftBottom && !leftUpper) || (rightBottom && !rightUpper);
    }
    public boolean left(){
        return (leftUpper && !rightUpper)||(leftBottom && !rightBottom);
    }
    public boolean right(){
        return (rightUpper && !leftUpper)||(rightBottom && !leftBottom);

    }
}
