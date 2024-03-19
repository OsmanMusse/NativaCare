import android.content.Context
import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.example.nativacare.Database


actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver{
        return AndroidSqliteDriver(
            schema = Database.Schema.synchronous(),
            context = context,
            name = "appointments.db"
        )
    }
}