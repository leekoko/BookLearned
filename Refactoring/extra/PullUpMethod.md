# 方法提公

D：以下为原来的代码：

```java
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
```

M：这段代码有什么问题？

Z：Vehicle的两个子类Car，Motorcycle都用到Turn方法，可以把这个方法提出来放到基类中。这样不仅减少代码量，而且提高了代码的复用性。

```java
public abstract class Vehicle{
    public void Turn(Direction direction){
        // code here
    }
    // other methods
}

class Car extends Vehicle{

}

class Motorcycle extends Vehicle{

}

enum Direction{
    Left,
    Right
}
```

M：总结一下，两个子类都用到同一个方法，把共性方法提到基类中。

# 降低方法

Z：降低方法刚好和上面的方法提公相反，方法提公就是基类中某个方法只有某个子类会用，那就提取到需要它的类中去。让结构更清晰，提高代码的可读性。

原代码：

```java
public abstract class Vehicle{
    public void Turn(Direction direction){
        // code here
    }
    // other methods
}

class Car extends Vehicle{

}

class Motorcycle extends Vehicle{

}

enum Direction{
    Left,
    Right
}
```

重构后的代码：

```java
public abstract class Vehicle{
    // other methods
}

class Car extends Vehicle{
    public void Turn(Direction direction){
        // code here
    }
}

class Motorcycle extends Vehicle{

}

enum Direction{
    Left,
    Right
}
```

