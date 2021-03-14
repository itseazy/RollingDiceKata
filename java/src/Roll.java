package src;


public class Roll {
    //attribut
    private int diceValue;
    private int nbRoll;
    private int modifier = 0;
    private int type = 0;
    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    // constructeur
    public Roll(String formula) {
        System.out.println("--------------|formula -> " + formula + "\n");
        String str;
        if(formula.matches("^[d]\\d+|^[d][-+]\\d+|^[d]\\d+[-+]\\d+")) {//check begin by d
            System.out.println("commence par d");
            this.nbRoll = 1;
            this.type = 1;
            if(formula.indexOf("+") != -1 ) {
                System.out.println("il y a un +");
                str = formula.substring(formula.indexOf("+"));
                this.modifier = Integer.parseInt(str);
                str = formula.substring(formula.indexOf("d") + 1, formula.indexOf("+"));
                System.out.println( "str  = " + str);
                this.diceValue = Integer.parseInt(str);
            } else if ( formula.indexOf("-") != -1) {
                System.out.println("il y a un -");
                str = formula.substring(formula.indexOf("-"));
                this.modifier = Integer.parseInt(str);
                str = formula.substring(formula.indexOf("d") + 1, formula.indexOf("-"));
                System.out.println( "str  = " + str);
                this.diceValue = Integer.parseInt(str);
            }else {
                System.out.println( "only dice value");
                str = formula.substring(formula.indexOf("d") + 1);
                System.out.println( "str  = " + str);
                this.diceValue = Integer.parseInt(str);
            }
        }
        if(formula.matches("^\\d+[d]\\d+|^\\d+[d]\\d+[+-]\\d+|\\d+[d][-+]\\d+")) {   // check begin by figure and d
            System.out.println("commence par chiffre");
            str = formula.substring(0,formula.indexOf("d"));
            System.out.println( " pour nb roll str  = " + str);
            this.nbRoll = Integer.parseInt(str);
            this.type = 2;
            if(formula.matches("\\d+[d][-+]\\d+")) {
                System.out.println( "matching");
                if(formula.indexOf("+") != -1 ) {
                    str = formula.substring(formula.indexOf("+"));
                    this.modifier = Integer.parseInt(str);
                } else {
                    str = formula.substring(formula.indexOf("-"));
                    this.modifier = Integer.parseInt(str);
                }

            } else {
                if(formula.indexOf("+") != -1 ) {
                    System.out.println("il y a un +");
                    str = formula.substring(formula.indexOf("+"));
                    this.modifier = Integer.parseInt(str);
                    str = formula.substring(formula.indexOf("d") + 1, formula.indexOf("+"));
                    System.out.println( "str  = " + str);
                    this.diceValue = Integer.parseInt(str);
                } else if ( formula.indexOf("-") != -1) {
                    System.out.println("il y a un -");
                    str = formula.substring(formula.indexOf("-"));
                    this.modifier = Integer.parseInt(str);
                    str = formula.substring(formula.indexOf("d") + 1, formula.indexOf("-"));
                    System.out.println( "str  = " + str);
                    this.diceValue = Integer.parseInt(str);
                } else {
                    System.out.println( "only dice value");
                    str = formula.substring(formula.indexOf("d") + 1);
                    System.out.println( "str  = " + str);
                    this.diceValue = Integer.parseInt(str);
                }
            }
        }
        System.out.println( "modifier  = " + this.modifier);
        System.out.println( "diceValue  = " + this.diceValue);
        System.out.println( "diceValue  = " + this.nbRoll);
        System.out.println( "type  = " + this.type);
    }
    //constructeur
    public Roll(int diceValue, int nbRoll, int modifier) {
        this.diceValue = diceValue;
        this.nbRoll = nbRoll;
        this.modifier = modifier;
        this.type = 3;
    }

    //fonction
    public int makeRoll(RollType rollType) {
        int repeat = 1;
        int bonus = 2;
        int temp = 0;
        if(this.type == 0)
            return -1;
        if(this.nbRoll <= 0)
            return -1;
        if(this.diceValue <= 0)
            return -1;
        Dice d = new Dice(this.diceValue);
        int i = 0;
        int res = 0;
        if(rollType == Roll.RollType.ADVANTAGE)
            bonus = 1;
        if(rollType == Roll.RollType.DISADVANTAGE) {
            bonus = 0;
            temp = this.diceValue * this.nbRoll;
        }
        for (int j  = 0; j < repeat; j++) {
            while (i < this.nbRoll) {
                res += d.rollDice();
                i++;
            }
            System.out.println( "b res  = " + res);
            if(bonus != 2){
                if(bonus == 1 && res > temp)
                    temp = res;
                if(bonus == 0 && res < temp)
                    temp = res;
                System.out.println( "c res  = " + res);
                System.out.println( "temp  = " + temp);
                res = 0;
            }
        }
        if(bonus != 2)
            res = temp;
        if(this.modifier != 0) {
            res += this.modifier;
        }
        System.out.println( "res  = " + res);
        if(res < 0)
            return 0;
        return res;
    }
}