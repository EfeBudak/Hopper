package com.efebudak.hopper

class Hopper private constructor(val infinite:Boolean){

    fun hopper(lambda: HopperBuilder.() -> Unit):Hopper{

        return HopperBuilder().apply(lambda).build()
    }

    class HopperBuilder{
        var infinite :Boolean = true

        fun infinite(lambda:() -> Boolean){
            this.infinite = lambda()
        }

        fun build() = Hopper(infinite)
    }

}