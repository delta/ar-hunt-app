package edu.nitt.delta.orientation22.di.repository

import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.models.Person

class PersonRepository {
fun getalldata(): List<Person>{
    return listOf(
        Person(level = R.drawable.up,
        position = 1,
        profile = R.drawable.item1,
        name="Sarvesh",
        points=2019),
        Person(level = R.drawable.down,
            position = 2,
            profile = R.drawable.item2,
            name="Vivek",
            points=1932),
        Person(level = R.drawable.down,
            position = 3,
            profile = R.drawable.item1,
            name="Nitish",
            points=1398),
        Person(level = R.drawable.up,
            position = 4,
            profile = R.drawable.item1,
            name="Karthik",
            points=1200),
        Person(level = R.drawable.up,
            position = 5,
            profile = R.drawable.item2,
            name="Ram",
            points=1019),
        Person(level = R.drawable.down,
            position = 6,
            profile = R.drawable.item2,
            name="Dipesh",
            points=900),
        Person(level = R.drawable.up,
            position = 7,
            profile = R.drawable.item1,
            name="Satya",
            points=750),
    )
}
}