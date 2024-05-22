package exercise8

import common.FileReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter


internal fun parseMovies(moviesLines: List<String>): List<Movie> {
    val directors = HashMap<String, MovieDirector>()
    val actors = HashMap<String, MovieActor>()

    return moviesLines
        .drop(1)
        .map { movies ->
            val data = movies.split(";")

            val title = data[2].trim()
            val budget = data[0].trim().toLong()
            val revenue = data[5].trim().toLong()
            val releaseDate = parseDate(data[4])
            val runtimeInMinutes = data[6].trim().toDouble().toInt()
            val director = directors.getOrPut(data[8].trim()) { MovieDirector(data[8].trim()) }

            val genres = data[1].trim().split(",")
                .filter { it.isNotBlank() }
                .map { it.trim() }

            val movieActors = data[7].trim().split(",")
                .filter { it.isNotBlank() }
                .map { actorName -> actors.getOrPut(actorName.trim()) { MovieActor(actorName) } }

            val rating = data[3].trim().toDouble()

            Movie(title, budget, revenue, releaseDate, runtimeInMinutes, director, genres, movieActors, rating)
        }
}

private fun parseDate(dateAsString : String) : LocalDate {
    return LocalDate.parse(dateAsString, DateTimeFormatter.ISO_DATE)
}

fun main() {
    val moviesCSVFile = FileReader.readFileInResources("exercise8/movies.csv")
    val movies = parseMovies(moviesCSVFile)

    val movieDBApi : MovieDBApi = MovieDB(movies)

    println(movieDBApi.getBestRatedMovies(5))
}