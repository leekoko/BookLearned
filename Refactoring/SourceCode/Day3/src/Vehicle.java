

public abstract class Vehicle{
    // other methods
}

class Car extends Vehicle{
    public void Turn(Direction direction){
        // code here
    }
}

class Motorcycle extends Vehicle{
    public void Turn(Direction direction){
        // code here
    }
}

enum Direction{
    Left,
    Right
}
