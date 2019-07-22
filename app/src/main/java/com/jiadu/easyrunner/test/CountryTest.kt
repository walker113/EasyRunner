package com.jiadu.easyrunner.test


class CountryTest {

    fun filterCountries (countries: List<Country>,
                         conditions: (Country) -> Boolean) : List<Country>{
        val res = mutableListOf<Country>()

        for (c in countries) {
            if (conditions(c)) {
                res.add(c)
            }
        }

        return res
    }

    fun bigger(country: Country): Boolean {
        return country.continient == "EU" && country.pop > 1000
    }
}

class Book(val name: String)


enum class Day {
    MON,
    TUE,
    WEN,
    THU,FRI,
    SAT,SUN
}

enum class DayOfWeek(val day: Int){
    MON(1),TUR(2),WEN(3), THU(4), FRI(5);
    fun getDayNumber():Int{
        return day
    }
}


class Bird(age: Int) {
    val age: Int

    init {
        this.age = age
    }

    fun fly () {

    }

}


class Prize private constructor(val name: String, val count: Int, val type: Int) {
    companion object {
        val TYPE_COMMON = 1
        val TYPE_REDPACK = 2
        val TYPE_COUPON = 3

        val defaultCommonPrize = Prize("普通奖品", 10, Prize.TYPE_COMMON)
        fun newRedpackPrize(name: String, count: Int) = Prize(name, count, Prize.TYPE_REDPACK)

    }
}

object DatabaceConfig{
    var host: String = "127.0.0.1"
    var port: Int = 3306
    var username: String = "root"
    var password: String = ""
}

fun main() {

    val newRedpackPrize = Prize.newRedpackPrize("", 12)


}