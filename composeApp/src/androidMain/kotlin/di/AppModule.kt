package di

import DatabaseDriverFactory
import android.content.Context
import data.local.SqlDelightAppointmentDataSource
import data.local.SqlDelightArticleDataSource
import domain.AppointmentDataSource
import domain.ArticleDataSource
import org.example.nativacare.Database


actual class AppModule(
    private val context: Context
) {
    actual val appointmentDataSource: AppointmentDataSource by lazy {
        SqlDelightAppointmentDataSource(
            db = Database(
                driver = DatabaseDriverFactory(context).create()
            )
        )
    }
    actual val articleDataSource: ArticleDataSource by lazy {
        SqlDelightArticleDataSource(
            db = Database(
                driver = DatabaseDriverFactory(context).create()
            )
        )
    }

}