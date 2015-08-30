package princetronics.assignment2.Data;

public class Group {
    String name;
    String id;

    public Group(){

    }

    public Group(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }
}