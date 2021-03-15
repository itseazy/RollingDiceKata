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
        String str;
        if(formula.matches("^[d]\\d+|^[d][-+]\\d+|^[d]\\d+[-+]\\d+")) {                     //check begin by d
            this.nbRoll = 1;                                                                //set to one because no value before the d
            this.type = 1;                                                                  //type is attribu to check if string format is valuable
            if(formula.indexOf("+") != -1 ) {                                               //cut the string according to the string format
                str = formula.substring(formula.indexOf("+"));
                this.modifier = Integer.parseInt(str);
                str = formula.substring(formula.indexOf("d") + 1, formula.indexOf("+"));
                this.diceValue = Integer.parseInt(str);
            } else if ( formula.indexOf("-") != -1) {
                str = formula.substring(formula.indexOf("-"));
                this.modifier = Integer.parseInt(str);
                str = formula.substring(formula.indexOf("d") + 1, formula.indexOf("-"));
                this.diceValue = Integer.parseInt(str);
            }else {
                str = formula.substring(formula.indexOf("d") + 1);
                this.diceValue = Integer.parseInt(str);
            }
        }
        if(formula.matches("^\\d+[d]\\d+|^\\d+[d]\\d+[+-]\\d+|\\d+[d][-+]\\d+")) {   // check begin by figure and d
            str = formula.substring(0,formula.indexOf("d"));
            this.nbRoll = Integer.parseInt(str);
            this.type = 2;
            if(formula.matches("\\d+[d][-+]\\d+")) {
                if(formula.indexOf("+") != -1 ) {
                    str = formula.substring(formula.indexOf("+"));
                    this.modifier = Integer.parseInt(str);
                } else {
                    str = formula.substring(formula.indexOf("-"));
                    this.modifier = Integer.parseInt(str);
                }
            } else {
                if(formula.indexOf("+") != -1 ) {
                    str = formula.substring(formula.indexOf("+"));
                    this.modifier = Integer.parseInt(str);
                    str = formula.substring(formula.indexOf("d") + 1, formula.indexOf("+"));
                    this.diceValue = Integer.parseInt(str);
                } else if ( formula.indexOf("-") != -1) {
                    str = formula.substring(formula.indexOf("-"));
                    this.modifier = Integer.parseInt(str);
                    str = formula.substring(formula.indexOf("d") + 1, formula.indexOf("-"));
                    this.diceValue = Integer.parseInt(str);
                } else {
                    str = formula.substring(formula.indexOf("d") + 1);
                    this.diceValue = Integer.parseInt(str);
                }
            }
        }
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
        if(this.type == 0)                              //check if there was an ok format
            return -1;
        if(this.nbRoll <= 0)                            // check if not negativ
            return -1;
        if(this.diceValue <= 0)                         // check if not negativ
            return -1;
        Dice d = new Dice(this.diceValue);
        int i = 0;
        int res = 0;
        if(rollType == Roll.RollType.ADVANTAGE) {
            bonus = 1;
            repeat = 2;
        }
            
        if(rollType == Roll.RollType.DISADVANTAGE) {
            bonus = 0;
            temp = this.diceValue * this.nbRoll;         //temp must be at the highest valu for line 107 work
            repeat = 2;
        }
        for (int j  = 0; j < repeat; j++) {
            while (i < this.nbRoll) {
                res += d.rollDice();
                i++;
            }
            if(bonus != 2){
                if(bonus == 1 && res > temp)
                    temp = res;
                if(bonus == 0 && res < temp)
                    temp = res;
                res = 0;
            }
            i = 0;
        }
        if(bonus != 2)
            res = temp;
        if(this.modifier != 0) {
            res += this.modifier;
        }
        if(res < 0)
            return 0;
        return res;
    }
}