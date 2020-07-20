package com.grupo19;

import com.grupo19.Interfaces.ITuple;

import java.io.Serializable;

public class Tuple<A, B> implements Serializable, ITuple {
    private final A a;
    private final B b;


    /**
     * construtor parametrizado
     *
     * @param mA primeiro elemento do tuplo
     * @param mB segundo elemento do tuplo
     */
    public Tuple(A mA, B mB) {
        this.a = mA;
        this.b = mB;
    }


    /**
     * devolve o primeiro elememento
     *
     * @return primeiro elemento do grupo
     */
    public A getFirstElem() {
        return this.a;
    }


    /**
     * devolve o segundo elemento
     *
     * @return segundo elemento do grupo
     */
    public B getSecondElem() {
        return this.b;
    }
}
