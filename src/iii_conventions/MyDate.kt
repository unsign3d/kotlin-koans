package iii_conventions

data class RepeatedTimeInterval(val timeInterval: TimeInterval, val number: Int)

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {

    override fun compareTo(other: MyDate): Int {
        if(other.year != this.year) return this.year - other.year
        if(other.month != this.month) return this.month - other.month
        if(other.dayOfMonth != this.dayOfMonth) return this.dayOfMonth - other.dayOfMonth
        return 0
    }

    infix operator fun plus(timeInterval: TimeInterval): MyDate =
            addTimeIntervals(timeInterval, 1)

    infix operator fun plus(repeatedTimeInterval: RepeatedTimeInterval): MyDate =
            addTimeIntervals(
                    repeatedTimeInterval.timeInterval,
                    repeatedTimeInterval.number)
}


operator fun MyDate.rangeTo(other: MyDate): DateRange {
    return DateRange(this, other)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    infix operator fun times(i: Int) = RepeatedTimeInterval(this,i)

}

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = DateIterator(this)

}

class DateIterator(val dateRange: DateRange) : Iterator<MyDate> {
    var current = dateRange.start

    override fun hasNext(): Boolean {
        return current <= dateRange.endInclusive
    }

    override fun next(): MyDate {
        val result = current
        current = current.nextDay()
        return result

    }

}