package ru.aiefu.timeandwindct;

public class TimeTicker{

    private boolean enableCustomTicker = false;
    private long dayD;
    private int dayMod;
    private double dayRoundingError;
    private long nightD;
    private int nightMod;
    private double nightRoundingError;

    private boolean isDayLesserThanVanilla = false;
    private boolean isNightLesserThanVanilla = false;

    private double leftOver = 0;
    private double leftOverInverted = 0;

    public void tickTime(ITimeOperations world, long timeOfDay){
        long time = world.getTimeOfDayTAW();
        if(!enableCustomTicker){
            world.setTimeOfDayTAW(time + 1L);
            return;
        }
        int currentTime = (int) (time % 24000);
        int mod;
        double leftOverToAdd;
        if(currentTime < 12000){
            mod = dayMod;
            leftOverToAdd = dayRoundingError;
            if(isDayLesserThanVanilla){
                tickTime(world, mod, leftOverToAdd, time, currentTime, 12000);
                return;
            }
        } else {
            mod = nightMod;
            leftOverToAdd = nightRoundingError;
            if(isNightLesserThanVanilla){
                tickTime(world, mod, leftOverToAdd, time, currentTime, 24000);
                return;
            }
        }
        if(world.getTimeTAW() % mod == 0){
            if(leftOver >= 1.0D){
                leftOver -= 1;
                return;
            }
            leftOver += leftOverToAdd;
            world.setTimeOfDayTAW(time + 1L);
        }
    }
    private void tickTime(ITimeOperations world, int mod, double leftOverToAdd, long timeOfDay, int currentTime, int cap){
        if(currentTime + mod > cap){
            world.setTimeOfDayTAW(timeOfDay + (cap - currentTime));
            leftOverInverted = 0.0D;
            return;
        }
        if(leftOverInverted >= 1.0D){
            world.setTimeOfDayTAW(timeOfDay + mod + 1L);
            leftOverInverted -= 1;
            return;
        }
        world.setTimeOfDayTAW(timeOfDay + mod);
        leftOverInverted += leftOverToAdd;
    }

    public void setupCustomTime(long dayD, long nightD){
        if(dayD == 12000 && nightD == 12000){
            enableCustomTicker = false;
            return;
        }
        this.enableCustomTicker = true;
        double dayVal;
        double nightVal;
        boolean dayInversion = dayD < 12000;
        boolean nightInversion = nightD < 12000;
        dayVal = getFactor(dayD, dayInversion);
        nightVal = getFactor(nightD, nightInversion);
        this.isDayLesserThanVanilla = dayInversion;
        this.isNightLesserThanVanilla = nightInversion;
        int checkDay = (int) dayVal;
        int checkNight = (int) nightVal;
        this.dayMod = checkDay;
        this.nightMod = checkNight;

        this.dayRoundingError = dayVal - checkDay;
        this.nightRoundingError = nightVal - checkNight;

        this.dayD = dayD;
        this.nightD = nightD;

    }

    private double getFactor(long value, boolean shouldInverse){
        return shouldInverse ? 12000.0D / value : value / 12000.0D;
    }




    public void setCustomTicker(boolean bl){
        this.enableCustomTicker = bl;
    }
    public long getDayD(){
        return this.dayD;
    }
    public long getNightD(){
        return this.nightD;
    }
    public int getDayMod(){
        return this.dayMod;
    }
    public int getNightMod(){
        return this.nightMod;
    }
    public double getDayRoundingError(){
        return this.dayRoundingError;
    }
    public double getNightRoundingError(){
        return this.nightRoundingError;
    }
    public boolean getState(){
        return this.enableCustomTicker;
    }
}
