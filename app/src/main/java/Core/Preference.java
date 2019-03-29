package Core;

public class Preference {
    private String setting;
    private boolean value;

    public Preference(){

    }

    public Preference(String setting, boolean value){
        this.setting = setting;
        this.value = value;
    }

    public void SetValue(boolean value){
        this.value = value;
    }

    public boolean GetValue(){
        return value;
    }
}
