import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import org.example.nativacare.Database

expect class DatabaseDriverFactory {
     fun create(): SqlDriver
}

//fun createDatabase(driverFactory: DatabaseDriverFactory) {
//     val driver = driverFactory.create()
//     val database = Database(driver)
//
//     // Do more work with the database (see below).
//}
