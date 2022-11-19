package edu.nitt.delta.orientation22.di.repository

import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.models.Person

class PersonRepository {
fun getalldata(): List<Person>{
    return listOf(
        Person(
        position = 1,
        profile = R.drawable.item1,
        name="Sarvesh",
        points=2019),
        Person(
            position = 2,
            profile = R.drawable.item2,
            name="Vivek Venkataraman",
            points=1932),
        Person(
            position = 3,
            profile = R.drawable.item1,
            name="Nitish",
            points=1398),
        Person(
            position = 4,
            profile = R.drawable.item1,
            name="Karthik",
            points=1200),
        Person(
            position = 5,
            profile = R.drawable.item2,
            name="Ram",
            points=1019),
        Person(
            position = 6,
            profile = R.drawable.item2,
            name="Dipesh",
            points=900),
        Person(
            position = 7,
            profile = R.drawable.item1,
            name="Satyamurthi Doddini",
            points=750),
    )
}

}