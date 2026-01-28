class A {
    A get() {
        return this;
    }
}

class B extends A {
    @Override
    B get() {
        return this;
    }
}

class covariant {
    public static void main(String[] args) {
        A obj=new A();
        System.out.println(obj.get().getClass());
        B obj2=new B();
        obj2.get();
        System.out.println(obj2);
        B b = new B();
        A x = b.get();
        B y = b.get();

    }
}
