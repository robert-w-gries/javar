package Translate;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:20 PM
 */
public abstract class Cx extends Exp {

    Tree.Exp unEx() {

        Temp.Temp r = new Temp.Temp();
        Temp.Label t = new Temp.Label();
        Temp.Label f = new Temp.Label();

        return new Tree.ESEQ(
                new Tree.SEQ(new Tree.MOVE(new Tree.TEMP(r),
                                            new Tree.CONST(1)),
                        new Tree.SEQ(unCx(t,f),
                                new Tree.SEQ(new Tree.LABEL(f),
                                        new Tree.SEQ(new Tree.MOVE(new Tree.TEMP(r),
                                                                    new Tree.CONST(0)),
                                                    new Tree.LABEL(t))))),
                new Tree.TEMP(r));

    }

    //TODO
    Tree.Stm unNx() {
        return null;
    }

    abstract Tree.Stm unCx(Temp.Label t, Temp.Label f);

}
