package dk.polytope.androidkarafunapi;

public class InvalidAttributeException extends Exception {
    private String name;
    private String value;

    public InvalidAttributeException(String name) {
        this(name, null);
    }

    public InvalidAttributeException(String name, String value){
        this.name = name;
        this.value = value;
    }

    @Override
    public String getMessage(){
        return "Invalid attribute: \""+name+"\", with value: \""+value+"\"";
    }
}
