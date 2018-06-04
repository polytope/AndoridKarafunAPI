package dk.polytope.androidkarafunapi;

public class InvalidTagException extends Exception {
    private String name;
    private String value;

    public InvalidTagException(String name) {
        this(name, null);
    }

    public InvalidTagException(String name, String value){
        this.name = name;
        this.value = value;
    }

    @Override
    public String getMessage(){
        return "Invalid tag: \""+name+"\", with value: \""+value+"\"";
    }
}
